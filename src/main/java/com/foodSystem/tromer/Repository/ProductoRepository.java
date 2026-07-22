package com.foodSystem.tromer.Repository;

import com.foodSystem.tromer.Logica.Categoria;
import com.foodSystem.tromer.Logica.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    /**
     * Búsqueda parcial por nombre (insensible a mayúsculas).
     * Útil para filtros de UI, buscadores y autocompletado.
     */
    List<Producto> findByNombreContainingIgnoreCase(String nombre);

    /**
     * Búsqueda exacta por nombre.
     * Retorna Optional para obligar al llamador a manejar el caso "no encontrado"
     * y evitar NullPointerExceptions en el Service.
     */
    Optional<Producto> findByNombre(String nombre);

    /**
     * Verifica si ya existe un producto con ese nombre exacto.
     * Más eficiente que findByNombre().isPresent(): solo ejecuta COUNT, no SELECT *.
     * Útil para validar duplicados antes de registrar un nuevo producto.
     */
    boolean existsByNombre(String nombre);

    /**
     * Filtra productos por categoría.
     * Útil para presentar el menú segmentado (PLATOS, BEBIDAS, POSTRES).
     */
    List<Producto> findByCategoria(Categoria categoria);
}
