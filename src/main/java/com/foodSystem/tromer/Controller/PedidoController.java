package com.foodSystem.tromer.Controller;

import com.foodSystem.tromer.Logica.Pedido;
import com.foodSystem.tromer.Service.PedidoService;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    // Inyección por constructor
    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    // GET /api/pedidos  →  lista todos
    @GetMapping
    public ResponseEntity<List<Pedido>> listarPedidos() {
        return ResponseEntity.ok(pedidoService.mostrarPedido());
    }

    // POST /api/pedidos
    @PostMapping
    public ResponseEntity<Pedido> crearPedido(@Valid @RequestBody Pedido pedido) {
        Pedido guardado = pedidoService.registrarPedido(
                pedido.getCliente(),
                pedido.getEstado(),
                pedido.getDestino(),
                pedido.getFecha(),
                pedido.getTotal()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
    }

    // PUT /api/pedidos/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Void> actualizarPedido(
            @PathVariable Long id,
            @Valid @RequestBody Pedido datos) {

        boolean actualizado = pedidoService.editarPedido(
                id,
                datos.getCliente(),
                datos.getEstado(),
                datos.getDestino()
        );

        if (actualizado) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    // DELETE /api/pedidos/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPedido(@PathVariable Long id) {
        pedidoService.eliminarPedido(id);
        return ResponseEntity.noContent().build();
    }
}
