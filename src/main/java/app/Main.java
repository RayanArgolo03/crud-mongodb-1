package app;


import controllers.UserController;
import enums.UserOption;
import jpa.DatabaseManager;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import repositories.impl.UserRepositoryImpl;
import services.UserService;
import utils.ReaderUtils;

import java.util.InputMismatchException;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Log4j2
public final class Main {

    static DatabaseManager MANAGER = new DatabaseManager("h2");

    static UserController CONTROLLER = new UserController(new UserService(
            new UserRepositoryImpl(MANAGER))
    );

    public static void main(String[] args) {

        UserOption option;
        loop:
        do {

            try {
                option = ReaderUtils.readOption();

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