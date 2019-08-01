package vn.vissoft.employee.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import vn.vissoft.employee.model.User;
import vn.vissoft.employee.service.Employee;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;


@Repository
public class UserRepositoryImpl implements UserRepository{


    @PersistenceContext
    EntityManager entityManager;


    @Override
    public List<User> findAll() {

        Query query = entityManager.createQuery("select e FROM User e");
        return query.getResultList();

    }

    @Override
    public List findNames() {
        Query query = entityManager.createQuery("select e.username FROM Employee e");
        return query.getResultList();
    }

    @Override
    public User findById(Long id) {
        Query query = entityManager.createQuery("select e FROM User e WHERE e.id= :id");
        query.setParameter("id", id);
        return (User) query.getSingleResult();
    }
    @Override
    public User findByName(String name) {
        Query query = entityManager.createQuery("select e FROM User e WHERE e.username= :username");
        query.setParameter("username", name);
        return (User) query.getSingleResult();
    }

    @Override
    @Transactional
    public void create(User user) {
        this.entityManager.persist(user);

    }


    @Override
    public void update(User user) {
        entityManager.merge(user);

    }

    @Override
    public User findByUsername(String username) {
        Query query = entityManager.createQuery("select e FROM User e WHERE e.username= :username");
        query.setParameter("username", username);
        return (User) query.getSingleResult();
    }

    @Override
    public void delete(User user) {
        entityManager.remove(entityManager.merge(user));

    }
}
