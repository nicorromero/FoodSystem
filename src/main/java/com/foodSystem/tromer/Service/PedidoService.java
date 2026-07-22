package com.foodSystem.tromer.Service;

import com.foodSystem.tromer.DTO.PedidoRequestDTO;
import com.foodSystem.tromer.DTO.PedidoResponseDTO;
import com.foodSystem.tromer.Exception.RecursoNoEncontradoException;
import com.foodSystem.tromer.Logica.Destino;
import com.foodSystem.tromer.Logica.Pedido;
import com.foodSystem.tromer.Repository.DestinoRepository;
import com.foodSystem.tromer.Repository.PedidoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * Servicio de negocio para la gestión de Pedidos.
 *
 * La lógica de construcción y mutación de estado se delega a los métodos
 * de dominio de la entidad Pedido (agregarItem, avanzarEstado, cancelar).
 * Este servicio actúa como orquestador.
 */
@Service
@Transactional
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final DestinoRepository destinoRepository;

    public PedidoService(PedidoRepository pedidoRepository, DestinoRepository destinoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.destinoRepository = destinoRepository;
    }

    /**
     * Registra un nuevo Pedido. El estado inicial siempre será PENDIENTE
     * y la fecha se asigna automáticamente en el constructor de Pedido.
     *
     * @throws RecursoNoEncontradoException (HTTP 404) si el destinoId no existe.
     */
    public PedidoResponseDTO registrarPedido(PedidoRequestDTO dto) {
        Destino destino = destinoRepository.findById(dto.destinoId())
            .orElseThrow(() -> new RecursoNoEncontradoException("Destino", dto.destinoId()));
        Pedido nuevoPedido = new Pedido(dto.cliente(), destino);
        return toDTO(pedidoRepository.save(nuevoPedido));
    }

    /**
     * Edita los campos mutables de un Pedido existente.
     *
     * @throws RecursoNoEncontradoException (HTTP 404) si el pedido o destino no existe.
     */
    public PedidoResponseDTO editarPedido(Long id, PedidoRequestDTO dto) {
        Pedido ped = pedidoRepository.findById(id)
            .orElseThrow(() -> new RecursoNoEncontradoException("Pedido", id));
        Destino destino = destinoRepository.findById(dto.destinoId())
            .orElseThrow(() -> new RecursoNoEncontradoException("Destino", dto.destinoId()));
        ped.setCliente(dto.cliente());
        ped.setDestino(destino);
        if (dto.estado() != null) {
            ped.setEstado(dto.estado());
        }
        // Dirty checking de Hibernate detecta los cambios y hace UPDATE al cerrar la transacción
        return toDTO(ped);
    }

    /**
     * Elimina un Pedido por ID.
     *
     * @throws RecursoNoEncontradoException (HTTP 404) si no existe el ID.
     */
    public void eliminarPedido(Long id) {
        if (!pedidoRepository.existsById(id)) {
            throw new RecursoNoEncontradoException("Pedido", id);
        }
        pedidoRepository.deleteById(id);
    }

    /**
     * Retorna todos los pedidos registrados.
     */
    @Transactional(readOnly = true)
    public List<PedidoResponseDTO> mostrarPedido() {
        return pedidoRepository.findAll()
            .stream()
            .map(this::toDTO)
            .toList();
    }

    /**
     * Busca un Pedido por ID.
     *
     * @throws RecursoNoEncontradoException (HTTP 404) si no existe.
     */
    @Transactional(readOnly = true)
    public PedidoResponseDTO buscarPorId(Long id) {
        return pedidoRepository.findById(id)
            .map(this::toDTO)
            .orElseThrow(() -> new RecursoNoEncontradoException("Pedido", id));
    }

    /** Convierte una entidad Pedido a su DTO de salida, aplanando el Destino. */
    private PedidoResponseDTO toDTO(Pedido p) {
        Long destinoId = p.getDestino() != null ? p.getDestino().getId() : null;
        String destinoNombre = p.getDestino() != null ? p.getDestino().getNombre() : null;
        return new PedidoResponseDTO(
            p.getId(),
            p.getCliente(),
            p.getEstado().name(),
            p.getTotal(),
            p.getFecha(),
            destinoId,
            destinoNombre
        );
    }
}
