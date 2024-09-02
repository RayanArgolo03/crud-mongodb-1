package repositories.impl;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.InsertOneResult;
import jpa.DatabaseManager;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import model.User;
import org.bson.Document;
import repositories.UserRepository;

import javax.print.Doc;
import java.time.LocalDateTime;
import java.util.function.Consumer;

@AllArgsConstructor
@RequiredArgsConstructor
public final class UserRepositoryImpl implements UserRepository {

    DatabaseManager databaseManager;

    @Override
    public void save(final Document document) {
        return;
    }
}
