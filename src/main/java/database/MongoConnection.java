package database;


import com.mongodb.*;
import com.mongodb.client.*;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.connection.ClusterConnectionMode;
import com.mongodb.connection.ClusterDescription;
import exceptions.DatabaseException;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.bson.Document;

import java.util.concurrent.TimeUnit;

import static java.lang.CharSequence.compare;
import static java.lang.String.format;

@Log4j2
@Getter
public final class MongoConnection {

    private static MongoConnection INSTANCE;
    private final MongoCollection<Document> collection;

    private MongoConnection() {

        //Init connection with server time limit (1 second in this example)
        MongoDatabase database = MongoClients.create(MongoClientSettings.builder()
                        .applyToClusterSettings(builder -> builder.serverSelectionTimeout(1000, TimeUnit.MILLISECONDS))
                        .build())
                .getDatabase("application");

        //Test connection below
        testConnection(database);

        //Get collection
        collection = database.getCollection("users");

        createConstraints();

    }

    //Singleton instantiation
    public static MongoConnection getInstance() {
        if (INSTANCE == null) INSTANCE = new MongoConnection();
        return INSTANCE;
    }

    //Test connection
    private void testConnection(MongoDatabase database) {

        try {
            database.runCommand(new BasicDBObject("ping", "1000"));

        } catch (Exception e) {
            log.info("MongoConnection not initiated! {} ", e.getMessage());
            System.exit(0);
        }

    }

    private void createConstraints() {
        collection.createIndex(
                new Document("email", 1),
                new IndexOptions().unique(true)
        );
    }
}
