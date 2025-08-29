package sn.estm.examen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sn.estm.examen.entites.Utilisateur;
import sn.estm.examen.metier.UtilisateurService;

import java.util.List;

@Controller
@RequestMapping("/utilisateurs")
public class UtilisateurController {

    @Autowired
    private UtilisateurService utilisateurService;

    @GetMapping
    public String listUtilisateurs(Model model) {
        model.addAttribute("utilisateurs", utilisateurService.getAllUsers());
        return "utilisateurs/list";
    }

    @GetMapping("/form")
    public String showCreateForm(Model model) {
        model.addAttribute("utilisateur", new Utilisateur());
        model.addAttribute("roles", List.of("ADMIN", "ETUDIANT", "ENSEIGNANT"));
        return "utilisateurs/form";
    }

    @GetMapping("/form/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Utilisateur utilisateur = utilisateurService.getUserById(id);
        model.addAttribute("utilisateur", utilisateur);
        model.addAttribute("roles", List.of("ADMIN", "ETUDIANT", "ENSEIGNANT"));
        return "utilisateurs/form";
    }

    @PostMapping("/save")
    public String saveUtilisateur(@ModelAttribute Utilisateur utilisateur) {
        utilisateurService.createUser(utilisateur);
        return "redirect:/utilisateurs";
    }

    @PostMapping("/update/{id}")
    public String updateUtilisateur(@PathVariable Long id, @ModelAttribute Utilisateur utilisateur) {
        utilisateurService.updateUser(id, utilisateur);
        return "redirect:/utilisateurs";
    }

    @GetMapping("/delete/{id}")
    public String deleteUtilisateur(@PathVariable Long id) {
        utilisateurService.deleteUser(id);
        return "redirect:/utilisateurs";
    }
}
