package repositories.impl;

import database.MongoConnection;
import lombok.AllArgsConstructor;
import org.bson.Document;
import repositories.interfaces.UserRepository;

import java.util.Optional;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Projections.*;


@AllArgsConstructor
public final class UserRepositoryImpl implements UserRepository {

    private final MongoConnection connection;

    @Override
    public Optional<Document> findUsername(final String username) {
        return Optional.ofNullable(
                connection.getCollection()
                .find(eq("lastName", username))
                .projection(fields(include("username"), excludeId()))
                .first()
        );
    }

    @Override
    public Document save(final Document document) {
        connection.getCollection().insertOne(document);
        return document;
    }
}
