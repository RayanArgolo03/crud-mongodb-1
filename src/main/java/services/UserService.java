package services;

import com.mongodb.client.model.Filters;
import dto.UserRequest;
import dto.UserResponse;
import enums.DeleteOption;
import exceptions.BusinessException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import mappers.UserMapper;
import model.User;
import org.bson.Document;
import org.bson.conversions.Bson;
import repositories.interfaces.UserRepository;
import utils.ReaderUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

import static java.lang.String.format;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public final class UserService {

    UserMapper mapper;
    UserRepository repository;

    public String validateAndFormatFirstName(final String firstName) {

        if (!firstName.matches("^[A-Za-zÀ-ÖØ-öø-ÿ]+$"))
            throw new BusinessException(format("%s is a invalid name, contains special symbol!", firstName));

        return firstName.substring(0, 1).toUpperCase().concat(
                firstName.substring(1).toLowerCase()
        );
    }

    public String validateAndFormatUpdateName(final String newName, final String oldName) {

        if (newName.equalsIgnoreCase(oldName))
            throw new BusinessException(format("%s is equals to current name!", newName));

        return validateAndFormatFirstName(newName);
    }

    public void validatePassword(final String password) {
        if (password.length() < 3)
            throw new BusinessException(format("%s is a invalid password, short password!", password));
    }

    public int validateAndFormatAge(final String temp) {

        try {
            int age = Integer.parseInt(temp);

            if (age < 0) throw new BusinessException("Negative age!");
            if (age < 18) throw new BusinessException("Underage!");
            if (age > 59) throw new BusinessException("Elderly!");

            return age;

        } catch (NumberFormatException e) {
            throw new BusinessException("Age only numbers!");
        }

    }

    public LocalDate validateAndFormatDate(final String date) {

        try {
            return LocalDate.parse(date, DateTimeFormatter.ofPattern("MM/dd/uuuu")
                    .withResolverStyle(ResolverStyle.STRICT));

        } catch (DateTimeParseException e) {
            throw new BusinessException(format("%s is a invalid date", date));
        }

    }

    public LocalTime validateAndFormatTime(final String time) {

        try {
            return DateTimeFormatter.ofPattern("HH:mm")
                    .withResolverStyle(ResolverStyle.STRICT)
                    .parse(time, LocalTime::from);

        } catch (DateTimeParseException e) {
            throw new BusinessException(format("%s is a invalid time", time));
        }

    }

    public UserResponse save(final UserRequest request) {

        final User user = mapper.requestToEntity(request);
        repository.save(user);

        return mapper.entityToResponse(user);
    }

    public UserResponse findByFirstName(final String firstName) {

        return repository.findByFirstName(firstName)
                .map(mapper::entityToResponse)
                .orElseThrow(() -> new BusinessException(format("User not found by first name %s", firstName)));
    }

    public UserResponse updateName(final String newName, final String oldName) {

        return mapper.entityToResponse(
                repository.updateName(newName, oldName)
        );

    }

    public Set<UserResponse> deleteByOption(final DeleteOption option) {

        final Set<User> usersDeleted = repository.delete(getFilter(option));
        if (usersDeleted.isEmpty()) throw new BusinessException("Users not deleted by option!");

        return usersDeleted.stream()
                .map(mapper::entityToResponse)
                .collect(Collectors.toSet());
    }

    private Bson getFilter(final DeleteOption option) {

        return switch (option) {
            case AGE_EQUALS_OR_GREATER_THAN -> Filters.gte("age", validateAndFormatAge(
                    ReaderUtils.readString("age (positive, more than 17 and less than 60, don´t question it..)")
            ));

            case AGE_EQUALS_OR_LESS_THAN -> Filters.lte("age", validateAndFormatAge(
                    ReaderUtils.readString("age (positive, more than 17 and less than 60, don´t question it..)")
            ));

            case EXACTLY_NAME -> Filters.eq("first_name", validateAndFormatFirstName(
                    ReaderUtils.readString("exactly the first name (special symbols not authorised)")
            ));
        };

    }

}
