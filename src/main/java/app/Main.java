package app;


import controllers.UserController;
import enums.UserOption;
import database.MongoConnection;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import mappers.UserMapper;
import org.mapstruct.factory.Mappers;
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
            Mappers.getMapper(UserMapper.class),
            new UserRepositoryImpl(CONNECTION)
    ));

    public static void main(String[] args) {

        final Class<UserOption> enumClass = UserOption.class;
        UserOption option;

        loop:
        while ((option = ReaderUtils.readOption(enumClass)) != UserOption.OUT) {

            try {
                switch (option) {
                    case CREATE -> CONTROLLER.create();
                    case READ -> CONTROLLER.read();
                    case UPDATE -> CONTROLLER.update();
                    case DELETE -> CONTROLLER.delete();
                }

            } catch (InputMismatchException | ArrayIndexOutOfBoundsException e) {
                log.info("Invalid option! Stopping the program.. ");
                break loop;
            } catch (Exception e) {
                log.error("Occured: {}", e.getMessage());
            }

        }

        System.out.println("Rayan Argolo");
    }


}