package ma.projet.test;

import ma.projet.classes.Categorie;
import ma.projet.classes.Commande;
import ma.projet.classes.LigneCommandeProduit;
import ma.projet.classes.Produit;
import ma.projet.service.CategorieService;
import ma.projet.service.CommandeService;
import ma.projet.service.LigneCommandeService;
import ma.projet.service.ProduitService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TestApplication {

    public static void main(String[] args) {
        // Charger le contexte Spring
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        // Récupérer les services
        CategorieService categorieService = context.getBean(CategorieService.class);
        ProduitService produitService = context.getBean(ProduitService.class);
        CommandeService commandeService = context.getBean(CommandeService.class);
        LigneCommandeService ligneCommandeService = context.getBean(LigneCommandeService.class);

        try {
            System.out.println("=== TEST 1: Création des catégories ===");
            Categorie cat1 = new Categorie("CAT1", "Ordinateurs");
            Categorie cat2 = new Categorie("CAT2", "Périphériques");
            Categorie cat3 = new Categorie("CAT3", "Composants");

            categorieService.create(cat1);
            categorieService.create(cat2);
            categorieService.create(cat3);
            System.out.println("Catégories créées avec succès!");

            System.out.println("\n=== TEST 2: Création des produits ===");
            Produit p1 = new Produit("ES12", 120f, cat1);
            Produit p2 = new Produit("ZR85", 100f, cat2);
            Produit p3 = new Produit("EE85", 200f, cat3);
            Produit p4 = new Produit("AB45", 150f, cat1);
            Produit p5 = new Produit("CD78", 80f, cat2);

            produitService.create(p1);
            produitService.create(p2);
            produitService.create(p3);
            produitService.create(p4);
            produitService.create(p5);
            System.out.println("Produits créés avec succès!");

            System.out.println("\n=== TEST 3: Afficher les produits par catégorie ===");
            List<Produit> produitsCategorie1 = produitService.findByCategorie(cat1);
            System.out.println("Produits de la catégorie " + cat1.getLibelle() + ":");
            for (Produit p : produitsCategorie1) {
                System.out.println("  - " + p.getReference() + " : " + p.getPrix() + " DH");
            }

            System.out.println("\n=== TEST 4: Création des commandes ===");
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Commande cmd1 = new Commande(sdf.parse("14/03/2013"));
            Commande cmd2 = new Commande(sdf.parse("20/04/2013"));
            Commande cmd3 = new Commande(sdf.parse("15/05/2013"));

            commandeService.create(cmd1);
            commandeService.create(cmd2);
            commandeService.create(cmd3);
            System.out.println("Commandes créées avec succès!");

            System.out.println("\n=== TEST 5: Création des lignes de commande ===");
            LigneCommandeProduit lc1 = new LigneCommandeProduit(cmd1, p1, 7);
            LigneCommandeProduit lc2 = new LigneCommandeProduit(cmd1, p2, 14);
            LigneCommandeProduit lc3 = new LigneCommandeProduit(cmd1, p3, 5);
            LigneCommandeProduit lc4 = new LigneCommandeProduit(cmd2, p4, 3);
            LigneCommandeProduit lc5 = new LigneCommandeProduit(cmd3, p5, 10);

            ligneCommandeService.create(lc1);
            ligneCommandeService.create(lc2);
            ligneCommandeService.create(lc3);
            ligneCommandeService.create(lc4);
            ligneCommandeService.create(lc5);
            System.out.println("Lignes de commande créées avec succès!");

            System.out.println("\n=== TEST 6: Afficher les produits commandés dans une commande ===");
            System.out.println("Commande : " + cmd1.getId() + "     Date : 14 Mars 2013");
            System.out.println("Liste des produits :");
            System.out.println("Référence\tPrix\t\tQuantité");
            
            List<Object[]> produitsCommande = produitService.findProduitsParCommande(cmd1.getId());
            for (Object[] row : produitsCommande) {
                String reference = (String) row[0];
                float prix = (float) row[1];
                int quantite = (int) row[2];
                System.out.println(reference + "\t\t" + prix + " DH\t" + quantite);
            }

            System.out.println("\n=== TEST 7: Produits commandés entre deux dates ===");
            Date dateDebut = sdf.parse("01/03/2013");
            Date dateFin = sdf.parse("30/04/2013");
            
            List<Produit> produitsEntreDates = produitService.findProduitsCommandesEntreDates(dateDebut, dateFin);
            System.out.println("Produits commandés entre " + sdf.format(dateDebut) + " et " + sdf.format(dateFin) + ":");
            for (Produit p : produitsEntreDates) {
                System.out.println("  - " + p.getReference() + " : " + p.getPrix() + " DH");
            }

            System.out.println("\n=== TEST 8: Produits dont le prix est supérieur à 100 DH (requête nommée) ===");
            List<Produit> produitsChers = produitService.findProduitsPrixSuperieur(100f);
            System.out.println("Produits avec prix > 100 DH:");
            for (Produit p : produitsChers) {
                System.out.println("  - " + p.getReference() + " : " + p.getPrix() + " DH (" + p.getCategorie().getLibelle() + ")");
            }

            System.out.println("\n=== Tous les tests ont été exécutés avec succès! ===");

        } catch (Exception e) {
            System.err.println("Erreur lors de l'exécution des tests:");
            e.printStackTrace();
        }

        // Fermer le contexte
        ((ClassPathXmlApplicationContext) context).close();
    }
}
