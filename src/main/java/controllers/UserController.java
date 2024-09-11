package controllers;

import dtos.UserRequest;
import dtos.UserResponse;
import params.UserParam;
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

//        final String username = ReaderUtils.readString("username (at least 2 characters)");
        final String username = "Ja";
        service.validateInput(username);

        service.findUsername(username);

//        final String password = ReaderUtils.readString("password (at least 2 characters)");
        final String password = "Ja";
        service.validateInput(password);

        log.info("Creating user info..");

//        final String name = service.validateAndFormatFirstName(
//                ReaderUtils.readString("first name (at least 2 characters and without special symbols)")
//        );
        final String name = "Ja";

//        final String email = ReaderUtils.readString("email (pattern xxx@domain.com)");
        final String email = "rayan@gmail.com";
        service.validateEmail(email);

        User user = service.requestToUser(
                new UserRequest(name, email, new Login(username, password))
        );

        final Document document = service.save(user);

        user = GsonUtils.documentToEntity(document, User.class);
        user.setId(document.getObjectId("_id"));

        return service.userToResponse(user);
    }

    public Set<UserResponse> read() {

        log.info("Receiving filters to find.. \n");
        final UserParam userParam = service.readParams();

        final Set<UserResponse> users = service.findByParams(userParam);

        log.info("Users found! \n");
        return users;
    }

    public UserResponse update() {

        log.info("Receiving filters to find.. \n");
        final UserParam params = service.readParams();

        log.info("Receiving values to update.. \n");
        final UserParam updateValues = service.readParams();

        return service.findAndUpdateByParams(params, updateValues);
    }

    public UserResponse delete() {

        log.info("Receiving filters to find and delete.. \n");
        final UserParam params = service.readParams();

        return service.findAndDeleteByParams(params);
    }
}
