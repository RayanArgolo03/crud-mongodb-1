package repositories.interfaces;


import model.User;
import org.bson.Document;

import java.util.Optional;

public interface UserRepository {

    Optional<Document> findUsername(String username);
    Document save(Document document);
}
