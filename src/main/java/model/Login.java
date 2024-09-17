package model;


import org.bson.types.ObjectId;

public final class Login {

    private final ObjectId id = new ObjectId();
    private final String password;

    public Login(String password) {
        this.password = password;
    }

    public ObjectId getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }
}
