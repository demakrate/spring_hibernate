package hiber.dao;


import hiber.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;


@Repository
public class UserDaoImp implements UserDao {

    private final SessionFactory sessionFactory;

    public UserDaoImp(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void add(User user) {
        try {
            sessionFactory.getCurrentSession().save(user);
        } catch (ConstraintViolationException e) {
            sessionFactory.getCurrentSession().clear();
            System.out.println("Пользователь с таким номером автомобиля уже существует");
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> listUsers() {
        TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery(
                "select u from User u JOIN fetch u.car", User.class);
        return query.getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public User getUserByCar(String model, int series) {

        TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery(
                "SELECT u FROM User u JOIN fetch u.car p WHERE p.model = :model AND p.series = :series");
        query.setParameter("model", model);
        query.setParameter("series", series);
        if (query.getResultList().equals(new ArrayList<>())) {
            System.out.println("Пользователя с таким автомобилем не существует");
            return (new User("-", "-", "-"));
        } else {
            return query.getResultList().get(0);
        }


    }

}
