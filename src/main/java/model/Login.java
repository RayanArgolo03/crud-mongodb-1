package model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor(force = true)
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)

@Entity
@Table(name = "logins")
public final class Login {

    @GeneratedValue
    @Id
    UUID id;

    @Column(unique = true, nullable = false)
    String username;

    @Column(nullable = false)
    String password;

}
