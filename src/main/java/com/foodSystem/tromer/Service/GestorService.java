package com.foodSystem.tromer.Service;

import com.foodSystem.tromer.DTO.PedidoRequestDTO;
import com.foodSystem.tromer.DTO.PedidoResponseDTO;
import com.foodSystem.tromer.DTO.ProductoRequestDTO;
import com.foodSystem.tromer.DTO.ProductoResponseDTO;
import com.foodSystem.tromer.DTO.ReservaRequestDTO;
import com.foodSystem.tromer.DTO.ReservaResponseDTO;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Fachada (Facade Pattern) que centraliza el acceso a los tres servicios
 * principales del sistema. Útil para operaciones que coordinan múltiples
 * dominios o para simplificar el acceso desde clientes internos.
 *
 * BUGS CORREGIDOS:
 * - editarReservaExistente() antes llamaba a eliminarReserva() en lugar de editarReserva()
 * - editarProductoExistente() antes pasaba precio=0 hardcodeado ignorando el parámetro real
 * - mostrarXxx() antes retornaban void, descartando los resultados de los servicios
 * - Constructor vacío public eliminado para garantizar inmutabilidad de dependencias
 */
@Service
public class GestorService {

    private final ProductoService gestorProducto;
    private final PedidoService gestorPedido;
    private final ReservaService gestorReserva;

    public GestorService(ProductoService gestorProducto,
                         PedidoService gestorPedido,
                         ReservaService gestorReserva) {
        this.gestorProducto = gestorProducto;
        this.gestorPedido = gestorPedido;
        this.gestorReserva = gestorReserva;
    }

    // ===== PRODUCTOS =====

    public ProductoResponseDTO registrarNuevoProducto(ProductoRequestDTO dto) {
        return gestorProducto.registrarProducto(dto);
    }

    public void eliminarProducto(Long id) {
        gestorProducto.eliminarProducto(id);
    }

    /** CORREGIDO: ahora pasa el dto completo (antes pasaba precio=0 hardcodeado). */
    public ProductoResponseDTO editarProductoExistente(Long id, ProductoRequestDTO dto) {
        return gestorProducto.editarProducto(id, dto);
    }

    /** CORREGIDO: ahora retorna List<ProductoResponseDTO> en lugar de void. */
    public List<ProductoResponseDTO> mostrarProductos() {
        return gestorProducto.mostrarProductos();
    }

    // ===== PEDIDOS =====

    public PedidoResponseDTO registrarNuevoPedido(PedidoRequestDTO dto) {
        return gestorPedido.registrarPedido(dto);
    }

    public void eliminarPedido(Long id) {
        gestorPedido.eliminarPedido(id);
    }

    public PedidoResponseDTO editarPedidoExistente(Long id, PedidoRequestDTO dto) {
        return gestorPedido.editarPedido(id, dto);
    }

    /** CORREGIDO: ahora retorna List<PedidoResponseDTO> en lugar de void. */
    public List<PedidoResponseDTO> mostrarPedidos() {
        return gestorPedido.mostrarPedido();
    }

    // ===== RESERVAS =====

    public ReservaResponseDTO registrarNuevaReserva(ReservaRequestDTO dto) {
        return gestorReserva.registrarReserva(dto);
    }

    public void eliminarReserva(Long id) {
        gestorReserva.eliminarReserva(id);
    }

    /** CORREGIDO: ahora llama a editarReserva() (antes llamaba a eliminarReserva() — bug crítico). */
    public ReservaResponseDTO editarReservaExistente(Long id, ReservaRequestDTO dto) {
        return gestorReserva.editarReserva(id, dto);
    }

    /** CORREGIDO: ahora retorna List<ReservaResponseDTO> en lugar de void. */
    public List<ReservaResponseDTO> mostrarReservas() {
        return gestorReserva.mostrarReservas();
    }
}
