package edu.nmt.dao;

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
import edu.nmt.model.Prioritization;

/**
 * A <a href="http://www.hibernate.org/">Hibernate</a>
 * data access object for {@link Population}s.
 */
public class HibernatePrioritizationDao extends HibernateDao {

    private static Logger LOG = LogManager.getLogger(HibernatePrioritizationDao.class);

    /**
     * Constructor.
     *
     * @see HibernateDao
     */
    public HibernatePrioritizationDao() {
    }

    /**
     * Constructor
     * @param cfg - name of the hibernate configuration file.
     * @param i - the interceptor.
     * @see HibernateDao
     */
    public HibernatePrioritizationDao(String cfg, Interceptor i) {
        super(cfg, i);
    }

    synchronized public Prioritization getPrioritization(long id)
            throws RepositoryException {
        try {
            Session s = getSession();

            Prioritization prior = (Prioritization) s.get(Prioritization.class, id);
            return prior;
        } 
        catch (HibernateException he) {
            LOG.warn("Hibernate get failed for prioritization id: " + id);
            throw new RepositoryException(he);
        }
    }

    /**
     * @param id
     * @return null if a Prioritization with the provided id doesn't exist.
     * @throws edu.nmt.model.RepositoryException
     */
    synchronized public Prioritization findById(long id)
            throws RepositoryException {
        try {
            Session s = getSession();

            Prioritization prior = (Prioritization) s.get(Prioritization.class, id);
            return prior;
        } 
        catch (HibernateException he) {
            LOG.warn("Hibernate get failed for Prioritization id: " + id);
            throw new RepositoryException(he);
        }
    }


    /**
     *
     * @return @throws edu.nmt.model.RepositoryException
     */
    @SuppressWarnings("unchecked")
    synchronized public List<Prioritization> getAllPrioritizations()
            throws RepositoryException {
        try {
            Session s = getSession();

            Query q = s.createQuery("from Prioritization p");

            List<Prioritization> priors = new ArrayList<>();
            priors.addAll(q.list());

            return priors;
        } 
        catch (HibernateException he) {
            LOG.warn("Hibernate query failed while searching for all Prioritizations");
            throw new RepositoryException(he);
        }
    }

    /**
     * Saves a prioritization to the database.
     * @param prior - the prioritization to save.
     * @throws edu.nmt.model.RepositoryException
     */
    synchronized public void save(Prioritization prior)
            throws RepositoryException {
        try {
            Session s = getSession();
            s.saveOrUpdate(prior);
        } 
        catch (HibernateException he) {
            LOG.warn("Failed to save Prioritization: " + prior.getId());
            rollbackTransaction();
            throw new RepositoryException(he);
        }
    }

    /**
     * Deletes a prioritization by identifier.
     * @param id - an identifier for a prioritization.
     * @throws edu.nmt.model.RepositoryException
     */
    synchronized public void deleteById(long id)
            throws RepositoryException {
        delete(findById(id));
    }

    /**
     * Silently ignores being passed null and transient objects.
     *
     * @param prior - the prioritization to delete.
     * @throws edu.nmt.model.RepositoryException
     */
    synchronized public void delete(Prioritization prior)
            throws RepositoryException {
        if (prior != null) {
            try {
                Session s = getSession();

                //If prior.getId() returns 0, the object has not been persisted
                //yet. I.E. it's transient, not detached or persistent. If it's >
                //0, we can go ahead and just call delete and it'll figure it out
                //for us.
                if (prior.getId() > 0) {
                    s.delete(prior);
                }
            } 
            catch (HibernateException he) {
                LOG.warn("Could not delete Prioritization: " + prior);
                rollbackTransaction();
                throw new RepositoryException(he);
            }
        }
    }
}
