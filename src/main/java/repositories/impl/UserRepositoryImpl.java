package repositories.impl;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import database.MongoConnection;
import lombok.AllArgsConstructor;
import org.bson.Document;
import org.bson.conversions.Bson;
import params.UserParam;
import repositories.interfaces.UserRepository;

import java.util.*;
import java.util.function.BiFunction;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Projections.*;
import static com.mongodb.client.model.Updates.*;


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
    public Set<Document> findByParams(final UserParam userParam) {

        final List<Bson> conditions = createParams(userParam, false);
        final Bson where = and(conditions);

        return connection.getCollection()
                .find(where)
                .into(new HashSet<>());
    }

    @Override
    public Document findAndUpdateByParams(final UserParam userParam, final UserParam updateValues) {

        final List<Bson> conditions = createParams(userParam, false);
        final List<Bson> update = createParams(updateValues, true);

        update.add(currentTimestamp("updated"));

        final Bson where = and(conditions);

        return connection.getCollection()
                .findOneAndUpdate(where, combine(update));
    }

    @Override
    public Document findAndDeleteByParams(final UserParam params) {

        final List<Bson> conditions = createParams(params, false);
        final Bson where = and(conditions);

        return connection.getCollection()
                .findOneAndDelete(where);
    }

    private List<Bson> createParams(final UserParam userParam, final boolean isUpdate) {

        final List<Bson> params = new ArrayList<>();

        final BiFunction<String, String, Bson> condition = (isUpdate)
                ? Updates::set
                : Filters::eq;

        if (userParam.getName() != null) params.add(condition.apply("name", userParam.getName()));

        if (userParam.getEmail() != null) params.add(condition.apply("email", userParam.getEmail()));

        if (userParam.getUsername() != null) params.add(condition.apply("login.username", userParam.getUsername()));

        if (userParam.getPassword() != null) params.add(condition.apply("login.password", userParam.getPassword()));

        return params;
    }


    @Override
    public Document save(final Document document) {
        connection.getCollection().insertOne(document);
        return document;
    }

    private long updateOne(final String oldName, final String newName) {
        return connection.getCollection()
                .updateOne(eq("name", oldName), set("name", newName))
                .getMatchedCount();
    }

    private long deleteOne(final String oldName) {
        return connection.getCollection()
                .deleteOne(eq("name", oldName))
                .getDeletedCount();
    }

    private long updateMany(final String oldName, final String newName) {
        return connection.getCollection()
                .updateMany(eq("name", oldName), combine(
                        set("name", newName),
                        set("password", "jajaja"),
                        currentTimestamp("lastUpdate")
                ))
                .getMatchedCount();
    }

    private long deleteMany(final String oldName, final String newName) {
        return connection.getCollection()
                .deleteMany(eq("name", oldName))
                .getDeletedCount();
    }

    private List<Document> findAll(final String oldName) {
        return connection.getCollection()
                .find(eq("name", oldName))
                .into(new ArrayList<>());
    }

    private Document find(final String oldName) {
        return connection.getCollection()
                .find()
                .first();
    }


}
