package org.beginningee6.tutorial;

import java.util.logging.Logger;

import javax.inject.Inject;

/**
 * @author Antonio Goncalves & Alexis Moussine-Pouchkine
 *         Tutorial - Beginning with The Java EE 6 Platform
 *         http://www.antoniogoncalves.org
 *         http://blogs.sun.com/alexismp
 *         --
 *         A simple implementation of the Customer interface with no CDI Qualifier
 */
public class DefaultCustomer implements Customer {

    @Inject Logger logger;
    
    @Override
    public OrderItem buy(String id) {
        logger.info("Not supported yet.");
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean canBuy() {
        return false;
    }

}
