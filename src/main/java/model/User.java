package model;


import lombok.Getter;
import lombok.Setter;
import org.bson.BsonType;
import org.bson.codecs.pojo.annotations.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

@Getter
public final class User {

    @BsonRepresentation(value = BsonType.OBJECT_ID)
    @Setter
    private String id;

    @BsonProperty(value = "first_name")
    private final String firstName;

    private final Login login;

    private final int age;

    @BsonProperty("created_at")
    private final LocalDateTime createdAt;

    @BsonProperty("last_update")
    private final LocalDateTime lastUpdate;

    @BsonCreator
    public User(@BsonProperty("first_name") String firstName, @BsonProperty("login") Login login, @BsonProperty("age") int age) {
        this.firstName = firstName;
        this.login = login;
        this.age = age;

        this.createdAt = LocalDateTime.now();
        this.lastUpdate = LocalDateTime.now();
    }

    @BsonIgnore
    public String getDateInString(final LocalDateTime date) {
        return date.format(DateTimeFormatter.ofPattern("uuuu/MM/dd HH:mm)")
                .withResolverStyle(ResolverStyle.STRICT));
    }

}
