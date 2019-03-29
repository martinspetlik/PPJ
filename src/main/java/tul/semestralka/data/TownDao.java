package tul.semestralka.data;


import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Transactional
public class TownDao {

    @Autowired
    private SessionFactory sessionFactory;

    public Session session() {
        return sessionFactory.getCurrentSession();
    }


    public List<Town> getTowns() {
        Criteria crit = session().createCriteria(Town.class);
        return crit.list();
    }

    public boolean update(Town town) {
        boolean success = false;
        try {
            // obtaining session is omitted
            session().update(town);
            success = true;

        } catch (Exception e) {
            System.out.println(e.getMessage());

        }
        return success;
    }

    public boolean create(Town town) {
        boolean success = false;
        try {
            // obtaining session is omitted
            session().save(town);
            success = true;

        } catch (Exception e) {
            System.out.println(e.getMessage());

        }
        return success;
    }


    /**
     * Delete specific item from table town
     * @param id town id
     * @return bool
     */
    public void delete(int id) {
        Town town = (Town ) session().createCriteria(Town.class)
                .add(Restrictions.eq("id", id)).uniqueResult();
        session().delete(town);
    }

    /**
     * Delete all towns from table TOWN
     */
    public void deleteTowns() {
        session().createQuery("delete from town").executeUpdate();
    }


    public Town getTown(int townId) {
        Criteria crit = session().createCriteria(Town.class);
        crit.add(Restrictions.idEq(townId));

        return (Town) crit.uniqueResult();
    }
}
