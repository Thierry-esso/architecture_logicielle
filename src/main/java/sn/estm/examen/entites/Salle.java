package sn.estm.examen.entites;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "salles")
public class Salle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nom;

    @Column(nullable = false)
    private int capacite;


    @OneToMany(mappedBy = "salle", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<SalleEquipement> salleEquipements = new HashSet<>();

    public Salle() {
    }

    public Salle(String nom, int capacite) {
        this.nom = nom;
        this.capacite = capacite;
    }

    // --- Méthode utilitaire GRASP Creator ---
    public void ajouterEquipement(SalleEquipement se) {
        salleEquipements.add(se);
        se.setSalle(this);
    }

    public void retirerEquipement(SalleEquipement se) {
        salleEquipements.remove(se);
        se.setSalle(null);
    }

    // --- Getters / Setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getCapacite() {
        return capacite;
    }

    public void setCapacite(int capacite) {
        this.capacite = capacite;
    }

    public Set<SalleEquipement> getSalleEquipements() {
        return salleEquipements;
    }

    public void setSalleEquipements(Set<SalleEquipement> salleEquipements) {
        this.salleEquipements = salleEquipements;
    }
}
