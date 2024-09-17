package model;


import org.bson.types.ObjectId;
public final class User {

    private ObjectId id;
    private final String firstName;
    private  final  Login login;

    public User(String firstName, Login login) {
        this.firstName = firstName;
        this.login = login;
    }

    public ObjectId getId() {
        return id;
    }

    public Login getLogin() {
        return login;
    }

    public String getFirstName() {
        return firstName;
    }
}
