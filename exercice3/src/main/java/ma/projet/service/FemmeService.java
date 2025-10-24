package ma.projet.service;

import ma.projet.beans.Femme;
import ma.projet.dao.IDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class FemmeService implements IDao<Femme> {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public boolean create(Femme o) {
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
    public boolean update(Femme o) {
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
    public boolean delete(Femme o) {
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
    public Femme findById(int id) {
        try {
            Session session = sessionFactory.getCurrentSession();
            return session.get(Femme.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Femme> findAll() {
        try {
            Session session = sessionFactory.getCurrentSession();
            return session.createQuery("FROM Femme", Femme.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Trouver la femme la plus âgée
     */
    public Femme findFemmeLaPlusAgee() {
        try {
            List<Femme> femmes = findAll();
            if (femmes == null || femmes.isEmpty()) {
                return null;
            }
            return femmes.stream()
                    .min(Comparator.comparing(Femme::getDateNaissance))
                    .orElse(null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Compter le nombre d'enfants d'une femme entre deux dates (requête native nommée)
     */
    public int countEnfantsFemmeEntreDates(int femmeId, Date dateDebut, Date dateFin) {
        try {
            Session session = sessionFactory.getCurrentSession();
            NativeQuery<Long> query = session.createNamedQuery("countEnfantsFemmeEntreDates", Long.class);
            query.setParameter("femmeId", femmeId);
            query.setParameter("dateDebut", dateDebut);
            query.setParameter("dateFin", dateFin);
            
            Long result = query.uniqueResult();
            return result != null ? result.intValue() : 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * Afficher les femmes mariées au moins deux fois (requête nommée)
     */
    public List<Femme> findFemmesMarieesAuMoinsDeuxFois() {
        try {
            Session session = sessionFactory.getCurrentSession();
            Query<Femme> query = session.createNamedQuery("findFemmesMarieesPlusieursfois", Femme.class);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
