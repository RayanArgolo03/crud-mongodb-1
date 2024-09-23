package model;


import lombok.Getter;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

@Getter
public final class Login {

    private final ObjectId id = new ObjectId();

    @BsonProperty("password")
    private final String password;

    @BsonCreator
    public Login(@BsonProperty("password") String password) {
        this.password = password;
    }
}
