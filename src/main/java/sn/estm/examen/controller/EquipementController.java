package sn.estm.examen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sn.estm.examen.entites.Equipement;
import sn.estm.examen.entites.SalleEquipement;
import sn.estm.examen.metier.EquipementService;
import sn.estm.examen.metier.SalleService;


@Controller
@RequestMapping("/equipements")
public class EquipementController {

    @Autowired
    private EquipementService equipementService;

    @Autowired
    private SalleService salleService;

    @GetMapping
    public String listEquipements(Model model) {
        model.addAttribute("equipements", equipementService.getAllEquipements());
        return "equipements/list";
    }

    // Détails d’une salle
    @GetMapping("/{id}")
    public String getEquipement(@PathVariable Long id, Model model, Object salleRepository) {
        Equipement equipement = equipementService.getEquipementById(id);
        model.addAttribute("equipement", equipement);
        model.addAttribute("salleEquipement", new SalleEquipement());
        model.addAttribute("salles", salleService.getAllSalles());
        return "equipements/equipement-detail";
    }

    @GetMapping("/form")
    public String showCreateForm(Model model) {
        model.addAttribute("equipement", new Equipement());
        return "equipements/form";
    }

    @GetMapping("/form/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Equipement equipement = equipementService.getEquipementById(id);
        model.addAttribute("equipement", equipement);
        return "equipements/form";
    }

    @PostMapping("/save")
    public String saveEquipement(@ModelAttribute Equipement equipement) {
        equipementService.createEquipement(equipement);
        return "redirect:/equipements";
    }

    @PostMapping("/update/{id}")
    public String updateSalle(@PathVariable Long id, @ModelAttribute Equipement equipement) {
        equipementService.updateEquipement(id, equipement);
        return "redirect:/equipements";
    }

    @GetMapping("/delete/{id}")
    public String deleteEquipement(@PathVariable Long id) {
        equipementService.deleteEquipement(id);
        return "redirect:/equipements";
    }
}
