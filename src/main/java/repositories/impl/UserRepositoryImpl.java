package repositories.impl;

import com.mongodb.client.MongoCollection;
import jpa.MongoConnection;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import model.User;
import repositories.interfaces.UserRepository;

public final class UserRepositoryImpl implements UserRepository {

    private final MongoCollection<User> collection;

    public UserRepositoryImpl(MongoConnection mongoConnection) {
        this.collection = mongoConnection.getDatabase().getCollection("users", User.class);
    }

    @Override
    public void save(final User user) {
        collection.insertOne(user);
        System.out.println();
    }
}
