package jpa;

import exceptions.ConnectionException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;

import java.util.Objects;
import java.util.function.Consumer;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Getter
@Log4j2
public final class JpaManager {

    EntityManager entityManager;

    public JpaManager(String persistenceUnit) {

        Objects.requireNonNull(persistenceUnit, "Persistence unit can´t be null!");

        this.entityManager = Persistence.createEntityManagerFactory(persistenceUnit)
                .createEntityManager();
    }

    public void executeAction(final Consumer<EntityManager> action) {

        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            action.accept(entityManager);
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

    public void clearPersistenceContext(){ entityManager.clear(); }
}
