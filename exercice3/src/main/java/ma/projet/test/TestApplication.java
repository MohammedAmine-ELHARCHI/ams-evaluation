package ma.projet.test;

import ma.projet.beans.Femme;
import ma.projet.beans.Homme;
import ma.projet.beans.Mariage;
import ma.projet.service.FemmeService;
import ma.projet.service.HommeService;
import ma.projet.service.MariageService;
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
        HommeService hommeService = context.getBean(HommeService.class);
        FemmeService femmeService = context.getBean(FemmeService.class);
        MariageService mariageService = context.getBean(MariageService.class);

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            System.out.println("=== TEST 1: Création de 10 femmes ===");
            Femme f1 = new Femme("RAMI", "SALIMA", "0611111111", "Casablanca", sdf.parse("15/05/1970"));
            Femme f2 = new Femme("ALI", "AMAL", "0622222222", "Rabat", sdf.parse("20/08/1975"));
            Femme f3 = new Femme("ALAOUI", "WAFA", "0633333333", "Fes", sdf.parse("10/03/1978"));
            Femme f4 = new Femme("ALAMI", "KARIMA", "0644444444", "Marrakech", sdf.parse("25/12/1968"));
            Femme f5 = new Femme("BENNIS", "SAMIRA", "0655555555", "Tanger", sdf.parse("30/07/1980"));
            Femme f6 = new Femme("CHAKIR", "FATIMA", "0666666666", "Agadir", sdf.parse("18/11/1972"));
            Femme f7 = new Femme("DAKIR", "LAILA", "0677777777", "Kenitra", sdf.parse("05/02/1985"));
            Femme f8 = new Femme("ELHAJ", "NADIA", "0688888888", "Meknes", sdf.parse("22/09/1976"));
            Femme f9 = new Femme("FAHMI", "ZINEB", "0699999999", "Oujda", sdf.parse("14/04/1982"));
            Femme f10 = new Femme("GHAZI", "HOUDA", "0600000000", "Tetouan", sdf.parse("08/06/1979"));

            femmeService.create(f1);
            femmeService.create(f2);
            femmeService.create(f3);
            femmeService.create(f4);
            femmeService.create(f5);
            femmeService.create(f6);
            femmeService.create(f7);
            femmeService.create(f8);
            femmeService.create(f9);
            femmeService.create(f10);
            System.out.println("10 femmes créées avec succès!");

            System.out.println("\n=== TEST 2: Création de 5 hommes ===");
            Homme h1 = new Homme("SAFI", "SAID", "0711111111", "Casablanca", sdf.parse("12/01/1965"));
            Homme h2 = new Homme("BENNANI", "KHALID", "0722222222", "Rabat", sdf.parse("28/03/1970"));
            Homme h3 = new Homme("IDRISSI", "YOUSSEF", "0733333333", "Fes", sdf.parse("17/07/1968"));
            Homme h4 = new Homme("TAZI", "OMAR", "0744444444", "Marrakech", sdf.parse("03/11/1975"));
            Homme h5 = new Homme("ZIANI", "HICHAM", "0755555555", "Tanger", sdf.parse("21/09/1972"));

            hommeService.create(h1);
            hommeService.create(h2);
            hommeService.create(h3);
            hommeService.create(h4);
            hommeService.create(h5);
            System.out.println("5 hommes créés avec succès!");

            System.out.println("\n=== TEST 3: Création des mariages ===");
            // Mariages de SAFI SAID (h1) - 4 mariages pour tester le cas de 4 femmes
            Mariage m1 = new Mariage(sdf.parse("03/09/1989"), sdf.parse("03/09/1990"), 0, h1, f4);
            Mariage m2 = new Mariage(sdf.parse("03/09/1990"), null, 4, h1, f1);
            Mariage m3 = new Mariage(sdf.parse("03/09/1995"), null, 2, h1, f2);
            Mariage m4 = new Mariage(sdf.parse("04/11/2000"), null, 3, h1, f3);

            // Mariages pour tester les femmes mariées plusieurs fois
            Mariage m5 = new Mariage(sdf.parse("10/06/1992"), sdf.parse("15/08/1995"), 1, h2, f5);
            Mariage m6 = new Mariage(sdf.parse("20/09/1996"), null, 2, h3, f5);

            Mariage m7 = new Mariage(sdf.parse("15/03/1993"), sdf.parse("10/07/1998"), 0, h4, f6);
            Mariage m8 = new Mariage(sdf.parse("25/11/2000"), null, 3, h5, f6);

            // Mariages supplémentaires
            Mariage m9 = new Mariage(sdf.parse("12/04/1994"), null, 2, h2, f7);
            Mariage m10 = new Mariage(sdf.parse("08/08/1997"), null, 1, h3, f8);
            Mariage m11 = new Mariage(sdf.parse("19/02/1999"), null, 2, h4, f9);
            Mariage m12 = new Mariage(sdf.parse("30/05/2001"), null, 1, h5, f10);

            mariageService.create(m1);
            mariageService.create(m2);
            mariageService.create(m3);
            mariageService.create(m4);
            mariageService.create(m5);
            mariageService.create(m6);
            mariageService.create(m7);
            mariageService.create(m8);
            mariageService.create(m9);
            mariageService.create(m10);
            mariageService.create(m11);
            mariageService.create(m12);
            System.out.println("Mariages créés avec succès!");

            System.out.println("\n=== TEST 4: Afficher la liste des femmes ===");
            List<Femme> femmes = femmeService.findAll();
            System.out.println("Liste des femmes:");
            for (Femme f : femmes) {
                System.out.println("  - " + f.getNom() + " " + f.getPrenom() + 
                                 " (Née le: " + sdf.format(f.getDateNaissance()) + ")");
            }

            System.out.println("\n=== TEST 5: Afficher la femme la plus âgée ===");
            Femme femmePlusAgee = femmeService.findFemmeLaPlusAgee();
            if (femmePlusAgee != null) {
                System.out.println("Femme la plus âgée: " + femmePlusAgee.getNom() + " " + 
                                 femmePlusAgee.getPrenom() + " (Née le: " + 
                                 sdf.format(femmePlusAgee.getDateNaissance()) + ")");
            }

            System.out.println("\n=== TEST 6: Afficher les épouses d'un homme entre deux dates ===");
            Date dateDebut = sdf.parse("01/01/1990");
            Date dateFin = sdf.parse("31/12/1999");
            List<Femme> epouses = hommeService.findEpousesEntreDeuxDates(h1.getId(), dateDebut, dateFin);
            System.out.println("Épouses de " + h1.getNom() + " " + h1.getPrenom() + 
                             " entre " + sdf.format(dateDebut) + " et " + sdf.format(dateFin) + ":");
            for (Femme f : epouses) {
                System.out.println("  - " + f.getNom() + " " + f.getPrenom());
            }

            System.out.println("\n=== TEST 7: Nombre d'enfants d'une femme entre deux dates ===");
            Date dateDebut2 = sdf.parse("01/01/1989");
            Date dateFin2 = sdf.parse("31/12/2005");
            int nbrEnfants = femmeService.countEnfantsFemmeEntreDates(f1.getId(), dateDebut2, dateFin2);
            System.out.println("Nombre d'enfants de " + f1.getNom() + " " + f1.getPrenom() + 
                             " entre " + sdf.format(dateDebut2) + " et " + sdf.format(dateFin2) + 
                             ": " + nbrEnfants);

            System.out.println("\n=== TEST 8: Femmes mariées au moins deux fois ===");
            List<Femme> femmesMarieesDeuxFois = femmeService.findFemmesMarieesAuMoinsDeuxFois();
            System.out.println("Femmes mariées au moins 2 fois:");
            for (Femme f : femmesMarieesDeuxFois) {
                System.out.println("  - " + f.getNom() + " " + f.getPrenom());
            }

            System.out.println("\n=== TEST 9: Hommes mariés à 4 femmes entre deux dates ===");
            Date dateDebut3 = sdf.parse("01/01/1989");
            Date dateFin3 = sdf.parse("31/12/2005");
            int nbrHommes = hommeService.countHommesMarieA4FemmesEntreDates(dateDebut3, dateFin3);
            System.out.println("Nombre d'hommes mariés à 4 femmes entre " + 
                             sdf.format(dateDebut3) + " et " + sdf.format(dateFin3) + ": " + nbrHommes);

            System.out.println("\n=== TEST 10: Afficher les mariages d'un homme avec tous les détails ===");
            Homme homme = hommeService.findById(h1.getId());
            List<Mariage> mariages = hommeService.findMariagesByHomme(h1.getId());
            
            System.out.println("Nom : " + homme.getNom() + " " + homme.getPrenom());
            
            // Séparer les mariages en cours et échoués
            System.out.println("\nMariages En Cours :");
            int compteurEnCours = 1;
            for (Mariage m : mariages) {
                if (m.getDateFin() == null) {
                    System.out.println(compteurEnCours + ". Femme : " + m.getFemme().getPrenom() + 
                                     " " + m.getFemme().getNom() + 
                                     "   Date Début : " + sdf.format(m.getDateDebut()) + 
                                     "    Nbr Enfants : " + m.getNbrEnfant());
                    compteurEnCours++;
                }
            }
            
            System.out.println("\nMariages échoués :");
            int compteurEchoue = 1;
            for (Mariage m : mariages) {
                if (m.getDateFin() != null) {
                    System.out.println(compteurEchoue + ". Femme : " + m.getFemme().getPrenom() + 
                                     " " + m.getFemme().getNom() + 
                                     "  Date Début : " + sdf.format(m.getDateDebut()));
                    System.out.println("   Date Fin : " + sdf.format(m.getDateFin()) + 
                                     "    Nbr Enfants : " + m.getNbrEnfant());
                    compteurEchoue++;
                }
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
