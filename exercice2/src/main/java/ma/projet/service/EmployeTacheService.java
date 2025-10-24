package ma.projet.service;

import ma.projet.classes.EmployeTache;
import ma.projet.dao.IDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class EmployeTacheService implements IDao<EmployeTache> {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public boolean create(EmployeTache o) {
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
    public boolean update(EmployeTache o) {
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
    public boolean delete(EmployeTache o) {
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
    public EmployeTache findById(int id) {
        try {
            Session session = sessionFactory.getCurrentSession();
            return session.get(EmployeTache.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<EmployeTache> findAll() {
        try {
            Session session = sessionFactory.getCurrentSession();
            return session.createQuery("FROM EmployeTache", EmployeTache.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
