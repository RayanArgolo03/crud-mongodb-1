package jpa;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.InsertOneResult;
import exceptions.DatabaseException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import model.Login;
import model.User;
import org.bson.Document;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.function.Consumer;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Log4j2
public final class DatabaseManager {

    EntityManager sqlEntityManager;

    public DatabaseManager(String persistenceUnit) {

        Objects.requireNonNull(persistenceUnit, "Persistence unit can´t be null!");

        sqlEntityManager = Persistence.createEntityManagerFactory(persistenceUnit)
                .createEntityManager();

    }

    public void executeSqlAction(final Consumer<EntityManager> action) {

        Objects.requireNonNull(action, "Action can´t be null!");

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

            throw new DatabaseException(e.getCause().getMessage(), e);

        } finally {
            clearPersistenceContext();
        }

    }

    public void executeNoSqlAction(final Consumer<MongoCollection<Document>> action, final String collection) {

        Objects.requireNonNull(action, "Action can´t be null!");
        Objects.requireNonNull(collection, "Collection can´t be null!");

        try (MongoClient connection = MongoClients.create()) {

            final MongoDatabase database = connection.getDatabase("registry");
            action.accept(
                    database.getCollection(collection)
            );

        } catch (Exception e) {
            throw new DatabaseException(e.getMessage(), e);
        }


    }

    public void clearPersistenceContext() {
        sqlEntityManager.clear();
    }
}
