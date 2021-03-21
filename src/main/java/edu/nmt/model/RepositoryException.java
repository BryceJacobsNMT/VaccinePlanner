package edu.nmt.model;

import java.io.Serializable;

/**
 * A RepositoryException occurs when there is a problem accessing the DAO layer. 
 * This class encapsulates the exceptions thrown by a specific implementation of a
 * DAO or DaoFactory.
 */
public class RepositoryException extends Exception implements Serializable {

    private static final long serialVersionUID = 1L;

    public RepositoryException() {
    }

    public RepositoryException(String msg) {
        super(msg);
    }

    public RepositoryException(String msg, Throwable th) {
        super(msg, th);
    }

    public RepositoryException(Throwable th) {
        super(th);
    }
}