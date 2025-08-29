package sn.estm.examen.entites;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "salle_equipements")
public class SalleEquipement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Date de début et fin d'affectation
    @Column(nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateDebut;

    @Column
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateFin;

    // Relation vers Salle
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_salle", nullable = false)
    private Salle salle;

    // Relation vers Equipement
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_equipement", nullable = false)
    private Equipement equipement;

    public SalleEquipement() {
    }

    public SalleEquipement(Salle salle, Equipement equipement, LocalDateTime dateDebut, LocalDateTime dateFin) {
        this.salle = salle;
        this.equipement = equipement;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }

    // --- Méthode métier (GRASP Expert) ---
    public boolean isDisponible(LocalDateTime date) {
        return (dateFin == null || !date.isAfter(dateFin)) && !date.isBefore(dateDebut);
    }

    // --- Getters / Setters ---
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDateTime dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDateTime getDateFin() {
        return dateFin;
    }
    public void setDateFin(LocalDateTime dateFin) {
        this.dateFin = dateFin;
    }

    public Salle getSalle() {
        return salle;
    }
    public void setSalle(Salle salle) {
        this.salle = salle;
    }

    public Equipement getEquipement() {
        return equipement;
    }

    public void setEquipement(Equipement equipement) {
        this.equipement = equipement;
    }
}
