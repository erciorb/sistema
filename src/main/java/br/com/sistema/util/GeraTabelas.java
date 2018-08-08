package br.com.sistema.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author ericsson
 */
public class GeraTabelas {

    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("prototipo");

        EntityManager manager = factory.createEntityManager();

        manager.close();
        factory.close();
    }
}
