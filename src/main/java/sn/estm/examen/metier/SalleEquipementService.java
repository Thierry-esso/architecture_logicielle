package sn.estm.examen.metier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sn.estm.examen.dao.SalleEquipementRepository;
import sn.estm.examen.entites.SalleEquipement;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SalleEquipementService {

    private final SalleEquipementRepository salleEquipementRepository;

    @Autowired
    public SalleEquipementService(SalleEquipementRepository salleEquipementRepository) {
        this.salleEquipementRepository = salleEquipementRepository;
    }

    public SalleEquipementService() {
        salleEquipementRepository = null;
    }


    public List<SalleEquipement> getActifs(LocalDateTime date) throws SalleEquipementException {
        List<SalleEquipement> actifs = salleEquipementRepository
                .findByDateDebutBeforeAndDateFinAfter(date, date);

        if (actifs == null || actifs.isEmpty()) {
            throw new SalleEquipementException("Aucune assignation active trouvée pour la date : " + date);
        }

        return actifs;
    }

    public SalleEquipement assignSalleEquipement(SalleEquipement salleEquipement) {
        return salleEquipementRepository.save(salleEquipement);
    }

    public void deleteAssignation(Long id) {
        salleEquipementRepository.deleteById(id);
    }


    //Gestion des erreurs
    public class SalleEquipementException extends Exception {
        public SalleEquipementException(String message) {
            super(message);
        }
    }

}
