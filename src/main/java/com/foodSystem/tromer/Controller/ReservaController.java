package com.foodSystem.tromer.Controller;

import com.foodSystem.tromer.DTO.ReservaRequestDTO;
import com.foodSystem.tromer.DTO.ReservaResponseDTO;
import com.foodSystem.tromer.Service.ReservaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Controlador REST para la gestión de Reservas.
 * Endpoint base: /api/reservas
 *
 * El body del POST/PUT espera:
 * { "cliente": "Juan", "cantidad": 4, "fecha": "2026-08-15T20:00:00" }
 */
@RestController
@RequestMapping("/api/reservas")
public class ReservaController {

    private final ReservaService reservaService;

    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    /** GET /api/reservas → lista todas las reservas */
    @GetMapping
    public ResponseEntity<List<ReservaResponseDTO>> listarReservas() {
        return ResponseEntity.ok(reservaService.mostrarReservas());
    }

    /** GET /api/reservas/{id} → busca una reserva por ID */
    @GetMapping("/{id}")
    public ResponseEntity<ReservaResponseDTO> obtenerReserva(@PathVariable Long id) {
        return ResponseEntity.ok(reservaService.buscarPorId(id));
    }

    /** POST /api/reservas → crea una nueva reserva */
    @PostMapping
    public ResponseEntity<ReservaResponseDTO> crearReserva(
            @Valid @RequestBody ReservaRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(reservaService.registrarReserva(dto));
    }

    /** PUT /api/reservas/{id} → actualiza una reserva existente */
    @PutMapping("/{id}")
    public ResponseEntity<ReservaResponseDTO> actualizarReserva(
            @PathVariable Long id,
            @Valid @RequestBody ReservaRequestDTO dto) {
        return ResponseEntity.ok(reservaService.editarReserva(id, dto));
    }

    /** DELETE /api/reservas/{id} → elimina una reserva (HTTP 404 si no existe) */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarReserva(@PathVariable Long id) {
        reservaService.eliminarReserva(id);
        return ResponseEntity.noContent().build();
    }
}
