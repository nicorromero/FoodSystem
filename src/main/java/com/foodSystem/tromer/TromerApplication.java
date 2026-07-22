package com.foodSystem.tromer;

import com.foodSystem.tromer.Logica.Categoria;
import com.foodSystem.tromer.Logica.Mesa;
import com.foodSystem.tromer.Logica.Pedido;
import com.foodSystem.tromer.Logica.Producto;
import com.foodSystem.tromer.Logica.Reserva;
import com.foodSystem.tromer.Repository.ProductoRepository;
import com.foodSystem.tromer.Service.GestorService;
import com.foodSystem.tromer.Service.PedidoService;
import com.foodSystem.tromer.Service.ProductoService;
import com.foodSystem.tromer.Service.ReservaService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class TromerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TromerApplication.class, args);
    }
}
