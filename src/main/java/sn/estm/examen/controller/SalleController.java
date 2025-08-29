package sn.estm.examen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sn.estm.examen.entites.Salle;
import sn.estm.examen.entites.SalleEquipement;
import sn.estm.examen.metier.EquipementService;
import sn.estm.examen.metier.SalleEquipementService;
import sn.estm.examen.metier.SalleService;

import java.time.LocalDateTime;
import java.util.List;


@Controller
@RequestMapping("/salles")
public class SalleController {

    @Autowired
    private SalleService salleService;

    @Autowired
    private EquipementService equipementService;

    @Autowired
    private SalleEquipementService salleEquipementService;

    @GetMapping
    public String listSalles(@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateDebut,
                             @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateFin,
                             Model model) {
        List<Salle> salles;
        if (dateDebut != null && dateFin != null) {
            salles = salleService.getSallesDisponibles(dateDebut, dateFin);
        } else {
            salles = salleService.getAllSalles();
        }
        model.addAttribute("salles", salles);
        model.addAttribute("dateDebut", dateDebut);
        model.addAttribute("dateFin", dateFin);
        return "salles/list";
    }


    // Détails d’une salle
    @GetMapping("/{id}")
    public String getSalle(@PathVariable Long id, Model model, Object salleRepository) {
        Salle salle = salleService.getSalleById(id);
        model.addAttribute("salle", salle);
        model.addAttribute("salleEquipement", new SalleEquipement());
        model.addAttribute("equipements", equipementService.getAllEquipements());
        return "salles/salle-detail";
    }

    @GetMapping("/form")
    public String showCreateForm(Model model) {
        model.addAttribute("salle", new Salle());
        return "salles/form";
    }

    @GetMapping("/form/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Salle salle = salleService.getSalleById(id);
        model.addAttribute("salle", salle);
        return "salles/form";
    }

    @PostMapping("/save")
    public String saveSalle(@ModelAttribute Salle salle) {
        salleService.createSalle(salle);
        return "redirect:/salles";
    }

    @PostMapping("/update/{id}")
    public String updateSalle(@PathVariable Long id, @ModelAttribute Salle salle) {
        salleService.updateSalle(id, salle);
        return "redirect:/salles";
    }

    @GetMapping("/delete/{id}")
    public String deleteSalle(@PathVariable Long id) {
        salleService.deleteSalle(id);
        return "redirect:/salles";
    }

}