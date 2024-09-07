package mappers;

import dtos.UserRequest;
import dtos.UserResponse;
import model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface UserMapper {


    User requestToUser(UserRequest request);

    @Mapping(target = "firstName", source = "name")
    @Mapping(target = "idInHex", expression = "java(user.getId())")
    @Mapping(target = "createdAt", expression = "java(user.getCreatedAt())")
    UserResponse userToResponse(User user);


}
