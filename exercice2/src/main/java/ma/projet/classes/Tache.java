package ma.projet.classes;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "tache")
@NamedQueries({
    @NamedQuery(
        name = "findTachesPrixSuperieur",
        query = "SELECT t FROM Tache t WHERE t.prix > :prixMin"
    )
})
public class Tache {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(nullable = false)
    private String nom;
    
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dateDebut;
    
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dateFin;
    
    @Column(nullable = false)
    private double prix;
    
    @ManyToOne
    @JoinColumn(name = "projet_id", nullable = false)
    private Projet projet;
    
    @OneToMany(mappedBy = "tache", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<EmployeTache> employeTaches;

    public Tache() {
    }

    public Tache(String nom, Date dateDebut, Date dateFin, double prix, Projet projet) {
        this.nom = nom;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.prix = prix;
        this.projet = projet;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public Projet getProjet() {
        return projet;
    }

    public void setProjet(Projet projet) {
        this.projet = projet;
    }

    public List<EmployeTache> getEmployeTaches() {
        return employeTaches;
    }

    public void setEmployeTaches(List<EmployeTache> employeTaches) {
        this.employeTaches = employeTaches;
    }

    @Override
    public String toString() {
        return "Tache{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", dateDebut=" + dateDebut +
                ", dateFin=" + dateFin +
                ", prix=" + prix +
                '}';
    }
}
