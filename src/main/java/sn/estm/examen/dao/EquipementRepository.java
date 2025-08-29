package sn.estm.examen.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sn.estm.examen.entites.Equipement;

@Repository
public interface EquipementRepository extends JpaRepository<Equipement, Long> {
    boolean existsByNom(String nom);
}
