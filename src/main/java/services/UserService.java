package services;

import com.mongodb.MongoException;
import dtos.UserRequest;
import dtos.UserResponse;
import enums.ParamOption;
import exceptions.BusinessException;
import exceptions.DatabaseException;
import params.UserParam;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import mappers.UserMapper;
import model.User;
import org.bson.Document;
import repositories.interfaces.UserRepository;
import utils.GsonUtils;
import utils.ReaderUtils;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static java.lang.String.format;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public final class UserService {

    UserRepository repository;
    UserMapper mapper;

    public void findUsername(final String username) {
        if (repository.findUsername(username).isPresent()) {
            throw new BusinessException(format("Username %s already exists!", username));
        }
    }


    public Set<UserResponse> findByParams(final UserParam userParam) {

        Objects.requireNonNull(userParam, "No filters to find users!");

        final Set<Document> documents = repository.findByParams(userParam);
        if (documents.isEmpty()) throw new BusinessException("Users not found by filters!");

        return documents.stream()
                .map(document -> {
                    User user = GsonUtils.documentToEntity(document, User.class);
                    user.setId(document.getObjectId("_id"));
                    return this.userToResponse(user);
                })
                .collect(Collectors.toSet());
    }

    public UserResponse findAndUpdateByParams(final UserParam params, final UserParam updateValues) {

        Objects.requireNonNull(params, "No filters to find users!");
        Objects.requireNonNull(params, "No values to update!");

        //If value exists, throw MongoWrite exception
        try {
            final Document document = repository.findAndUpdateByParams(params, updateValues);

            if (document == null) throw new BusinessException("User not found by filters, no update!");

            final User user = GsonUtils.documentToEntity(document, User.class);
            user.setId(document.getObjectId("_id"));

            return this.userToResponse(user);

        } catch (MongoException e) {
            throw new DatabaseException("Duplicate email in update!", e);
        }
    }

    public UserResponse findAndDeleteByParams(final UserParam params) {

        Objects.requireNonNull(params, "No filters to delete users!");

        final Document deletedDocument = repository.findAndDeleteByParams(params);
        if (deletedDocument == null) throw new BusinessException("Users not deleted by filters");

        final User user = GsonUtils.documentToEntity(deletedDocument, User.class);
        user.setId(deletedDocument.getObjectId("_id"));

        return this.userToResponse(user);

    }

    public UserParam readParams() {

        final UserParam userParam = new UserParam();

        ParamOption option;
        String input = null;

        while ((option = ReaderUtils.readOption(ParamOption.class)) != ParamOption.OUT) {

            input = ReaderUtils.readString(option.name());

            switch (option) {
                case NAME -> userParam.setName(input);
                case EMAIL -> userParam.setEmail(input);
                case USERNAME -> userParam.setUsername(input);
                case PASSWORD -> userParam.setPassword(input);
            }

            System.out.printf("%s in %s\n", input, option);

        }

        return (input == null) ? null : userParam;
    }


    public void validateInput(final String input) {

        Objects.requireNonNull(input, "Info is null!");
        if (input.length() < 2) throw new BusinessException(format("%s is small!", input));
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

    public Document save(final User user) {

        Objects.requireNonNull(user, "User can´t be null!");

        try {
            return repository.save(GsonUtils.entityToDocument(user));

            //If user has existing email, throw exception
        } catch (Exception e) {
            throw new DatabaseException(e.getMessage(), e);
        }

    }

}
