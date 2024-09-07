package dtos;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import model.Login;

import java.time.LocalDate;
import java.time.LocalDateTime;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@AllArgsConstructor
public final class UserResponse {

    String idInHex;
    String firstName;
    String createdAt;

    @Override
    public String toString() {
        return firstName + " - Created at " + createdAt;
    }
}
