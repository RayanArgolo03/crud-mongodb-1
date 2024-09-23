package utils;

import enums.UserOption;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.util.Scanner;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public final class ReaderUtils {

    static Scanner SCANNER = new Scanner(System.in);

    public static <T extends Enum<T>> T readOption(final Class<T> enumClass) {

        T[] enumConstants = enumClass.getEnumConstants();

        System.out.println("Enter with your option: ");

        for (T enumm : enumConstants) System.out.printf("%d - %s \n",enumm.ordinal(), enumm.name());

        int option = SCANNER.nextInt();

        return enumConstants[option];
    }

    public static String readString(final String title) {
        System.out.printf("Enter with %s: ", title);
        return SCANNER.next();
    }

}
