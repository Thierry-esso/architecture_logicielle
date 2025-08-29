package sn.estm.examen.metier;

import org.springframework.stereotype.Service;
import sn.estm.examen.dao.ReservationRepository;
import sn.estm.examen.dao.SalleEquipementRepository;
import sn.estm.examen.dao.SalleRepository;
import sn.estm.examen.entites.Salle;
import sn.estm.examen.entites.SalleEquipement;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SalleService {
    private final SalleRepository salleRepository;
    private final SalleEquipementRepository salleEquipementRepository;
    private final ReservationRepository reservationRepository;

    public SalleService(SalleRepository salleRepository) {
        this.salleRepository = salleRepository;
        salleEquipementRepository = null;
        reservationRepository = null;
    }

    public List<Salle> getAllSalles() {
        return salleRepository.findAll();
    }

    public Salle getSalleById(Long id) {
        return salleRepository.findById(id).orElse(null);
    }

    public Salle createSalle(Salle salle) {
        if (salleRepository.existsByNom(salle.getNom())) {
            throw new RuntimeException("Salle existe déjà");
        }
        return salleRepository.save(salle);
    }

    public Salle updateSalle(Long id, Salle salle) {
        Salle existing = getSalleById(id);
        if (existing == null) {
            throw new RuntimeException("Salle non trouvée");
        }
        existing.setNom(salle.getNom());
        existing.setCapacite(salle.getCapacite());
        return salleRepository.save(existing);
    }

    public void deleteSalle(Long id) {
        salleRepository.deleteById(id);
    }

    public void retirerEquipementDeSalle(Long salleId, Long equipementId) {
        Salle salle = getSalleById(salleId);
        SalleEquipement equipement = salleEquipementRepository.findById(equipementId)
                .orElseThrow(() -> new RuntimeException("Équipement non trouvé"));
        salle.retirerEquipement(equipement); // méthode utilitaire
        salleRepository.save(salle);
    }

    public List<Salle> getSallesDisponibles(LocalDateTime dateDebut, LocalDateTime dateFin) {
        return salleRepository.findSallesDisponibles(dateDebut, dateFin);
    }


}
