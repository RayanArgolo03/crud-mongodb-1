package repositories.impl;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import database.MongoConnection;
import filters.UserFilter;
import lombok.AllArgsConstructor;
import org.bson.Document;
import org.bson.conversions.Bson;
import repositories.interfaces.UserRepository;

import java.util.*;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Projections.*;


@AllArgsConstructor
public final class UserRepositoryImpl implements UserRepository {

    private final MongoConnection connection;

    @Override
    public Optional<Document> findUsername(final String username) {
        return Optional.ofNullable(
                connection.getCollection()
                .find(eq("login.username", username))
                .projection(fields(include("username"), excludeId()))
                .first()
        );
    }

    @Override
    public Set<Document> findByFilters(final UserFilter userFilter) {

        final List<Bson> filters = new LinkedList<>();

        if (userFilter.getName() != null) filters.add(eq("name", userFilter.getName()));

        if (userFilter.getEmail() != null) filters.add(eq("email", userFilter.getEmail()));

        if (userFilter.getUsername() != null) filters.add(eq("login.username", userFilter.getUsername()));

        if (userFilter.getPassword() != null) filters.add(eq("login.password", userFilter.getPassword()));

        return connection.getCollection()
                .find(and(filters))
                .into(new HashSet<>());
    }

    @Override
    public Document save(final Document document) {
        connection.getCollection().insertOne(document);
        return document;
    }
}
