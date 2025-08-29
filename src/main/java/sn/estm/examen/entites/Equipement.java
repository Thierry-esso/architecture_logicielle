package sn.estm.examen.entites;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "equipements")
public class Equipement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    // Association avec SalleEquipement
    @OneToMany(mappedBy = "equipement", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<SalleEquipement> salleEquipements = new HashSet<>();

    // --- Méthode utilitaire (GRASP Créateur) ---
    public void lierEquipement(SalleEquipement se) {
        salleEquipements.add(se);
        se.setEquipement(this);
    }

    public void delierSalle(SalleEquipement se) {
        salleEquipements.remove(se);
        se.setEquipement(null);
    }

    public Equipement() {
    }

    public Equipement(String nom) {
        this.nom = nom;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Set<SalleEquipement> getSalleEquipements() {
        return salleEquipements;
    }

    public void setSalleEquipements(Set<SalleEquipement> salleEquipements) {
        this.salleEquipements = salleEquipements;
    }
}
