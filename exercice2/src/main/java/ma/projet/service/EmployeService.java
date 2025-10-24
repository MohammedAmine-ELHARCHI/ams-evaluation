package ma.projet.service;

import ma.projet.classes.Employe;
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
public class EmployeService implements IDao<Employe> {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public boolean create(Employe o) {
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
    public boolean update(Employe o) {
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
    public boolean delete(Employe o) {
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
    public Employe findById(int id) {
        try {
            Session session = sessionFactory.getCurrentSession();
            return session.get(Employe.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Employe> findAll() {
        try {
            Session session = sessionFactory.getCurrentSession();
            return session.createQuery("FROM Employe", Employe.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Afficher la liste des tâches réalisées par un employé
     */
    public List<Tache> findTachesRealiseesByEmploye(int employeId) {
        try {
            Session session = sessionFactory.getCurrentSession();
            Query<Tache> query = session.createQuery(
                "SELECT t FROM Tache t " +
                "JOIN t.employeTaches et " +
                "JOIN et.employe e " +
                "WHERE e.id = :employeId", Tache.class);
            query.setParameter("employeId", employeId);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Afficher la liste des projets gérés par un employé
     */
    public List<Projet> findProjetsGeresByEmploye(int employeId) {
        try {
            Session session = sessionFactory.getCurrentSession();
            Query<Projet> query = session.createQuery(
                "SELECT p FROM Projet p " +
                "WHERE p.chefProjet.id = :employeId", Projet.class);
            query.setParameter("employeId", employeId);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
