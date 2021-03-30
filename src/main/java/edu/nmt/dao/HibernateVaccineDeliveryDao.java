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
import edu.nmt.model.VaccineDelivery;

/**
 * A <a href="http://www.hibernate.org/">Hibernate</a>
 * data access object for {@link Population}s.
 */
public class HibernateVaccineDeliveryDao extends HibernateDao {

    private static Logger LOG = LogManager.getLogger(HibernateVaccineDeliveryDao.class);

    /**
     * Constructor.
     *
     * @see HibernateDao
     */
    public HibernateVaccineDeliveryDao() {
    }

    /**
     * Constructor
     * @param cfg - name of the hibernate configuration file.
     * @param i - the interceptor.
     * @see HibernateDao
     */
    public HibernateVaccineDeliveryDao(String cfg, Interceptor i) {
        super(cfg, i);
    }

    synchronized public VaccineDelivery getVaccineDelivery(long id)
            throws RepositoryException {
        try {
            Session s = getSession();

            VaccineDelivery vd = (VaccineDelivery) s.get(VaccineDelivery.class, id);
            return vd;
        } 
        catch (HibernateException he) {
            LOG.warn("Hibernate get failed for vaccine delivery id: " + id);
            throw new RepositoryException(he);
        }
    }

    /**
     * @param id
     * @return null if a VaccineDelivery with the provided id doesn't exist.
     * @throws edu.nmt.model.RepositoryException
     */
    synchronized public VaccineDelivery findById(long id)
            throws RepositoryException {
        try {
            Session s = getSession();

            VaccineDelivery vd = (VaccineDelivery) s.get(VaccineDelivery.class, id);
            return vd;
        } 
        catch (HibernateException he) {
            LOG.warn("Hibernate get failed for VaccineDelivery id: " + id);
            throw new RepositoryException(he);
        }
    }

   

    /**
     *
     * @return @throws edu.nmt.model.RepositoryException
     */
    @SuppressWarnings("unchecked")
    synchronized public List<VaccineDelivery> getAllVaccineDeliveries()
            throws RepositoryException {
        try {
            Session s = getSession();

            Query q = s.createQuery("from VaccineDelivery d");

            List<VaccineDelivery> vds = new ArrayList<>();
            vds.addAll(q.list());

            return vds;
        } 
        catch (HibernateException he) {
            LOG.warn("Hibernate query failed while searching for all VaccineDeliveries");
            throw new RepositoryException(he);
        }
    }

    /**
     * Saves a vaccine delivery to the database.
     * @param vd - the vaccine delivery to save.
     * @throws edu.nmt.model.RepositoryException
     */
    synchronized public void save(VaccineDelivery vd)
            throws RepositoryException {
        try {
            Session s = getSession();
            s.saveOrUpdate(vd);
        } 
        catch (HibernateException he) {
            LOG.warn("Failed to save Vaccine Delivery: " + vd.getId());
            rollbackTransaction();
            throw new RepositoryException(he);
        }
    }

    /**
     * Deletes a vaccine delivery by identifier.
     * @param id - an identifier for a vaccine delivery.
     * @throws edu.nmt.model.RepositoryException
     */
    synchronized public void deleteById(long id)
            throws RepositoryException {
        delete(findById(id));
    }

    /**
     * Silently ignores being passed null and transient objects.
     *
     * @param vd - the vaccine delivery to delete.
     * @throws edu.nmt.model.RepositoryException
     */
    synchronized public void delete(VaccineDelivery vd)
            throws RepositoryException {
        if (vd != null) {
            try {
                Session s = getSession();

                //If vd.getId() returns 0, the object has not been persisted
                //yet. I.E. it's transient, not detached or persistent. If it's >
                //0, we can go ahead and just call delete and it'll figure it out
                //for us.
                if (vd.getId() > 0) {
                    s.delete(vd);
                }
            } 
            catch (HibernateException he) {
                LOG.warn("Could not delete Vaccine Delivery: " + vd);
                rollbackTransaction();
                throw new RepositoryException(he);
            }
        }
    }
}
