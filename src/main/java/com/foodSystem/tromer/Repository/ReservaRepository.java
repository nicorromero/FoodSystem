package com.foodSystem.tromer.Repository;

import com.foodSystem.tromer.Logica.Reserva;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;
import java.util.List;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    /**
     * Historial paginado de reservas de un cliente.
     * Evita cargar todo el historial en memoria cuando un cliente
     * tiene muchas reservas acumuladas.
     */
    Page<Reserva> findByClienteIgnoreCase(String cliente, Pageable pageable);

    /**
     * Próximas reservas a partir de una fecha dada, ordenadas cronológicamente.
     * Uso típico: findByFechaAfterOrderByFechaAsc(LocalDateTime.now())
     * para ver la agenda del día/semana.
     */
    List<Reserva> findByFechaAfterOrderByFechaAsc(LocalDateTime desde);

    /**
     * Reservas para grupos iguales o mayores al mínimo indicado.
     * Útil para planificación de personal y asignación de salas.
     */
    List<Reserva> findByCantidadGreaterThanEqual(int minimoPersonas);

    /**
     * Detecta conflictos de agenda: retorna true si ya existe una reserva
     * cuya fecha cae dentro del rango [inicio, fin].
     * Usa JPQL para portabilidad entre motores de BD (MySQL, H2, PostgreSQL).
     *
     * @param inicio Inicio del rango de tiempo a verificar.
     * @param fin    Fin del rango de tiempo a verificar.
     * @return true si hay al menos una reserva solapada.
     */
    @Query("SELECT COUNT(r) > 0 FROM Reserva r WHERE r.fecha BETWEEN :inicio AND :fin")
    boolean existeReservaEnRango(
        @Param("inicio") LocalDateTime inicio,
        @Param("fin") LocalDateTime fin
    );
}
