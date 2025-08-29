package sn.estm.examen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sn.estm.examen.entites.Reservation;
import sn.estm.examen.metier.ReservationService;
import sn.estm.examen.metier.SalleService;
import sn.estm.examen.metier.UtilisateurService;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

@Controller
@RequestMapping("/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private UtilisateurService utilisateurService;
    @Autowired
    private SalleService salleService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    // Afficher toutes les réservations
    @GetMapping
    public String getAllReservations(Model model) {
        List<Reservation> reservations = reservationService.getAllReservations();
        model.addAttribute("reservations", reservations);
        return "reservations/list"; // Vue pour toutes les réservations
    }

    @PostMapping
    public String saveReservation(@ModelAttribute Reservation reservation, RedirectAttributes redirectAttributes) throws ReservationService.ReservationException {
        try {
            reservationService.createReservation(reservation);
            redirectAttributes.addFlashAttribute("successMessage", "Réservation enregistrée !");
        } catch (ReservationService.ReservationException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }

        return "redirect:/reservations";
    }

    // Afficher les réservations d'un utilisateur
    @GetMapping("/user/{userId}")
    public String getReservationsByUser(@PathVariable Long userId, Model model) {
        List<Reservation> reservations = reservationService.getReservationsByUser(userId);
        model.addAttribute("reservations", reservations);
        model.addAttribute("userId", userId);
        return "reservations/user-reservation";
    }

    // Afficher les réservations d'une salle
    @GetMapping("/salle/{salleId}")
    public String getReservationsBySalle(@PathVariable Long salleId, Model model) {
        List<Reservation> reservations = reservationService.getReservationsBySalle(salleId);
        model.addAttribute("reservations", reservations);
        model.addAttribute("salleId", salleId);
        return "reservations/salle-reservation";
    }

    // Formulaire pour créer une réservation
    @GetMapping("/form")
    public String showCreateForm(Model model) {
        model.addAttribute("reservation", new Reservation());
        model.addAttribute("utilisateurs", utilisateurService.getAllUsers());
        model.addAttribute("salles", salleService.getAllSalles());
        return "reservations/create-reservation";
    }


    // Formulaire pour mettre à jour une réservation
    @GetMapping("/form/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Reservation reservation = reservationService.getReservationById(id);
        if (reservation == null) {
            return "redirect:/reservations";
        }
        model.addAttribute("reservation", reservation);
        model.addAttribute("utilisateurs", utilisateurService.getAllUsers());
        model.addAttribute("salles", salleService.getAllSalles());

        return "reservations/create-reservation";
    }

    // Mettre à jour une réservation
    @PostMapping("/update/{id}")
    public String updateReservation(@PathVariable Long id, @ModelAttribute Reservation reservation) {
        reservationService.updateReservation(id, reservation);
        return "redirect:/reservations";
    }

    // Annuler une réservation
    @GetMapping("/cancel/{id}")
    public String cancelReservation(@PathVariable Long id, HttpServletRequest request) {
        reservationService.cancelReservation(id);
        // Récupérer l'URL de la page précédente
        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }
}
