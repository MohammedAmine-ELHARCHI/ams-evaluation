package ma.projet.service;

import ma.projet.classes.Categorie;
import ma.projet.classes.Produit;
import ma.projet.dao.IDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class ProduitService implements IDao<Produit> {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public boolean create(Produit o) {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.save(o);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Produit o) {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.update(o);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Produit o) {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.delete(o);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Produit findById(int id) {
        try {
            Session session = sessionFactory.getCurrentSession();
            return session.get(Produit.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Produit> findAll() {
        try {
            Session session = sessionFactory.getCurrentSession();
            return session.createQuery("FROM Produit", Produit.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Afficher la liste des produits par catégorie
     */
    public List<Produit> findByCategorie(Categorie categorie) {
        try {
            Session session = sessionFactory.getCurrentSession();
            Query<Produit> query = session.createQuery(
                "FROM Produit p WHERE p.categorie = :categorie", Produit.class);
            query.setParameter("categorie", categorie);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Afficher les produits commandés entre deux dates
     */
    public List<Produit> findProduitsCommandesEntreDates(Date dateDebut, Date dateFin) {
        try {
            Session session = sessionFactory.getCurrentSession();
            Query<Produit> query = session.createQuery(
                "SELECT DISTINCT p FROM Produit p " +
                "JOIN p.ligneCommandes lc " +
                "JOIN lc.commande c " +
                "WHERE c.date BETWEEN :dateDebut AND :dateFin", Produit.class);
            query.setParameter("dateDebut", dateDebut);
            query.setParameter("dateFin", dateFin);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Afficher les produits commandés dans une commande donnée
     */
    public List<Object[]> findProduitsParCommande(int commandeId) {
        try {
            Session session = sessionFactory.getCurrentSession();
            Query<Object[]> query = session.createQuery(
                "SELECT p.reference, p.prix, lc.quantite " +
                "FROM Produit p " +
                "JOIN p.ligneCommandes lc " +
                "JOIN lc.commande c " +
                "WHERE c.id = :commandeId", Object[].class);
            query.setParameter("commandeId", commandeId);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Afficher la liste des produits dont le prix est supérieur à 100 DH
     * (Requête nommée)
     */
    public List<Produit> findProduitsPrixSuperieur(float prixMin) {
        try {
            Session session = sessionFactory.getCurrentSession();
            Query<Produit> query = session.createNamedQuery("findProduitsPrixSuperieur", Produit.class);
            query.setParameter("prixMin", prixMin);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
