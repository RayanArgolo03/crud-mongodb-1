package model;


import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.bson.types.ObjectId;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.Date;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Getter
public final class User {

    @NonFinal
    @Setter
    ObjectId id;
    String name;
    String email;
    Login login;

    public String getCreatedAt() {
        return DateTimeFormatter.ofPattern("dd/MM/uuuu")
                .withResolverStyle(ResolverStyle.STRICT)
                .format(LocalDate.ofInstant(login.getId().getDate().toInstant(), ZoneId.systemDefault()));
    }

    public String getId() {
        return id.toHexString();
    }


}
