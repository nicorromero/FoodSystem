package com.foodSystem.tromer.Controller;

import com.foodSystem.tromer.DTO.PedidoRequestDTO;
import com.foodSystem.tromer.DTO.PedidoResponseDTO;
import com.foodSystem.tromer.Service.PedidoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Controlador REST para la gestión de Pedidos.
 * Endpoint base: /api/pedidos
 *
 * El body del POST espera: { "cliente": "...", "destinoId": 1 }
 * El body del PUT espera:  { "cliente": "...", "destinoId": 1, "estado": "EN_PREPARACION" }
 */
@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    /** GET /api/pedidos → lista todos los pedidos */
    @GetMapping
    public ResponseEntity<List<PedidoResponseDTO>> listarPedidos() {
        return ResponseEntity.ok(pedidoService.mostrarPedido());
    }

    /** GET /api/pedidos/{id} → busca un pedido por ID */
    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponseDTO> obtenerPedido(@PathVariable Long id) {
        return ResponseEntity.ok(pedidoService.buscarPorId(id));
    }

    /**
     * POST /api/pedidos → crea un nuevo pedido.
     * El estado se fija a PENDIENTE automáticamente; no se acepta en el body al crear.
     */
    @PostMapping
    public ResponseEntity<PedidoResponseDTO> crearPedido(
            @Valid @RequestBody PedidoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(pedidoService.registrarPedido(dto));
    }

    /** PUT /api/pedidos/{id} → actualiza cliente, destino y/o estado */
    @PutMapping("/{id}")
    public ResponseEntity<PedidoResponseDTO> actualizarPedido(
            @PathVariable Long id,
            @Valid @RequestBody PedidoRequestDTO dto) {
        return ResponseEntity.ok(pedidoService.editarPedido(id, dto));
    }

    /** DELETE /api/pedidos/{id} → elimina un pedido (HTTP 404 si no existe) */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPedido(@PathVariable Long id) {
        pedidoService.eliminarPedido(id);
        return ResponseEntity.noContent().build();
    }
}
