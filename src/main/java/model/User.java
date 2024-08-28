package model;

import jakarta.persistence.*;
import jakarta.persistence.CascadeType;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;
import org.bson.types.ObjectId;
import org.hibernate.annotations.*;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;

@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@NoArgsConstructor(force = true)

@Setter
@Getter
@Entity
@Table(name = "users")
public final class User {

    @javax.persistence.GeneratedValue
    @javax.persistence.Id
    final ObjectId id;

    @javax.persistence.Column(unique = true, nullable = false)
    String username;

    @javax.persistence.Column(nullable = false)
    String password;

    @javax.persistence.Column(nullable = false)
    String firstName;

    @javax.persistence.Column(name = "creation_date")
    @CreationTimestamp
    final LocalDateTime creationDate;
}
