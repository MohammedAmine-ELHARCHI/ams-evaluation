package ma.projet.service;

import ma.projet.classes.LigneCommandeProduit;
import ma.projet.dao.IDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class LigneCommandeService implements IDao<LigneCommandeProduit> {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public boolean create(LigneCommandeProduit o) {
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
    public boolean update(LigneCommandeProduit o) {
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
    public boolean delete(LigneCommandeProduit o) {
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
    public LigneCommandeProduit findById(int id) {
        try {
            Session session = sessionFactory.getCurrentSession();
            return session.get(LigneCommandeProduit.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<LigneCommandeProduit> findAll() {
        try {
            Session session = sessionFactory.getCurrentSession();
            return session.createQuery("FROM LigneCommandeProduit", LigneCommandeProduit.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
