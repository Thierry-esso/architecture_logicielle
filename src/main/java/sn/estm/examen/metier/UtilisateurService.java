package sn.estm.examen.metier;

import org.springframework.stereotype.Service;
import sn.estm.examen.dao.UtilisateurRepository;
import sn.estm.examen.entites.Utilisateur;

import java.util.List;

@Service
public class UtilisateurService {
    private final UtilisateurRepository utilisateurRepository;

    public UtilisateurService(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }

    public List<Utilisateur> getAllUsers() {

        return utilisateurRepository.findAll();
    }

    public Utilisateur getUserById(Long id) {

        return utilisateurRepository.findById(id).orElse(null);
    }

    public Utilisateur createUser(Utilisateur utilisateur) {
        if (utilisateurRepository.existsByEmail(utilisateur.getEmail())) {
            throw new RuntimeException("Email déjà utilisé");
        }
        return utilisateurRepository.save(utilisateur);
    }

    public Utilisateur updateUser(Long id, Utilisateur utilisateur) {
        Utilisateur existing = getUserById(id);
        if (existing == null) {
            throw new RuntimeException("Utilisateur non trouvé");
        }
        existing.setNom(utilisateur.getNom());
        existing.setEmail(utilisateur.getEmail());
        existing.setRole(utilisateur.getRole());
        return utilisateurRepository.save(existing);
    }

    public void deleteUser(Long id) {
        utilisateurRepository.deleteById(id);
    }
}
