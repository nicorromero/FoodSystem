package com.foodSystem.tromer.controller;

import com.foodSystem.tromer.dataTranferObject.ProductoRequestDTO;
import com.foodSystem.tromer.dataTranferObject.ProductoResponseDTO;
import com.foodSystem.tromer.service.ProductoService;

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
 * Controlador REST para la gestión de Productos.
 * Endpoint base: /api/productos
 *
 * CORREGIDO: ahora inyecta ProductoService (no ProductoRepository
 * directamente).
 * La lógica de negocio y acceso a datos vive en el Service, no en el
 * Controller.
 */
@RestController
@RequestMapping("/api/productos")
@Tag(name = "Productos", description = "Gestión del catálogo de productos y menú")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    /**
     * GET /api/productos             → lista todos los productos
     * GET /api/productos?categoria=BEBIDA → filtra por categoría
     */
    @Operation(summary = "Listar productos", description = "Retorna todos los productos o los filtra por categoría si se provee el parámetro.")
    @GetMapping
    public ResponseEntity<List<ProductoResponseDTO>> listarProductos(
            @RequestParam(required = false) String categoria) {
        if (categoria != null) {
            // Categoria.desdeString() lanza IllegalArgumentException si el valor es
            // inválido
            // → GlobalExceptionHandler lo convierte en HTTP 400
            return ResponseEntity.ok(productoService.mostrarProductosPorCategoria(categoria));
        }
        return ResponseEntity.ok(productoService.mostrarProductos());
    }

    /**
     * GET /api/productos/{id} → busca un producto por ID (HTTP 404 si no existe)
     */
    @Operation(summary = "Obtener un producto por su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Producto encontrado"),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> obtenerProducto(@PathVariable Long id) {
        return ResponseEntity.ok(productoService.buscarPorId(id));
    }

    /** POST /api/productos → crea un nuevo producto */
    @Operation(summary = "Crear un nuevo producto")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Producto creado"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos (ej. precio negativo)")
    })
    @PostMapping
    public ResponseEntity<ProductoResponseDTO> crearProducto(
            @Valid @RequestBody ProductoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productoService.registrarProducto(dto));
    }

    /**
     * PUT /api/productos/{id} → actualiza un producto existente (HTTP 404 si no
     * existe)
     */
    @Operation(summary = "Actualizar un producto existente")
    @PutMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> actualizarProducto(
            @PathVariable Long id,
            @Valid @RequestBody ProductoRequestDTO dto) {
        return ResponseEntity.ok(productoService.editarProducto(id, dto));
    }

    /** DELETE /api/productos/{id} → elimina un producto (HTTP 404 si no existe) */
    @Operation(summary = "Eliminar un producto por su ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id) {
        productoService.eliminarProducto(id);
        return ResponseEntity.noContent().build();
    }
}
