package com.foodSystem.tromer.Controller;

import com.foodSystem.tromer.Logica.Categoria;
import com.foodSystem.tromer.Logica.Producto;
import com.foodSystem.tromer.Repository.ProductoRepository;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = "http://localhost:5173")
public class ProductoController {
   
    @Autowired
    private final ProductoRepository productoRepository;

    // Inyección por constructor (buena práctica vs @Autowired en campo)
    public ProductoController(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    // GET /api/productos  →  lista todos
    // GET /api/productos?categoria=BEBIDA  →  filtra por categoría
    @GetMapping
    public ResponseEntity<List<Producto>> listarProductos(
            @RequestParam(required = false) String categoria) {

        if (categoria != null) {
            Categoria cat = Categoria.desdeString(categoria);
            if (cat == null) {
                return ResponseEntity.badRequest().build();
            }
            return ResponseEntity.ok(productoRepository.findByCategoria(cat));
        }

        return ResponseEntity.ok(productoRepository.findAll());
    }

    // GET /api/productos/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerProducto(@PathVariable Long id) {
        return productoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/productos
    @PostMapping
    public ResponseEntity<Producto> crearProducto(@Valid @RequestBody Producto producto) {
        Producto guardado = productoRepository.save(producto);
        return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
    }

    // PUT /api/productos/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizarProducto(
            @PathVariable Long id,
            @Valid @RequestBody Producto datos) {

        return productoRepository.findById(id)
                .map(existente -> {
                    existente.setNombre(datos.getNombre());
                    existente.setCategoria(datos.getCategoria());
                    existente.setPrecio(datos.getPrecio());
                    return ResponseEntity.ok(productoRepository.save(existente));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE /api/productos/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id) {
        if (!productoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        productoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
