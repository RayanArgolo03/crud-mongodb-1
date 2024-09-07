package utils;

import enums.UserOption;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.util.Scanner;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public final class ReaderUtils {

    static Scanner SCANNER = new Scanner(System.in);

    public static <T extends Enum<T>> T readOption(final Class<T> enumClass) {

        System.out.println("Enter with your option: ");

        T[] enumConstants = enumClass.getEnumConstants();

        for (T value : enumConstants) System.out.printf("%d - %s \n", value.ordinal(), value);

        int option = SCANNER.nextInt();

        return enumConstants[option];
    }

    public static String readString(final String title) {
        System.out.printf("\nEnter with %s:", title);
        return SCANNER.next();
    }

}
