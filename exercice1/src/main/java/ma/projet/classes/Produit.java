package ma.projet.classes;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "produit")
@NamedQueries({
    @NamedQuery(
        name = "findProduitsPrixSuperieur",
        query = "SELECT p FROM Produit p WHERE p.prix > :prixMin"
    )
})
public class Produit {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(nullable = false, unique = true)
    private String reference;
    
    @Column(nullable = false)
    private float prix;
    
    @ManyToOne
    @JoinColumn(name = "categorie_id", nullable = false)
    private Categorie categorie;
    
    @OneToMany(mappedBy = "produit", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<LigneCommandeProduit> ligneCommandes;

    public Produit() {
    }

    public Produit(String reference, float prix, Categorie categorie) {
        this.reference = reference;
        this.prix = prix;
        this.categorie = categorie;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public List<LigneCommandeProduit> getLigneCommandes() {
        return ligneCommandes;
    }

    public void setLigneCommandes(List<LigneCommandeProduit> ligneCommandes) {
        this.ligneCommandes = ligneCommandes;
    }

    @Override
    public String toString() {
        return "Produit{" +
                "id=" + id +
                ", reference='" + reference + '\'' +
                ", prix=" + prix +
                ", categorie=" + (categorie != null ? categorie.getLibelle() : "null") +
                '}';
    }
}
