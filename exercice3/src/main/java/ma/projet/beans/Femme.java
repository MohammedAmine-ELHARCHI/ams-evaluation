package ma.projet.beans;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "femme")
@NamedQueries({
    @NamedQuery(
        name = "findFemmesMarieesPlusieursfois",
        query = "SELECT f FROM Femme f WHERE (SELECT COUNT(m) FROM Mariage m WHERE m.femme = f) >= 2"
    )
})
@NamedNativeQueries({
    @NamedNativeQuery(
        name = "countEnfantsFemmeEntreDates",
        query = "SELECT SUM(m.nbrEnfant) FROM mariage m " +
                "JOIN femme f ON m.femme_id = f.id " +
                "WHERE f.id = :femmeId AND m.dateDebut BETWEEN :dateDebut AND :dateFin",
        resultClass = Long.class
    )
})
public class Femme extends Personne {
    
    @OneToMany(mappedBy = "femme", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Mariage> mariages;

    public Femme() {
    }

    public Femme(String nom, String prenom, String telephone, String adresse, Date dateNaissance) {
        super(nom, prenom, telephone, adresse, dateNaissance);
    }

    public List<Mariage> getMariages() {
        return mariages;
    }

    public void setMariages(List<Mariage> mariages) {
        this.mariages = mariages;
    }

    @Override
    public String toString() {
        return "Femme{" +
                "id=" + getId() +
                ", nom='" + getNom() + '\'' +
                ", prenom='" + getPrenom() + '\'' +
                '}';
    }
}
