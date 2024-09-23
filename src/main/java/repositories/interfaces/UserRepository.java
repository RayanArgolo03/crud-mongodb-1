package repositories.interfaces;


import model.User;
import org.bson.conversions.Bson;

import java.util.Optional;
import java.util.Set;

public interface UserRepository {

    void save(User user);

    Optional<User> findByFirstName(String firstName);

    User updateName(final String newName, final String oldName);

    Set<User> delete(Bson filter);

}
