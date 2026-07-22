package com.foodSystem.tromer.Service;

import com.foodSystem.tromer.DTO.ReservaRequestDTO;
import com.foodSystem.tromer.DTO.ReservaResponseDTO;
import com.foodSystem.tromer.Exception.RecursoNoEncontradoException;
import com.foodSystem.tromer.Logica.Reserva;
import com.foodSystem.tromer.Repository.ReservaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * Servicio de negocio para la gestión de Reservas.
 */
@Service
@Transactional
public class ReservaService {

    private final ReservaRepository reservaRepository;

    public ReservaService(ReservaRepository reservaRepository) {
        this.reservaRepository = reservaRepository;
    }

    /**
     * Registra una nueva Reserva.
     *
     * @param dto Datos de la reserva validados por @Valid en el Controller.
     * @return DTO con los datos de la reserva persistida, incluido su ID.
     */
    public ReservaResponseDTO registrarReserva(ReservaRequestDTO dto) {
        Reserva nueva = new Reserva(dto.cliente(), dto.cantidad(), dto.fecha());
        return toDTO(reservaRepository.save(nueva));
    }

    /**
     * Elimina una Reserva por ID.
     *
     * @throws RecursoNoEncontradoException (HTTP 404) si no existe el ID.
     */
    public void eliminarReserva(Long id) {
        if (!reservaRepository.existsById(id)) {
            throw new RecursoNoEncontradoException("Reserva", id);
        }
        reservaRepository.deleteById(id);
    }

    /**
     * Edita los campos de una Reserva existente.
     *
     * @throws RecursoNoEncontradoException (HTTP 404) si no existe el ID.
     */
    public ReservaResponseDTO editarReserva(Long id, ReservaRequestDTO dto) {
        Reserva res = reservaRepository.findById(id)
            .orElseThrow(() -> new RecursoNoEncontradoException("Reserva", id));
        res.setCliente(dto.cliente());
        res.setCantidad(dto.cantidad());
        res.setFecha(dto.fecha());
        // Dirty checking detecta los cambios; no se necesita save() explícito
        return toDTO(res);
    }

    /**
     * Retorna todas las reservas registradas.
     */
    @Transactional(readOnly = true)
    public List<ReservaResponseDTO> mostrarReservas() {
        return reservaRepository.findAll()
            .stream()
            .map(this::toDTO)
            .toList();
    }

    /**
     * Busca una Reserva por ID.
     *
     * @throws RecursoNoEncontradoException (HTTP 404) si no existe.
     */
    @Transactional(readOnly = true)
    public ReservaResponseDTO buscarPorId(Long id) {
        return reservaRepository.findById(id)
            .map(this::toDTO)
            .orElseThrow(() -> new RecursoNoEncontradoException("Reserva", id));
    }

    /** Convierte una entidad Reserva a su DTO de salida. */
    private ReservaResponseDTO toDTO(Reserva r) {
        return new ReservaResponseDTO(r.getId(), r.getCliente(), r.getCantidad(), r.getFecha());
    }
}
