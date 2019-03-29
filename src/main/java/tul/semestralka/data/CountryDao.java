package tul.semestralka.data;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Transactional
public class CountryDao {

    @Autowired
    private SessionFactory sessionFactory;

    public Session session() {
        return sessionFactory.getCurrentSession();
    }

    public boolean create(Country country) {
            boolean success = false;
            try {
                // obtaining session is omitted
                session().save(country);
                success = true;

            } catch (Exception e) {
                System.out.println(e.getMessage());

            }
            return success;
    }

    public boolean exists(String name) {
        Criteria crit = session().createCriteria(Country.class);

        crit.add(Restrictions.eq("title", name));
        Country cou = (Country) crit.uniqueResult();
        return cou != null;
    }

    public List<Country> getAllCountries() {
        //return session().createQuery("from User").list();
        Criteria crit = session().createCriteria(Country.class);
        return crit.list();
    }

    public void deleteCountries() {
        session().createQuery("delete from Country").executeUpdate();
    }
}
