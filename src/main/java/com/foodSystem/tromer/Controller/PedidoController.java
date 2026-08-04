package com.foodSystem.tromer.controller;

import com.foodSystem.tromer.dataTranferObject.PedidoRequestDTO;
import com.foodSystem.tromer.dataTranferObject.PedidoResponseDTO;
import com.foodSystem.tromer.service.PedidoService;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

/**
 * Controlador REST para la gestión de Pedidos.
 * Endpoint base: /api/pedidos
 *
 * El body del POST espera: { "cliente": "...", "destinoId": 1 }
 * El body del PUT espera: { "cliente": "...", "destinoId": 1, "estado":
 * "EN_PREPARACION" }
 */
@RestController
@RequestMapping("/api/pedidos")
@Tag(name = "Pedidos", description = "Gestión del ciclo de vida de los pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    /** GET /api/pedidos → lista todos los pedidos */
    @Operation(summary = "Listar todos los pedidos")
    @GetMapping
    public ResponseEntity<List<PedidoResponseDTO>> listarPedidos() {
        return ResponseEntity.ok(pedidoService.mostrarPedido());
    }

    /** GET /api/pedidos/{id} → busca un pedido por ID */
    @Operation(summary = "Obtener un pedido por su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Pedido encontrado"),
        @ApiResponse(responseCode = "404", description = "Pedido no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponseDTO> obtenerPedido(@PathVariable Long id) {
        return ResponseEntity.ok(pedidoService.buscarPorId(id));
    }

    /**
     * POST /api/pedidos → crea un nuevo pedido.
     * El estado se fija a PENDIENTE automáticamente; no se acepta en el body al
     * crear.
     */
    @Operation(summary = "Crear un nuevo pedido", description = "El estado inicial siempre será PENDIENTE.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Pedido creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
        @ApiResponse(responseCode = "404", description = "El destino indicado no existe")
    })
    @PostMapping
    public ResponseEntity<PedidoResponseDTO> crearPedido(
            @Valid @RequestBody PedidoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(pedidoService.registrarPedido(dto));
    }

    /** PUT /api/pedidos/{id} → actualiza cliente, destino y/o estado */
    @Operation(summary = "Actualizar un pedido existente", description = "Permite modificar el cliente, el destino y avanzar el estado.")
    @PutMapping("/{id}")
    public ResponseEntity<PedidoResponseDTO> actualizarPedido(
            @PathVariable Long id,
            @Valid @RequestBody PedidoRequestDTO dto) {
        return ResponseEntity.ok(pedidoService.editarPedido(id, dto));
    }

    /** DELETE /api/pedidos/{id} → elimina un pedido (HTTP 404 si no existe) */
    @Operation(summary = "Eliminar un pedido por su ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPedido(@PathVariable Long id) {
        pedidoService.eliminarPedido(id);
        return ResponseEntity.noContent().build();
    }
}
