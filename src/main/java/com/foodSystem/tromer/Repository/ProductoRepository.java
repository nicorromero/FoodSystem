package com.foodSystem.tromer.Repository;

import com.foodSystem.tromer.Logica.Categoria;
import com.foodSystem.tromer.Logica.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    // Busca productos cuyo nombre contenga el texto (insensible a mayúsculas)
    List<Producto> findByNombreContainingIgnoreCase(String nombre);

    // Filtra por categoría (enum)
    List<Producto> findByCategoria(Categoria categoria);
    
    
    
    
}
