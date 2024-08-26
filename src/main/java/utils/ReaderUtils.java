package utils;

import enums.UserOption;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.util.Scanner;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public final class ReaderUtils {

    static UserOption[] VALUES = UserOption.values();
    static Scanner SCANNER = new Scanner(System.in);

    public static UserOption readOption() {

        System.out.println("Enter with your option: ");

        for (UserOption value : VALUES)
            System.out.printf("%d - %s \n",
                    value.ordinal(), value.getFormattedName());

        int option = SCANNER.nextInt();

        return VALUES[option];
    }

}
