package app;


import controllers.UserController;
import database.MongoConnection;
import enums.UserOption;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import mappers.UserMapper;
import org.mapstruct.factory.Mappers;
import repositories.impl.UserRepositoryImpl;
import services.UserService;
import utils.ReaderUtils;

import java.util.InputMismatchException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Log4j2
public final class Main {

    static MongoConnection CONNECTION = MongoConnection.getInstance();

    static UserController CONTROLLER = new UserController(new UserService(
            new UserRepositoryImpl(CONNECTION),
            Mappers.getMapper(UserMapper.class))
    );

    public static void main(String[] args) {

        log.info("Welcome to MongoDB Crud! \n");

        UserOption option = null;

        loop:
        do {

            try {
                option = ReaderUtils.readOption(UserOption.class);

                switch (option) {
                    case CREATE -> System.out.printf("User created: %s \n", CONTROLLER.create());

                    case READ -> CONTROLLER.read().forEach(System.out::println);

                    case UPDATE -> System.out.printf("User updated: %s \n", CONTROLLER.update());

                    case DELETE -> System.out.printf("User deleted: %s \n", CONTROLLER.delete());
                }

            } catch (InputMismatchException | ArrayIndexOutOfBoundsException e) {
                log.error("Invalid option! Stopping the program.. ");
                break loop;

            } catch (Exception e) {
                log.error(e.getMessage());
            }

        } while (option != UserOption.OUT);

    }

}