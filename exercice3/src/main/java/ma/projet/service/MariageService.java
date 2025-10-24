package ma.projet.service;

import ma.projet.beans.Mariage;
import ma.projet.dao.IDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MariageService implements IDao<Mariage> {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public boolean create(Mariage o) {
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
    public boolean update(Mariage o) {
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
    public boolean delete(Mariage o) {
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
    public Mariage findById(int id) {
        try {
            Session session = sessionFactory.getCurrentSession();
            return session.get(Mariage.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Mariage> findAll() {
        try {
            Session session = sessionFactory.getCurrentSession();
            return session.createQuery("FROM Mariage", Mariage.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
