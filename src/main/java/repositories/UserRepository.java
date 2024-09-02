package repositories;


import model.User;
import org.bson.Document;

public interface UserRepository {
    void save(Document document);
}
