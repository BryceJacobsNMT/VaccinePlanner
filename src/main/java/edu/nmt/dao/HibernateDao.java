package edu.nmt.dao;

import edu.nmt.model.RepositoryException;
import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;

import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;

/**
 * This is the base class for Data access objects using Hibernate as their
 * persistence mechanism. It provides basic support for creating
 * SessionFactories and Sessions, and beginning and committing transactions.
 * SessionFactories are saved in a static Map&lt;String, SessionFactory&gt; with
 * keys being the file name of the hibernate configuration file used to create
 * the Factory. This is done so that multiple instances of a dao that accesses
 * the same database don't have to repeat the overhead of creating a
 * SessionFactory. You can also specify a Hibernate Interceptor to be used when
 * creating sessions.  <b>Note: </b> <em>This class is not threadsafe! To
 * implement threadsafety, have your subclass synchronize appropriate access
 * methods</em>
 */
public class HibernateDao {

    protected static final Map<String, SessionFactory> FACTORIES = new HashMap<>();

    private static final Logger LOG = LogManager.getLogger(HibernateDao.class);

    private String configFileName = null;
    private Session currentSession = null;
    private Transaction currentTx = null;
    private Interceptor interceptor = null;
    private Configuration hibConfig = null;


    /**
     * Creates a HibernateDao that uses <code>cfg</code> as it's configuration
     * file and creates Sessions with <code>i</code> as its Interceptor.
     *
     * @param cfg File name of the main xml hibernate configuration file. Must
     * be on the classpath somewhere. If null, "/hibernate.cfg.xml" is used.
     * @param i Interceptor used when creating Sessions. If null, no Interceptor
     * is used.
     */
    public HibernateDao(String cfg, Interceptor i) {
        if (cfg == null || cfg.length() == 0) {
            this.configFileName = "hibernate.cfg.xml";
        } else {
            this.configFileName = cfg;
        }
        System.out.println( "Config file is "+configFileName);
        this.interceptor = i;
    }

    /**
     * Creates a HibernateDao that uses the default configuration file name and
     * no Interceptor.
     *
     * @see #HibernateDao(String, Interceptor)
     */
    public HibernateDao() {
        this(null, null);
    }

    /**
     * Returns the Hibernate configuration file name used to create a
     * SessionFactory for this Dao.
     * @return - the configuration file name.
     */
    public String getConfigurationFileName() {
        return this.configFileName;
    }

    /**
     * Sets the Hibernate configuration file name used to create a
     * SessionFactory for this Dao.If a previous SessionFactory had already
     * been created for this Dao, Transactions are committed, sessions are
     * closed and then the previous SessionFactory is closed.
     * @param cfg - hibernate configuration file name.
     * @throws edu.nmt.model.RepositoryException
  
     */
    public void setConfigurationFileName(String cfg)
            throws RepositoryException {
        synchronized (FACTORIES) {
            String newCfg;
            if (cfg == null || cfg.length() == 0) {
                newCfg = "hibernate.cfg.xml";
            } 
            else {
                newCfg = cfg;
            }

            if (!this.configFileName.equals(newCfg) && FACTORIES.containsKey(this.configFileName)) {
                commitTransaction();
                closeSession();
                destroyFactory();
            }

            this.configFileName = newCfg;
            System.out.println( "Set config file name to "+configFileName);

            initConfig();
        }
    }

    protected Configuration initConfig()
            throws RepositoryException {
        try {
            this.hibConfig = new Configuration();
            URL resourceURL = HibernateDao.class.getClassLoader().getResource( configFileName);
            String filePath = resourceURL.getPath();
            System.out.println( "filePath="+filePath);
            File file = new File( filePath );
            this.hibConfig.configure(file);

            return this.hibConfig;
        } 
        catch (HibernateException ex) {
            System.out.println("Could not turn file name '" + this.configFileName + "' into a Hibernate Configuration.");
            throw new RepositoryException(ex);
        }
    }

   

    /**
     * returns a session factory that was configured using the config file
     * pointed to by {@link #getConfigurationFileName}.If a SessionFactory has
     * already been configured for this configuration file, return the old one.
     * @return 
     * @throws edu.nmt.model.RepositoryException
     */
    public SessionFactory getSessionFactory()
            throws RepositoryException {
        synchronized (FACTORIES) {
            SessionFactory fact = FACTORIES.get(this.configFileName);

            if (fact == null) {
                try {
                    LOG.debug("Creating a new SessionFactory");

                    if (this.hibConfig == null) {
                        initConfig();
                    }
                    ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(hibConfig.getProperties())
                            .build();

                    fact = this.hibConfig.buildSessionFactory(serviceRegistry);
                    FACTORIES.put(this.configFileName, fact);
                } 
                catch (HibernateException ex) {
                    LOG.warn("Could not create a SessionFactory!");
                    throw new RepositoryException(ex);
                }
            }

            return fact;
        }
    }

    /**
     * Returns the currently open Session. If there is non, one is opened and
     * then returned.
     */
    synchronized public Session getSession()
            throws RepositoryException {
        if (this.currentSession == null || !this.currentSession.isOpen()) {
            try {
                SessionFactory fact = getSessionFactory();
                LOG.debug("Creating a new session");
                if (this.interceptor != null) {
                    currentSession = fact.withOptions().interceptor(this.interceptor).openSession();
                    LOG.debug("\tUsing an Interceptor to create the session. ("
                            + this.interceptor.getClass() + ")");
                } else {
                    currentSession = fact.openSession();
                    LOG.debug("\tNOT using an Interceptor to create the session.");
                }
            } catch (HibernateException he) {
                LOG.warn("Could not retrieve a hibernate session");
                throw new RepositoryException(he);
            }
        }

        return this.currentSession;
    }

    /**
     * This method sets the Interceptor that is used to create sessions for this
     * Dao.If <code>i</code> != the old interceptor and a session factory
     * already exists for this configuration, transactions are committed and
     * sessions are closed so that the next session to be asked for will have
     * the interceptor you assigned.
     *
     * @param i can be null.
     * @throws edu.nmt.model.RepositoryException
     * @see Interceptor
     */
    public void setInterceptor(Interceptor i)
            throws RepositoryException {
        synchronized (FACTORIES) {
            if (FACTORIES.containsKey(this.configFileName) && ((this.interceptor == null) ? i != null : !this.interceptor.equals(i))) {
                commitTransaction();
                closeSession();
            }

            this.interceptor = i;
            LOG.debug("Set an interceptor");
        }
    }

    /**
     * Returns the Interceptor used when creating sessions returned by
     * getSession().If null is returned, then no interceptor was used when
     * creating the sessions.
     *
     * @return 
     * @see Interceptor
     */
    public Interceptor getInterceptor() {
        return this.interceptor;
    }

    /**
     * If there is an open session, close it.
     * @throws edu.nmt.model.RepositoryException
     */
    synchronized public void closeSession()
            throws RepositoryException {
        if (isInsideATransaction()) {
            throw new RepositoryException("You cannot close a session if you are still inside a transaction.  Roll it back or commit it first.");
        }

        if (isSessionOpen()) {
            try {
                this.currentSession.close();
                this.currentSession = null;
                LOG.debug("Closed a hibernate session");
            } 
            catch (HibernateException he) {
                LOG.warn("Could not close the hibernate session");
                throw new RepositoryException(he);
            }
        }
    }

    /**
     * If a transaction has not already begun.
     * @throws edu.nmt.model.RepositoryException, start one.
     */
    synchronized public void beginTransaction()
            throws RepositoryException {
        if (this.currentTx == null) {
            try {
                LOG.debug("Beginning a new transaction");
                this.currentTx = getSession().beginTransaction();
            } 
            catch (HibernateException he) {
                LOG.warn("Could not begin a transaction");
                throw new RepositoryException(he);
            }
        }
    }

    /**
     * If a transaction is active, commit it.If an exception occurs during the
     * commit, {@link #rollbackTransaction} is called.
     * @throws edu.nmt.model.RepositoryException
     */
    synchronized public void commitTransaction()
            throws RepositoryException {
        if (isSessionOpen() && isInsideATransaction()) {
            try {
                this.currentTx.commit();
                this.currentTx = null;
                LOG.debug("Committed transaction");
            } 
            catch (HibernateException he) {
                LOG.error("Could not commit a transaction! Rolling back db");
                rollbackTransaction();

                if (he instanceof StaleObjectStateException) {
                    throw he;
                }

                throw new RepositoryException(he);
            }
        } 
        else {
            LOG.warn("No transaction in progress; commitTransaction is meaningless!");
        }
    }

    /**
     * If a transaction is active, roll it back. This method also closes the
     * current session for this thread because all persistent objects related to
     * that session no longer match the data base. Note: if there is no
     * transaction at all, this method does nothing.
     */
    synchronized public void rollbackTransaction()
            throws RepositoryException {
        if (isSessionOpen() && isInsideATransaction()) {
            try {
                this.currentTx.rollback();
                LOG.warn("rolled back transaction");
            } 
            catch (HibernateException he) {
                LOG.warn("Could not rollback transaction");
                throw new RepositoryException(he);
            } 
            finally {
                this.currentTx = null;
                closeSession();
            }
        }
    }

    /**
     * This method ONLY closes the factory associated with
     * {@link #getConfigurationFileName}. It does not commit pending
     * transactions nor does it close any sessions.
     */
    public void destroyFactory()
            throws RepositoryException {
        synchronized (FACTORIES) {
            SessionFactory fact = FACTORIES.get(this.configFileName);

            if (fact != null) {
                fact.close();
                FACTORIES.remove(this.configFileName);
                LOG.debug("Closed a SessionFactory");
            }
        }
    }

    /**
     * This method will close ALL SessionFactories that have been created.It
     * does not commit pending transactions nor does it close any sessions.
     * @throws edu.nmt.model.RepositoryException
     */
    public static void destroyAllFactories()
            throws RepositoryException {
        synchronized (FACTORIES) {
            for (Iterator<String> it = FACTORIES.keySet().iterator(); it.hasNext();) {
                String cfg = it.next();
                SessionFactory fact = FACTORIES.get(cfg);
                if (fact != null) {
                    fact.close();
                    it.remove();
                    LOG.debug("Closed a SessionFactory");
                }
            }
        }
    }

    synchronized public boolean isSessionOpen() {
        return (this.currentSession != null && this.currentSession.isOpen());
    }

    synchronized public boolean isInsideATransaction() {
        return this.currentTx != null && !this.currentTx.wasCommitted() && !this.currentTx.wasRolledBack();
    }

    /**
     * Tests to see if the database for which this DAO is configured can be
     * reached in the given amount of time.
     *
     * @param timeoutSeconds the number of seconds to allow for login.
     *
     * @return
     * <i>true</i> if the connection was successful, <i>false</i> otherwise.
     */
    public boolean ping(int timeoutSeconds) {
        boolean success;

        //The constructor does not init hibConfig, but the public setter does
        if (this.hibConfig == null) {
            try {
                initConfig(); //logs an error on failure
            } 
            catch (RepositoryException ex) {
                return false;
            }
        }

        Properties cfgProps = this.hibConfig.getProperties();

        int cachedTimeout = DriverManager.getLoginTimeout();

        DriverManager.setLoginTimeout(timeoutSeconds);

        Connection cnxn = null;
        //to open a connection
        try {
            //DriverManager.setLogWriter(new PrintWriter(System.err));
            cnxn = DriverManager.getConnection(cfgProps.getProperty("hibernate.connection.url"),
                    cfgProps.getProperty("hibernate.connection.username"),
                    cfgProps.getProperty("hibernate.connection.password"));
            System.out.println( "Got connection to database");
            success = true;
        }
        //mark the failure
        catch (SQLException ex) {
            success = false;
            System.out.println("Could not connect to vaccine database."+ ex);
        } 
        //close connection & reset the login timeout
        finally {
            if (cnxn != null) {
                try {
                    cnxn.close();
                } 
                catch (SQLException ex) {
                    LOG.warn("ping was unable to close a connection", ex);
                }
            }

            DriverManager.setLoginTimeout(cachedTimeout);
        }

        return success;
    }

}
