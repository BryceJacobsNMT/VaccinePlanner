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

/**
 * A <a href="http://www.hibernate.org/">Hibernate</a>
 * data access object for {@link Population}s.
 */
public class HibernatePopulationDao extends HibernateDao {

    private static Logger LOG = LogManager.getLogger(HibernatePopulationDao.class);

    /**
     * Constructor.
     *
     * @see HibernateDao
     */
    public HibernatePopulationDao() {
    }

    /**
     * Constructor
     * @param cfg - name of the hibernate configuration file.
     * @param i - the interceptor.
     * @see HibernateDao
     */
    public HibernatePopulationDao(String cfg, Interceptor i) {
        super(cfg, i);
    }

    synchronized public Population getPopulation(long id)
            throws RepositoryException {
        try {
            Session s = getSession();

            Population pop = (Population) s.get(Population.class, id);
            return pop;
        } 
        catch (HibernateException he) {
            LOG.warn("Hibernate get failed for population id: " + id);
            throw new RepositoryException(he);
        }
    }

    /**
     * @param id
     * @return null if a Population with the provided id doesn't exist.
     * @throws edu.nmt.model.RepositoryException
     */
    synchronized public Population findById(long id)
            throws RepositoryException {
        try {
            Session s = getSession();

            Population pop = (Population) s.get(Population.class, id);
            return pop;
        } 
        catch (HibernateException he) {
            LOG.warn("Hibernate get failed for Population id: " + id);
            throw new RepositoryException(he);
        }
    }

    /**
     * @param popName
     * @return null if a Population with the provided name doesn't exist.
     * @throws edu.nmt.model.RepositoryException
     */
    /*synchronized public Population findByName(String popName)
            throws RepositoryException {
        try {
            Session s = getSession();

            Query q = s.createQuery("from Population p where p.name = :code");
            q.setString("name", popName);

            Population pop = (Population) q.uniqueResult();
            return pop;
        } 
        catch (HibernateException he) {
            LOG.warn("Hibernate query failed while searching for Population.name: " + popName);
            throw new RepositoryException(he);
        }
    }*/

    /**
     *
     * @return @throws edu.nmt.model.RepositoryException
     */
    @SuppressWarnings("unchecked")
    synchronized public List<Population> getAllPopulations()
            throws RepositoryException {
        try {
            Session s = getSession();

            Query q = s.createQuery("from Population p");

            List<Population> pops = new ArrayList<>();
            pops.addAll(q.list());

            return pops;
        } 
        catch (HibernateException he) {
            LOG.warn("Hibernate query failed while searching for all Populations");
            throw new RepositoryException(he);
        }
    }

    /**
     * Saves a population to the database.
     * @param pop - the population to save.
     * @throws edu.nmt.model.RepositoryException
     */
    synchronized public void save(Population pop)
            throws RepositoryException {
        try {
            Session s = getSession();
            this.beginTransaction();
            s.saveOrUpdate(pop);
            this.commitTransaction();
        } 
        catch (HibernateException he) {
            LOG.warn("Failed to save Population: " + pop.getId());
            rollbackTransaction();
            throw new RepositoryException(he);
        }
    }

    /**
     * Deletes a population by identifier.
     * @param id - an identifier for a population.
     * @throws edu.nmt.model.RepositoryException
     */
    synchronized public void deleteById(long id)
            throws RepositoryException {
        delete(findById(id));
    }

    /**
     * Silently ignores being passed null and transient objects.
     *
     * @param pop - the population to delete.
     * @throws edu.nmt.model.RepositoryException
     */
    synchronized public void delete(Population pop)
            throws RepositoryException {
        if (pop != null) {
            try {
                Session s = getSession();

                //If pop.getId() returns 0, the object has not been persisted
                //yet. I.E. it's transient, not detached or persistent. If it's >
                //0, we can go ahead and just call delete and it'll figure it out
                //for us.
                if (pop.getId() > 0) {
                    s.delete(pop);
                }
            } 
            catch (HibernateException he) {
                LOG.warn("Could not delete Population: " + pop);
                rollbackTransaction();
                throw new RepositoryException(he);
            }
        }
    }
}
