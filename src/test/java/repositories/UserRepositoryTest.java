package repositories;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;
import jpa.DatabaseManager;
import lombok.extern.java.Log;
import model.Login;
import model.User;
import org.bson.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import repositories.impl.UserRepositoryImpl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class UserRepositoryTest {

    private UserRepository repository;

    @BeforeEach
    void setUp() {
        repository = new UserRepositoryImpl(new DatabaseManager("h2"));
    }

    @Nested
    class CreateUserTests {

        @Test
        void test() {
            User user = new User(null, new Login(null, "pa", "pa", LocalDateTime.now()), "paa", LocalDateTime.now());

            GsonBuilder gsonBuilderDate = new GsonBuilder()
                    .registerTypeAdapter(LocalDateTime.class, (JsonSerializer<LocalDateTime>)
                            (localDateTime, type, context) -> {
                                String formattedDateTime = localDateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
                                return new JsonPrimitive(formattedDateTime);
                            });

            Gson gson = gsonBuilderDate.create();

            repository.save(Document.parse(gson.toJson(user)));
        }

    }

}