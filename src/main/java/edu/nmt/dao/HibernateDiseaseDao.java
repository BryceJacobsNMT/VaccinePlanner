package edu.nmt.dao;

import edu.nmt.model.Disease;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Interceptor;
import org.hibernate.HibernateException;

import edu.nmt.model.RepositoryException;
import edu.nmt.model.Population;

/**
 * A <a href="http://www.hibernate.org/">Hibernate</a>
 * data access object for {@link Population}s.
 */
public class HibernateDiseaseDao extends HibernateDao {

    private static Logger LOG = LogManager.getLogger(HibernateDiseaseDao.class);

    /**
     * Constructor.
     *
     * @see HibernateDao
     */
    public HibernateDiseaseDao() {
    }

    /**
     * Constructor
     * @param cfg - name of the hibernate configuration file.
     * @param i - the interceptor.
     * @see HibernateDao
     */
    public HibernateDiseaseDao(String cfg, Interceptor i) {
        super(cfg, i);
    }

    synchronized public Disease getDisease(long id)
            throws RepositoryException {
        try {
            Session s = getSession();

            Disease pop = (Disease) s.get(Disease.class, id);
            return pop;
        } 
        catch (HibernateException he) {
            LOG.warn("Hibernate get failed for disease id: " + id);
            throw new RepositoryException(he);
        }
    }

    /**
     * @param id
     * @return null if a Disease with the provided id doesn't exist.
     * @throws edu.nmt.model.RepositoryException
     */
    synchronized public Disease findById(long id)
            throws RepositoryException {
        try {
            Session s = getSession();

            Disease dis = (Disease) s.get(Disease.class, id);
            return dis;
        } 
        catch (HibernateException he) {
            LOG.warn("Hibernate get failed for Disease id: " + id);
            throw new RepositoryException(he);
        }
    }

    /**
     * @param disName
     * @return null if a Disease with the provided name doesn't exist.
     * @throws edu.nmt.model.RepositoryException
     */
    /*synchronized public Disease findByName(String disName)
            throws RepositoryException {
        try {
            Session s = getSession();

            Query q = s.createQuery("from Disease d where d.name = :code");
            q.setString("name", disName);

            Disease dis = (Disease) q.uniqueResult();
            return dis;
        } 
        catch (HibernateException he) {
            LOG.warn("Hibernate query failed while searching for Disease.name: " + disName);
            throw new RepositoryException(he);
        }
    }*/

    /**
     *
     * @return @throws edu.nmt.model.RepositoryException
     */
    @SuppressWarnings("unchecked")
    synchronized public List<Disease> getAllDiseases()
            throws RepositoryException {
        try {
            Session s = getSession();

            Query q = s.createQuery("from Disease d");

            List<Disease> dises = new ArrayList<>();
            dises.addAll(q.list());

            return dises;
        } 
        catch (HibernateException he) {
            LOG.warn("Hibernate query failed while searching for all Diseases");
            throw new RepositoryException(he);
        }
    }

    /**
     * Saves a disease to the database.
     * @param dis - the disease to save.
     * @throws edu.nmt.model.RepositoryException
     */
    synchronized public void save(Disease dis)
            throws RepositoryException {
        try {
            Session s = getSession();
            s.saveOrUpdate(dis);
        } 
        catch (HibernateException he) {
            LOG.warn("Failed to save Disease: " + dis.getId());
            rollbackTransaction();
            throw new RepositoryException(he);
        }
    }

    /**
     * Deletes a disease by identifier.
     * @param id - an identifier for a disease.
     * @throws edu.nmt.model.RepositoryException
     */
    synchronized public void deleteById(long id)
            throws RepositoryException {
        delete(findById(id));
    }

    /**
     * Silently ignores being passed null and transient objects.
     *
     * @param dis - the disease to delete.
     * @throws edu.nmt.model.RepositoryException
     */
    synchronized public void delete(Disease dis)
            throws RepositoryException {
        if (dis != null) {
            try {
                Session s = getSession();

                //If dis.getId() returns 0, the object has not been persisted
                //yet. I.E. it's transient, not detached or persistent. If it's >
                //0, we can go ahead and just call delete and it'll figure it out
                //for us.
                if (dis.getId() > 0) {
                    s.delete(dis);
                }
            } 
            catch (HibernateException he) {
                LOG.warn("Could not delete Disease: " + dis);
                rollbackTransaction();
                throw new RepositoryException(he);
            }
        }
    }
}
