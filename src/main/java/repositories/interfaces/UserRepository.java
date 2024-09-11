package repositories.interfaces;


import params.UserParam;
import org.bson.Document;

import java.util.Optional;
import java.util.Set;

public interface UserRepository {

    Optional<Document> findUsername(String username);

    Set<Document> findByParams(UserParam userParam);
    Document findAndUpdateByParams(UserParam userParam, UserParam updateValues);
    Document findAndDeleteByParams(UserParam userParam);

    Document save(Document document);
}
