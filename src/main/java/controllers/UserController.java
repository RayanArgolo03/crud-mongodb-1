package controllers;

import jpa.JpaManager;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import model.User;
import repositories.impl.UserRepositoryImpl;
import services.UserService;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public final class UserController {

    UserService service;

    public void create(){

    }

    public void read(){

    }

    public void update(){

    }

    public void delete(){

    }
}
