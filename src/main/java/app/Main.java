package app;


import controllers.UserController;
import enums.UserOption;
import jpa.MongoConnection;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import model.Login;
import model.User;
import repositories.impl.UserRepositoryImpl;
import services.UserService;
import utils.ReaderUtils;

import java.util.InputMismatchException;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Log4j2
public final class Main {

    private Main() {
    }

    static MongoConnection CONNECTION = MongoConnection.getInstance();

    static UserController CONTROLLER = new UserController(new UserService(
            new UserRepositoryImpl(CONNECTION)
    ));

    public static void main(String[] args) {

        UserOption option;
        loop:
        do {

            try {
                option = ReaderUtils.readOption();

                //Todo code com Codec Registry e aprende Records no DTO

                switch (option) {
                    case CREATE -> CONTROLLER.create();
                    case READ -> CONTROLLER.read();
                    case UPDATE -> CONTROLLER.update();
                    case DELETE -> CONTROLLER.delete();
                }

            } catch (InputMismatchException | ArrayIndexOutOfBoundsException e) {
                log.info("Invalid option! Stopping the program.. ");
                break loop;
            }

        } while (option != UserOption.OUT);

    }

}