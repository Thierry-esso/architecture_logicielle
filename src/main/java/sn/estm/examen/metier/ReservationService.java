package sn.estm.examen.metier;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sn.estm.examen.dao.ReservationRepository;
import sn.estm.examen.dao.SalleRepository;
import sn.estm.examen.entites.Reservation;

import java.util.List;

@Service
@Transactional
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final SalleRepository salleRepository;

    // Injection du constructeur
    @Autowired
    public ReservationService(ReservationRepository reservationRepository,
                              SalleRepository salleRepository) {
        this.reservationRepository = reservationRepository;
        this.salleRepository = salleRepository;
    }


    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
        salleRepository = null;
    }

    public List<Reservation> getAllReservations() {

        return reservationRepository.findAll();
    }

    public List<Reservation> getReservationsByUser(Long userId) {
        return reservationRepository.findByUtilisateurId(userId);
    }

    public List<Reservation> getReservationsBySalle(Long salleId) {
        return reservationRepository.findBySalleId(salleId);
    }

    public Reservation createReservation(Reservation reservation) throws ReservationException {
        // Récupère les réservations existantes de la salle
        List<Reservation> existing = reservationRepository.findBySalleId(reservation.getSalle().getId());

        // Vérifie si la nouvelle réservation chevauche une existante
        for (Reservation r : existing) {
            if (r.isDisponible(reservation.getDateDebut()) || r.isDisponible(reservation.getDateFin())) {
                throw new ReservationException("Salle déjà réservée pour cette période !");
            }
        }

        // Sauvegarder la réservation
        return reservationRepository.save(reservation);
    }


    public void cancelReservation(Long id) {
        reservationRepository.deleteById(id);
    }

    public Reservation getReservationById(Long id) {
        return reservationRepository.findById(id).orElse(null);
    }

    public Reservation updateReservation(Long id, Reservation reservation) {
        Reservation existing = getReservationById(id);
        if (existing == null) {
            throw new RuntimeException("Reservation non trouvée");
        }
        existing.setId(reservation.getId());
        existing.setDateDebut(reservation.getDateDebut());
        existing.setDateFin(reservation.getDateFin());
        return reservationRepository.save(existing);
    }

    //Gestion des erreurs
    public class ReservationException extends Exception {
        public ReservationException(String message) {
            super(message);
        }
    }
}
