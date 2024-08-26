package model;

import jakarta.persistence.*;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;
import org.hibernate.annotations.*;

import java.time.LocalDateTime;
import java.util.UUID;

@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@NoArgsConstructor(force = true)

@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "users")
public final class User {

    @GeneratedValue
    @Id
    final UUID id;

    @Setter
    @Column(name = "first_name", nullable = false)
    String firstName;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    final Login login;

    @CreationTimestamp
    @Column(name = "creation_date")
    final LocalDateTime creationDate;
}
