package repositories.impl;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Updates;
import database.MongoConnection;
import model.User;
import org.bson.Document;
import org.bson.conversions.Bson;
import repositories.interfaces.UserRepository;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static com.mongodb.client.model.Projections.excludeId;
import static com.mongodb.client.model.Updates.*;

public final class UserRepositoryImpl implements UserRepository {

    private final MongoCollection<User> collection;

    public UserRepositoryImpl(MongoConnection mongoConnection) {
        this.collection = mongoConnection.getDatabase().getCollection("users", User.class);
    }

    @Override
    public void save(final User user) {
        collection.insertOne(user);
    }

    @Override
    public Optional<User> findByFirstName(final String firstName) {

        return Optional.ofNullable(collection.find(getCaseInsensitiveCompare("first_name", firstName))
                .projection(excludeId())
                .first());

    }

    @Override
    public User updateName(final String newName, final String oldName) {

        collection.updateOne(
                getCaseInsensitiveCompare("first_name", oldName),
                combine(set("first_name", newName), set("last_update", LocalDateTime.now()))
        );

        return findByFirstName(newName).get();
    }

    @Override
    public Set<User> delete(final Bson filter) {

        final Set<User> users = collection.find(filter).into(new HashSet<>());

        collection.deleteMany(filter);

        return users;
    }

    private Document getCaseInsensitiveCompare(final String key, final String value) {
        return new Document().append(
                key,
                new Document("$regex", value).append("$options", "i")
        );
    }
}
