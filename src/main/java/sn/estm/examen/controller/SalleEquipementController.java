package sn.estm.examen.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sn.estm.examen.entites.SalleEquipement;
import sn.estm.examen.metier.EquipementService;
import sn.estm.examen.metier.SalleEquipementService;
import sn.estm.examen.metier.SalleService;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/salle-equipements")
public class SalleEquipementController {

    @Autowired
    private SalleEquipementService salleEquipementService;

    @Autowired
    private SalleService salleService;

    @Autowired
    private EquipementService equipementService;

    public SalleEquipementController(SalleEquipementService salleEquipementService) {
        this.salleEquipementService = salleEquipementService;
    }

    // Sauvegarde d'une assignation
    @PostMapping("/save")
    public String create(@ModelAttribute SalleEquipement salleEquipement) {
        salleEquipementService.assignSalleEquipement(salleEquipement);
        return "redirect:/salles/" + salleEquipement.getSalle().getId();
    }

    // Sauvegarde d'une assignation
    @PostMapping("/saveEqui")
    public String lierEquipement(@ModelAttribute SalleEquipement salleEquipement) {
        salleEquipementService.assignSalleEquipement(salleEquipement);
        return "redirect:/equipements/" + salleEquipement.getEquipement().getId();
    }

   // Liste des assignations actives à une date donnée
    @GetMapping("/actifs")
    public String getActifs(@RequestParam(required = false) String date, Model model, RedirectAttributes redirectAttributes) throws SalleEquipementService.SalleEquipementException {
        try {
            LocalDateTime localDate = (date != null) ? LocalDateTime.parse(date) : LocalDateTime.now();
            List<SalleEquipement> list = salleEquipementService.getActifs(localDate);
            model.addAttribute("salleEquipements", list);
            model.addAttribute("date", localDate);
            redirectAttributes.addFlashAttribute("successMessage", "Réservation enregistrée !");
        } catch (SalleEquipementService.SalleEquipementException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "salle_equipements/actifs";
    }

    // Suppression d'une assignation
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, HttpServletRequest request) {
        salleEquipementService.deleteAssignation(id);
        // Récupérer l'URL de la page précédente
        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }
}
