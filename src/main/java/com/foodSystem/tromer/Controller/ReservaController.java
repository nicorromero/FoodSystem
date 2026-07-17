package com.foodSystem.tromer.Controller;

import com.foodSystem.tromer.Logica.Reserva;
import com.foodSystem.tromer.Service.ReservaService;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservas")
public class ReservaController {

    private final ReservaService reservaService;

    // Inyección por constructor
    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    // GET /api/reservas  →  lista todas
    @GetMapping
    public ResponseEntity<List<Reserva>> listarReservas() {
        return ResponseEntity.ok(reservaService.mostrarReservas());
    }

    // POST /api/reservas
    @PostMapping
    public ResponseEntity<Reserva> crearReserva(@Valid @RequestBody Reserva reserva) {
        Reserva guardada = reservaService.registrarReserva(
                reserva.getCliente(),
                reserva.getCantidad(),
                reserva.getFecha()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(guardada);
    }

    // PUT /api/reservas/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Void> actualizarReserva(
            @PathVariable Long id,
            @Valid @RequestBody Reserva datos) {

        boolean actualizada = reservaService.editarReserva(
                id,
                datos.getCliente(),
                datos.getCantidad()
        );

        if (actualizada) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    // DELETE /api/reservas/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarReserva(@PathVariable Long id) {
        reservaService.eliminarReserva(id);
        return ResponseEntity.noContent().build();
    }
}
