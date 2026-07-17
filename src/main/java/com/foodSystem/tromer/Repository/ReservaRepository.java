/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.foodSystem.tromer.Repository;

import com.foodSystem.tromer.Logica.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Tomas
 */
public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    
}
