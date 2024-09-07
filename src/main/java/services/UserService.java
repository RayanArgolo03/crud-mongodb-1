package services;

import com.google.gson.Gson;
import dtos.UserRequest;
import dtos.UserResponse;
import enums.FilterOption;
import exceptions.BusinessException;
import exceptions.DatabaseException;
import filters.UserFilter;
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

    public UserFilter readFilters() {

        final UserFilter userFilter = new UserFilter();

        FilterOption option;
        String input = null;

        while ((option = ReaderUtils.readOption(FilterOption.class)) != FilterOption.OUT) {

            input = ReaderUtils.readString(option.name());

            switch (option) {
                case NAME -> userFilter.setName(input);
                case EMAIL -> userFilter.setEmail(input);
                case USERNAME -> userFilter.setUsername(input);
                case PASSWORD -> userFilter.setPassword(input);
            }

            System.out.printf("Filter defined: %s in %s\n", input, option);

        }

        return (input == null) ? null : userFilter;
    }

    public Set<UserResponse> findByFilters(final UserFilter userFilter) {

        Objects.requireNonNull(userFilter, "No filters to find users!");

        final Set<Document> documents = repository.findByFilters(userFilter);
        if (documents.isEmpty()) throw new BusinessException("Users not found by filters!");

        return documents.stream()
                .map(document -> {
                    User user = GsonUtils.documentToEntity(document, User.class);
                    user.setId(document.getObjectId("id"));
                    return this.userToResponse(user);
                })
                .collect(Collectors.toSet());
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

        } catch (Exception e) {
            throw new DatabaseException(e.getMessage(), e);
        }

    }

}
