package services;

import com.mongodb.MongoWriteException;
import dtos.UserRequest;
import dtos.UserResponse;
import exceptions.BusinessException;
import exceptions.DatabaseException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import mappers.UserMapper;
import model.User;
import org.bson.Document;
import org.bson.types.ObjectId;
import repositories.interfaces.UserRepository;
import utils.GsonUtils;

import java.util.Objects;

import static java.lang.String.format;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public final class UserService {

    UserRepository repository;
    UserMapper mapper;

    public void validateInput(final String input) {

        Objects.requireNonNull(input, "Info is null!");
        if (input.length() < 2) throw new BusinessException(format("%s is small!", input));
    }

    public void findUsername(final String username) {
        if (repository.findUsername(username).isPresent()) {
            throw new BusinessException(format("Username %s already exists!", username));
        }
    }

    public String validateAndFormatFirstName(final String firstName) {

        validateInput(firstName);

        if (!firstName.matches("^[A-Za-z]+((\\s)?((['\\-.])?([A-Za-z])+))*$")) {
            throw new BusinessException(format("%s contains special symbol!", firstName));
        }

        return firstName.substring(0, 1).toUpperCase().concat(
                firstName.substring(1).toLowerCase()
        );
    }

    public void validateEmail(final String email) {

        if (!email.matches("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")) {
            throw new BusinessException(format("%s does not match the pattern 'email@domain.com'", email));
        }
    }

    public User requestToUser(final UserRequest request) {

        Objects.requireNonNull(request, "User can´t be null!");
        return mapper.requestToUser(request);

    }

    public UserResponse userToResponse(final User user) {

        Objects.requireNonNull(user, "User can´t be null!");
        return mapper.userToResponse(user);

    }

    public Document save(final Document document) {

        try {
            return repository.save(document);
        } catch (Exception e) {
            throw new DatabaseException(e.getMessage(), e);
        }

    }

}
