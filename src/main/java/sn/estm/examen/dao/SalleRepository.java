package sn.estm.examen.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sn.estm.examen.entites.Salle;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SalleRepository extends JpaRepository<Salle, Long> {
    boolean existsByNom(String nom);

    @Query("SELECT s FROM Salle s " +
            "WHERE s.id NOT IN (" +
            "   SELECT r.salle.id FROM Reservation r " +
            "   WHERE (r.dateDebut < :dateFin AND r.dateFin > :dateDebut)" +
            ")")
    List<Salle> findSallesDisponibles(@Param("dateDebut") LocalDateTime dateDebut,
                                      @Param("dateFin") LocalDateTime dateFin);
}
