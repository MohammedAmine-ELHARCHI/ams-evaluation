package ma.projet.test;

import ma.projet.classes.Employe;
import ma.projet.classes.EmployeTache;
import ma.projet.classes.Projet;
import ma.projet.classes.Tache;
import ma.projet.service.EmployeService;
import ma.projet.service.EmployeTacheService;
import ma.projet.service.ProjetService;
import ma.projet.service.TacheService;
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
        EmployeService employeService = context.getBean(EmployeService.class);
        ProjetService projetService = context.getBean(ProjetService.class);
        TacheService tacheService = context.getBean(TacheService.class);
        EmployeTacheService employeTacheService = context.getBean(EmployeTacheService.class);

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            System.out.println("=== TEST 1: Création des employés ===");
            Employe emp1 = new Employe("Alami", "Ahmed", "0612345678");
            Employe emp2 = new Employe("Bennani", "Fatima", "0623456789");
            Employe emp3 = new Employe("Cherki", "Mohamed", "0634567890");

            employeService.create(emp1);
            employeService.create(emp2);
            employeService.create(emp3);
            System.out.println("Employés créés avec succès!");

            System.out.println("\n=== TEST 2: Création des projets ===");
            Projet proj1 = new Projet("Gestion de stock", 
                                     sdf.parse("14/01/2013"), 
                                     sdf.parse("30/06/2013"), 
                                     emp1);
            Projet proj2 = new Projet("Application mobile", 
                                     sdf.parse("01/03/2013"), 
                                     sdf.parse("31/08/2013"), 
                                     emp2);
            Projet proj3 = new Projet("Site web e-commerce", 
                                     sdf.parse("15/02/2013"), 
                                     sdf.parse("30/09/2013"), 
                                     emp1);

            projetService.create(proj1);
            projetService.create(proj2);
            projetService.create(proj3);
            System.out.println("Projets créés avec succès!");

            System.out.println("\n=== TEST 3: Création des tâches ===");
            // Tâches pour projet 1
            Tache tache1 = new Tache("Analyse", 
                                    sdf.parse("15/01/2013"), 
                                    sdf.parse("30/01/2013"), 
                                    1500.0, 
                                    proj1);
            Tache tache2 = new Tache("Conception", 
                                    sdf.parse("01/02/2013"), 
                                    sdf.parse("20/02/2013"), 
                                    1200.0, 
                                    proj1);
            Tache tache3 = new Tache("Développement", 
                                    sdf.parse("01/03/2013"), 
                                    sdf.parse("30/04/2013"), 
                                    2500.0, 
                                    proj1);
            
            // Tâches pour projet 2
            Tache tache4 = new Tache("Design UI", 
                                    sdf.parse("05/03/2013"), 
                                    sdf.parse("20/03/2013"), 
                                    800.0, 
                                    proj2);
            Tache tache5 = new Tache("Développement mobile", 
                                    sdf.parse("25/03/2013"), 
                                    sdf.parse("30/05/2013"), 
                                    3000.0, 
                                    proj2);

            tacheService.create(tache1);
            tacheService.create(tache2);
            tacheService.create(tache3);
            tacheService.create(tache4);
            tacheService.create(tache5);
            System.out.println("Tâches créées avec succès!");

            System.out.println("\n=== TEST 4: Affectation des employés aux tâches ===");
            EmployeTache et1 = new EmployeTache(emp2, tache1, 
                                               sdf.parse("10/02/2013"), 
                                               sdf.parse("20/02/2013"));
            EmployeTache et2 = new EmployeTache(emp3, tache2, 
                                               sdf.parse("10/03/2013"), 
                                               sdf.parse("15/03/2013"));
            EmployeTache et3 = new EmployeTache(emp2, tache3, 
                                               sdf.parse("10/04/2013"), 
                                               sdf.parse("25/04/2013"));
            EmployeTache et4 = new EmployeTache(emp3, tache4, 
                                               sdf.parse("10/03/2013"), 
                                               sdf.parse("18/03/2013"));
            EmployeTache et5 = new EmployeTache(emp2, tache5, 
                                               sdf.parse("01/04/2013"), 
                                               sdf.parse("25/05/2013"));

            employeTacheService.create(et1);
            employeTacheService.create(et2);
            employeTacheService.create(et3);
            employeTacheService.create(et4);
            employeTacheService.create(et5);
            System.out.println("Affectations créées avec succès!");

            System.out.println("\n=== TEST 5: Tâches réalisées par un employé ===");
            List<Tache> tachesEmp2 = employeService.findTachesRealiseesByEmploye(emp2.getId());
            System.out.println("Tâches réalisées par " + emp2.getNom() + " " + emp2.getPrenom() + ":");
            for (Tache t : tachesEmp2) {
                System.out.println("  - " + t.getNom() + " (Projet: " + t.getProjet().getNom() + ", Prix: " + t.getPrix() + " DH)");
            }

            System.out.println("\n=== TEST 6: Projets gérés par un employé ===");
            List<Projet> projetsEmp1 = employeService.findProjetsGeresByEmploye(emp1.getId());
            System.out.println("Projets gérés par " + emp1.getNom() + " " + emp1.getPrenom() + ":");
            for (Projet p : projetsEmp1) {
                System.out.println("  - " + p.getNom() + " (Du " + sdf.format(p.getDateDebut()) + 
                                 " au " + sdf.format(p.getDateFin()) + ")");
            }

            System.out.println("\n=== TEST 7: Tâches planifiées pour un projet ===");
            List<Tache> tachesProj1 = projetService.findTachesPlanifieesByProjet(proj1.getId());
            System.out.println("Tâches planifiées pour le projet '" + proj1.getNom() + "':");
            for (Tache t : tachesProj1) {
                System.out.println("  - " + t.getNom() + " (Du " + sdf.format(t.getDateDebut()) + 
                                 " au " + sdf.format(t.getDateFin()) + ", Prix: " + t.getPrix() + " DH)");
            }

            System.out.println("\n=== TEST 8: Tâches réalisées avec dates réelles ===");
            System.out.println("Projet : " + proj1.getId() + "      Nom : " + proj1.getNom() + 
                             "     Date début : 14 Janvier 2013");
            System.out.println("Liste des tâches:");
            System.out.println("Num\tNom\t\t\tDate Début Réelle\tDate Fin Réelle");
            
            List<Object[]> tachesRealisees = projetService.findTachesRealiseesByProjet(proj1.getId());
            for (Object[] row : tachesRealisees) {
                int id = (int) row[0];
                String nom = (String) row[1];
                Date dateDebut = (Date) row[2];
                Date dateFin = (Date) row[3];
                System.out.println(id + "\t" + nom + "\t\t" + sdf.format(dateDebut) + "\t\t" + sdf.format(dateFin));
            }

            System.out.println("\n=== TEST 9: Tâches avec prix supérieur à 1000 DH (requête nommée) ===");
            List<Tache> tachesCheRes = tacheService.findTachesPrixSuperieur(1000.0);
            System.out.println("Tâches dont le prix est > 1000 DH:");
            for (Tache t : tachesCheRes) {
                System.out.println("  - " + t.getNom() + " : " + t.getPrix() + " DH (Projet: " + 
                                 t.getProjet().getNom() + ")");
            }

            System.out.println("\n=== TEST 10: Tâches réalisées entre deux dates ===");
            Date dateDebut = sdf.parse("01/03/2013");
            Date dateFin = sdf.parse("30/04/2013");
            
            List<Tache> tachesEntreDates = tacheService.findTachesRealiseesEntreDates(dateDebut, dateFin);
            System.out.println("Tâches réalisées entre " + sdf.format(dateDebut) + " et " + sdf.format(dateFin) + ":");
            for (Tache t : tachesEntreDates) {
                System.out.println("  - " + t.getNom() + " (Projet: " + t.getProjet().getNom() + 
                                 ", Prix: " + t.getPrix() + " DH)");
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
