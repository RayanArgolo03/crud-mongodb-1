package jpa;

import exceptions.ConnectionException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;

import java.util.Objects;
import java.util.function.Consumer;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Getter
@Log4j2
public final class JpaManager {

    javax.persistence.EntityManager noSqlEntityManager;
    EntityManager sqlEntityManager;

    public JpaManager(String noSqlPersistenceUnit, String sqlPersistenceUnit) {

        Objects.requireNonNull(noSqlPersistenceUnit, "NoSQL Persistence unit can´t be null!");
        Objects.requireNonNull(sqlPersistenceUnit, "SQL Persistence unit can´t be null!");

        noSqlEntityManager = javax.persistence.Persistence.createEntityManagerFactory(noSqlPersistenceUnit)
                .createEntityManager();

        sqlEntityManager = Persistence.createEntityManagerFactory(sqlPersistenceUnit)
                .createEntityManager();
    }

    public void executeAction(final Consumer<EntityManager> action) {

        EntityTransaction transaction = null;
        try {
            transaction = sqlEntityManager.getTransaction();
            transaction.begin();
            action.accept(sqlEntityManager);
            transaction.commit();

        } catch (Exception e) {

            if (Objects.nonNull(transaction)) {
                try {
                    transaction.rollback();
                } catch (Exception ee) {
                    log.error(ee.getMessage());
                }
            }

            throw new ConnectionException(e.getCause().getMessage(), e);

        } finally {
            clearPersistenceContext();
        }

    }

    public void clearPersistenceContext() {
        sqlEntityManager.clear();
    }
}
