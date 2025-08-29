package sn.estm.examen.metier;

import org.springframework.stereotype.Service;
import sn.estm.examen.dao.EquipementRepository;
import sn.estm.examen.entites.Equipement;
import sn.estm.examen.entites.SalleEquipement;

import java.util.List;

@Service
public class EquipementService {

    private final EquipementRepository equipementRepository;

    public EquipementService(EquipementRepository equipementRepository) {
        this.equipementRepository = equipementRepository;
    }

    public List<Equipement> getAllEquipements() {
        return equipementRepository.findAll();
    }

    public Equipement getEquipementById(Long id) {
        return equipementRepository.findById(id).orElse(null);
    }

    public Equipement createEquipement(Equipement equipement) {
        if (equipementRepository.existsByNom(equipement.getNom())) {
            throw new RuntimeException("Équipement existe déjà");
        }
        return equipementRepository.save(equipement);
    }

    public Equipement updateEquipement(Long id, Equipement equipement) {
        Equipement existing = getEquipementById(id);
        if (existing == null) {
            throw new RuntimeException("Équipement non trouvé");
        }
        existing.setNom(equipement.getNom());
        return equipementRepository.save(existing);
    }

    public void deleteEquipement(Long id) {
        equipementRepository.deleteById(id);
    }

    public void lierEquipement(Long equipementId, SalleEquipement se) {
        Equipement equipement = equipementRepository.findById(equipementId)
                .orElseThrow(() -> new RuntimeException("Equipement non trouvé"));

        equipement.lierEquipement(se);

        equipementRepository.save(equipement);
    }
}
