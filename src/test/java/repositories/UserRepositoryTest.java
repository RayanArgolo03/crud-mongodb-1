package repositories;

import com.google.gson.Gson;
import database.MongoConnection;
import lombok.extern.java.Log;
import model.Login;
import model.User;
import org.bson.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import repositories.impl.UserRepositoryImpl;
import repositories.interfaces.UserRepository;
import utils.GsonUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserRepositoryTest {

    private UserRepository repository;
    private Document document;

    @BeforeEach
    void setUp() {
        repository = new UserRepositoryImpl(MongoConnection.getInstance());
        document = GsonUtils.entityToMongoDocument(new User("pa", "pa", new Login("pa", "pa")));
    }

    @Nested
    class CreateUserTests {

        @Test
        void test() {
            //Todo teste read
        }

    }

}