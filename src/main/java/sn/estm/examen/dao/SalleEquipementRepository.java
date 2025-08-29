package sn.estm.examen.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sn.estm.examen.entites.SalleEquipement;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SalleEquipementRepository extends JpaRepository<SalleEquipement, Long> {
    List<SalleEquipement> findByDateDebutBeforeAndDateFinAfter(LocalDateTime date, LocalDateTime date2);
}
