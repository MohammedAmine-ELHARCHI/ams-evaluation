package ma.projet.service;

import ma.projet.classes.Tache;
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
public class TacheService implements IDao<Tache> {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public boolean create(Tache o) {
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
    public boolean update(Tache o) {
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
    public boolean delete(Tache o) {
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
    public Tache findById(int id) {
        try {
            Session session = sessionFactory.getCurrentSession();
            return session.get(Tache.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Tache> findAll() {
        try {
            Session session = sessionFactory.getCurrentSession();
            return session.createQuery("FROM Tache", Tache.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Afficher les tâches dont le prix est supérieur à 1000 DH (requête nommée)
     */
    public List<Tache> findTachesPrixSuperieur(double prixMin) {
        try {
            Session session = sessionFactory.getCurrentSession();
            Query<Tache> query = session.createNamedQuery("findTachesPrixSuperieur", Tache.class);
            query.setParameter("prixMin", prixMin);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Afficher les tâches réalisées entre deux dates
     */
    public List<Tache> findTachesRealiseesEntreDates(Date dateDebut, Date dateFin) {
        try {
            Session session = sessionFactory.getCurrentSession();
            Query<Tache> query = session.createQuery(
                "SELECT DISTINCT t FROM Tache t " +
                "JOIN t.employeTaches et " +
                "WHERE et.dateDebutReelle BETWEEN :dateDebut AND :dateFin " +
                "OR et.dateFinReelle BETWEEN :dateDebut AND :dateFin", Tache.class);
            query.setParameter("dateDebut", dateDebut);
            query.setParameter("dateFin", dateFin);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
