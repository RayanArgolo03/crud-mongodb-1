package repositories;

import com.google.gson.Gson;
import database.MongoConnection;
import params.UserParam;
import model.Login;
import model.User;
import org.bson.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import repositories.impl.UserRepositoryImpl;
import repositories.interfaces.UserRepository;
import utils.GsonUtils;

import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryTest {

    private UserRepository repository;
    private Document document;

    @BeforeEach
    void setUp() {
        repository = new UserRepositoryImpl(MongoConnection.getInstance());
        document = GsonUtils.entityToDocument(new User("pa", "pa", new Login("pa", "pa")));
    }

    @Nested
    class CreateUserTests {

        @Test
        void test() {
            UserParam find = new UserParam("pa", "pa", "pa", "pa");
            UserParam update = new UserParam("ji", "oa", "pa", "pa");

            repository.save(document);

            assertNotNull(repository.findByParams(find));
            assertNotNull(repository.findAndUpdateByParams(find, update));
            assertNotNull(repository.findAndDeleteByParams(update));
        }

    }

}