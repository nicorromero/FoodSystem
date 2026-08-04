package com.foodSystem.tromer.controller;

import com.foodSystem.tromer.dataTranferObject.ReservaRequestDTO;
import com.foodSystem.tromer.dataTranferObject.ReservaResponseDTO;
import com.foodSystem.tromer.service.ReservaService;

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
 * Controlador REST para la gestión de Reservas.
 * Endpoint base: /api/reservas
 *
 * El body del POST/PUT espera:
 * { "cliente": "Juan", "cantidad": 4, "fecha": "2026-08-15T20:00:00" }
 */
@RestController
@RequestMapping("/api/reservas")
@Tag(name = "Reservas", description = "Gestión de reservas de clientes")
public class ReservaController {

    private final ReservaService reservaService;

    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    /** GET /api/reservas → lista todas las reservas */
    @Operation(summary = "Listar todas las reservas")
    @GetMapping
    public ResponseEntity<List<ReservaResponseDTO>> listarReservas() {
        return ResponseEntity.ok(reservaService.mostrarReservas());
    }

    /** GET /api/reservas/{id} → busca una reserva por ID */
    @Operation(summary = "Obtener una reserva por su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Reserva encontrada"),
        @ApiResponse(responseCode = "404", description = "Reserva no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ReservaResponseDTO> obtenerReserva(@PathVariable Long id) {
        return ResponseEntity.ok(reservaService.buscarPorId(id));
    }

    /** POST /api/reservas → crea una nueva reserva */
    @Operation(summary = "Crear una nueva reserva")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Reserva creada"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos (ej. fecha en el pasado)")
    })
    @PostMapping
    public ResponseEntity<ReservaResponseDTO> crearReserva(
            @Valid @RequestBody ReservaRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(reservaService.registrarReserva(dto));
    }

    /** PUT /api/reservas/{id} → actualiza una reserva existente */
    @Operation(summary = "Actualizar una reserva existente")
    @PutMapping("/{id}")
    public ResponseEntity<ReservaResponseDTO> actualizarReserva(
            @PathVariable Long id,
            @Valid @RequestBody ReservaRequestDTO dto) {
        return ResponseEntity.ok(reservaService.editarReserva(id, dto));
    }

    /** DELETE /api/reservas/{id} → elimina una reserva (HTTP 404 si no existe) */
    @Operation(summary = "Eliminar una reserva por su ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarReserva(@PathVariable Long id) {
        reservaService.eliminarReserva(id);
        return ResponseEntity.noContent().build();
    }
}
