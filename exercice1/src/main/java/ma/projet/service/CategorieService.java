package ma.projet.service;

import ma.projet.classes.Categorie;
import ma.projet.dao.IDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CategorieService implements IDao<Categorie> {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public boolean create(Categorie o) {
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
    public boolean update(Categorie o) {
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
    public boolean delete(Categorie o) {
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
    public Categorie findById(int id) {
        try {
            Session session = sessionFactory.getCurrentSession();
            return session.get(Categorie.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Categorie> findAll() {
        try {
            Session session = sessionFactory.getCurrentSession();
            return session.createQuery("FROM Categorie", Categorie.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
