package repositories;

import database.MongoConnection;
import filters.UserFilter;
import model.Login;
import model.User;
import org.bson.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import repositories.impl.UserRepositoryImpl;
import repositories.interfaces.UserRepository;
import utils.GsonUtils;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
            User entity = new User("pa", "pa", new Login("pa", "pa"));
            Set<Document> documents = repository.findByFilters(new UserFilter(entity.getName(), entity.getEmail(), entity.getLogin().getUsername(), entity.getLogin().getPassword()));
            assertEquals(1, documents.size());
        }

    }

}