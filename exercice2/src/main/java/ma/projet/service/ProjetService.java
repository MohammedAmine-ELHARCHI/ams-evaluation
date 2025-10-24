package ma.projet.service;

import ma.projet.classes.Projet;
import ma.projet.classes.Tache;
import ma.projet.dao.IDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProjetService implements IDao<Projet> {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public boolean create(Projet o) {
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
    public boolean update(Projet o) {
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
    public boolean delete(Projet o) {
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
    public Projet findById(int id) {
        try {
            Session session = sessionFactory.getCurrentSession();
            return session.get(Projet.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Projet> findAll() {
        try {
            Session session = sessionFactory.getCurrentSession();
            return session.createQuery("FROM Projet", Projet.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Afficher la liste des tâches planifiées pour un projet
     */
    public List<Tache> findTachesPlanifieesByProjet(int projetId) {
        try {
            Session session = sessionFactory.getCurrentSession();
            Query<Tache> query = session.createQuery(
                "SELECT t FROM Tache t WHERE t.projet.id = :projetId", Tache.class);
            query.setParameter("projetId", projetId);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Afficher la liste des tâches réalisées avec les dates réelles
     */
    public List<Object[]> findTachesRealiseesByProjet(int projetId) {
        try {
            Session session = sessionFactory.getCurrentSession();
            Query<Object[]> query = session.createQuery(
                "SELECT t.id, t.nom, et.dateDebutReelle, et.dateFinReelle " +
                "FROM Tache t " +
                "JOIN t.employeTaches et " +
                "WHERE t.projet.id = :projetId", Object[].class);
            query.setParameter("projetId", projetId);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
