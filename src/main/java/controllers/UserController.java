package controllers;

import dto.UserRequest;
import dto.UserResponse;
import enums.DeleteOption;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import model.Login;
import services.UserService;
import utils.ReaderUtils;

import java.util.Set;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public final class UserController {

    UserService service;

    public void create() {

        final String password = ReaderUtils.readString("password (at least 3 characters)");
        service.validatePassword(password);

        final String firstName = service.validateAndFormatFirstName(
                ReaderUtils.readString("first name (special symbols not authorised)")
        );

        final int age = service.validateAndFormatAge(
                ReaderUtils.readString("age (positive, more than 17 and less than 60, don´t question it..)")
        );

        System.out.println(
                service.save(new UserRequest(firstName, new Login(password), age))
        );

    }

    public void read() {

        System.out.println(
                service.findByFirstName(ReaderUtils.readString("first name"))
        );
    }

    public void update() {

        final UserResponse user = service.findByFirstName(
                ReaderUtils.readString("first name of user to be update")
        );

        System.out.println(user);

        final String newName = service.validateAndFormatUpdateName(
                ReaderUtils.readString("new first name"), user.name()
        );

        System.out.printf("Updated! %s", service.updateName(newName, user.name()));
    }

    public void delete() {

        final Set<UserResponse> userResponses = service.deleteByOption(ReaderUtils.readOption(DeleteOption.class));

        System.out.println("Users deleted: ");
        for (UserResponse user : userResponses) System.out.println(user);

    }
}
