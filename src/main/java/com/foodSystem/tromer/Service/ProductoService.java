package com.foodSystem.tromer.Service;

import com.foodSystem.tromer.DTO.ProductoRequestDTO;
import com.foodSystem.tromer.DTO.ProductoResponseDTO;
import com.foodSystem.tromer.Exception.RecursoNoEncontradoException;
import com.foodSystem.tromer.Logica.Categoria;
import com.foodSystem.tromer.Logica.Producto;
import com.foodSystem.tromer.Repository.ProductoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * Servicio de negocio para la gestión de Productos.
 *
 * Transaccionalidad:
 * - @Transactional a nivel de clase garantiza atomicidad en escrituras.
 * - readOnly=true en lecturas desactiva el dirty checking de Hibernate,
 *   optimizando el rendimiento de consultas.
 */
@Service
@Transactional
public class ProductoService {

    private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    /**
     * Registra un nuevo Producto.
     *
     * @param dto Datos del producto validados por @Valid en el Controller.
     * @return DTO con los datos del producto persistido, incluido su ID generado.
     */
    public ProductoResponseDTO registrarProducto(ProductoRequestDTO dto) {
        // Categoria.desdeString() lanza IllegalArgumentException si el valor es inválido
        Categoria cat = Categoria.desdeString(dto.categoria());
        Producto nuevo = new Producto(dto.nombre(), cat, dto.precio());
        return toDTO(productoRepository.save(nuevo));
    }

    /**
     * Retorna todos los productos registrados.
     * readOnly=true: Hibernate no realiza dirty checking sobre la lista retornada.
     */
    @Transactional(readOnly = true)
    public List<ProductoResponseDTO> mostrarProductos() {
        return productoRepository.findAll()
            .stream()
            .map(this::toDTO)
            .toList();
    }

    /**
     * Retorna productos filtrados por categoría.
     */
    @Transactional(readOnly = true)
    public List<ProductoResponseDTO> mostrarProductosPorCategoria(String categoria) {
        Categoria cat = Categoria.desdeString(categoria);
        return productoRepository.findByCategoria(cat)
            .stream()
            .map(this::toDTO)
            .toList();
    }

    /**
     * Busca un Producto por ID.
     *
     * @throws RecursoNoEncontradoException (HTTP 404) si no existe.
     */
    @Transactional(readOnly = true)
    public ProductoResponseDTO buscarPorId(Long id) {
        return productoRepository.findById(id)
            .map(this::toDTO)
            .orElseThrow(() -> new RecursoNoEncontradoException("Producto", id));
    }

    /**
     * Edita un Producto existente.
     *
     * @throws RecursoNoEncontradoException (HTTP 404) si no existe el ID.
     */
    public ProductoResponseDTO editarProducto(Long id, ProductoRequestDTO dto) {
        Producto existente = productoRepository.findById(id)
            .orElseThrow(() -> new RecursoNoEncontradoException("Producto", id));
        existente.setNombre(dto.nombre());
        existente.setPrecio(dto.precio());
        existente.setCategoria(Categoria.desdeString(dto.categoria()));
        // Hibernate detecta el cambio y hace UPDATE automáticamente al cerrar la transacción
        return toDTO(existente);
    }

    /**
     * Elimina un Producto por ID.
     *
     * @throws RecursoNoEncontradoException (HTTP 404) si no existe el ID.
     */
    public void eliminarProducto(Long id) {
        if (!productoRepository.existsById(id)) {
            throw new RecursoNoEncontradoException("Producto", id);
        }
        productoRepository.deleteById(id);
    }

    /** Convierte una entidad Producto a su DTO de salida. */
    private ProductoResponseDTO toDTO(Producto p) {
        return new ProductoResponseDTO(
            p.getId(),
            p.getNombre(),
            p.getCategoria().name(),
            p.getPrecio()
        );
    }
}
