/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.foodSystem.tromer.Repository;


import com.foodSystem.tromer.Logica.Pedido;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Tomas
 */
public interface PedidoRepository extends JpaRepository<Pedido, Long>{
    
    List<Pedido> findByClienteIgnoreCase(String cliente);
    
}
