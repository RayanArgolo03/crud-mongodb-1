package model;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@AllArgsConstructor
public final class Login {

    @NonFinal
    ObjectId id;
    String username;
    String password;
    LocalDateTime creationDate;

}
