package com.foodSystem.tromer.Controller;

import com.foodSystem.tromer.Logica.EstadoPedido;
import com.foodSystem.tromer.Logica.Pedido;
import com.foodSystem.tromer.Service.PedidoService;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para la gestión de Pedidos.
 * Endpoint base: /api/pedidos
 */
@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    // GET /api/pedidos — lista todos los pedidos
    @GetMapping
    public ResponseEntity<List<Pedido>> listarPedidos() {
        return ResponseEntity.ok(pedidoService.mostrarPedido());
    }

    // POST /api/pedidos — crea un nuevo pedido
    // El estado será PENDIENTE y la fecha se asigna automáticamente.
    // El body solo necesita: { "cliente": "...", "destino": { "id": ... } }
    @PostMapping
    public ResponseEntity<Pedido> crearPedido(@Valid @RequestBody Pedido pedido) {
        Pedido guardado = pedidoService.registrarPedido(
                pedido.getCliente(),
                pedido.getDestino()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
    }

    // PUT /api/pedidos/{id} — actualiza nombre, estado y destino de un pedido
    // El body debe incluir: { "cliente": "...", "estado": "EN_PREPARACION", "destino": { "id": ... } }
    @PutMapping("/{id}")
    public ResponseEntity<Void> actualizarPedido(
            @PathVariable Long id,
            @Valid @RequestBody Pedido datos) {

        EstadoPedido estado = datos.getEstado();

        boolean actualizado = pedidoService.editarPedido(
                id,
                datos.getCliente(),
                estado,
                datos.getDestino()
        );

        if (actualizado) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    // DELETE /api/pedidos/{id} — elimina un pedido por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPedido(@PathVariable Long id) {
        pedidoService.eliminarPedido(id);
        return ResponseEntity.noContent().build();
    }
}
