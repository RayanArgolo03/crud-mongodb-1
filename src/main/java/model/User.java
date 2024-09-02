package model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;

@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@ToString

public final class User{

    ObjectId id;
    final Login login;
    @NonFinal
    String firstName;
    final LocalDateTime creationDate;

}
