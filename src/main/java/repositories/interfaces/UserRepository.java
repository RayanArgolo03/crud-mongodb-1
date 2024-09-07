package repositories.interfaces;


import filters.UserFilter;
import model.User;
import org.bson.Document;

import java.util.Optional;
import java.util.Set;

public interface UserRepository {

    Optional<Document> findUsername(String username);

    Set<Document> findByFilters(UserFilter userFilter);

    Document save(Document document);
}
