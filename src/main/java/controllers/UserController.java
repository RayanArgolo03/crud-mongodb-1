package controllers;

import dtos.UserRequest;
import dtos.UserResponse;
import filters.UserFilter;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import model.Login;
import model.User;
import org.bson.Document;
import services.UserService;
import utils.GsonUtils;
import utils.ReaderUtils;

import java.util.Set;

@Log4j2
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public final class UserController {

    UserService service;

    public UserResponse create() {

        log.info("Creating login info..");

        final String username = ReaderUtils.readString("username (at least 2 characters)");
        service.validateInput(username);

        service.findUsername(username);
        log.info("Username not exists!");

        final String password = ReaderUtils.readString("password (at least 2 characters)");
        service.validateInput(password);

        log.info("Creating user info..");

        final String name = service.validateAndFormatFirstName(
                ReaderUtils.readString("first name (at least 2 characters and without special symbols)")
        );

        final String email = ReaderUtils.readString("email (pattern xxx@domain.com)");
        service.validateEmail(email);

        User user = service.requestToUser(
                new UserRequest(name, email, new Login(username, password))
        );

        final Document document = service.save(user);

        user = GsonUtils.documentToEntity(document, User.class);
        user.setId(document.getObjectId("id"));

        return service.userToResponse(user);
    }

    public Set<UserResponse> read() {

        final UserFilter userFilter = service.readFilters();
        final Set<UserResponse> users = service.findByFilters(userFilter);

        log.info("Users found! \n");
        return users;
    }

    public void update() {

    }

    public void delete() {

    }
}
