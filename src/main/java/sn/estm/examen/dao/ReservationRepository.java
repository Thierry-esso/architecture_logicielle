package sn.estm.examen.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sn.estm.examen.entites.Reservation;
import sn.estm.examen.entites.SalleEquipement;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByUtilisateurId(Long utilisateurId);
    List<Reservation> findBySalleId(Long salleId);
    List<Reservation> id(Long id);
}
