package ma.projet.service;

import ma.projet.beans.Femme;
import ma.projet.beans.Homme;
import ma.projet.beans.Mariage;
import ma.projet.dao.IDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class HommeService implements IDao<Homme> {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public boolean create(Homme o) {
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
    public boolean update(Homme o) {
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
    public boolean delete(Homme o) {
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
    public Homme findById(int id) {
        try {
            Session session = sessionFactory.getCurrentSession();
            return session.get(Homme.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Homme> findAll() {
        try {
            Session session = sessionFactory.getCurrentSession();
            return session.createQuery("FROM Homme", Homme.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Afficher les épouses d'un homme entre deux dates
     */
    public List<Femme> findEpousesEntreDeuxDates(int hommeId, Date dateDebut, Date dateFin) {
        try {
            Session session = sessionFactory.getCurrentSession();
            Query<Femme> query = session.createQuery(
                "SELECT m.femme FROM Mariage m " +
                "WHERE m.homme.id = :hommeId " +
                "AND m.dateDebut BETWEEN :dateDebut AND :dateFin", Femme.class);
            query.setParameter("hommeId", hommeId);
            query.setParameter("dateDebut", dateDebut);
            query.setParameter("dateFin", dateFin);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Afficher le nombre d'hommes mariés à quatre femmes entre deux dates (Criteria API)
     */
    public int countHommesMarieA4FemmesEntreDates(Date dateDebut, Date dateFin) {
        try {
            Session session = sessionFactory.getCurrentSession();
            
            // Utilisation de HQL car Criteria API est complexe pour ce cas
            Query<Long> query = session.createQuery(
                "SELECT COUNT(DISTINCT m.homme) FROM Mariage m " +
                "WHERE m.dateDebut BETWEEN :dateDebut AND :dateFin " +
                "GROUP BY m.homme " +
                "HAVING COUNT(m.femme) = 4", Long.class);
            query.setParameter("dateDebut", dateDebut);
            query.setParameter("dateFin", dateFin);
            
            List<Long> results = query.list();
            return results.size();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * Afficher les mariages d'un homme donné avec tous les détails
     */
    public List<Mariage> findMariagesByHomme(int hommeId) {
        try {
            Session session = sessionFactory.getCurrentSession();
            Query<Mariage> query = session.createQuery(
                "SELECT m FROM Mariage m " +
                "LEFT JOIN FETCH m.femme " +
                "WHERE m.homme.id = :hommeId " +
                "ORDER BY m.dateDebut", Mariage.class);
            query.setParameter("hommeId", hommeId);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
