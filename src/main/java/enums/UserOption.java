package enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Getter
@AllArgsConstructor
public enum UserOption {

    CREATE("Create"), UPDATE("Update"), READ("Read"), DELETE("Delete"), OUT("Out");
    String formattedName;
}
