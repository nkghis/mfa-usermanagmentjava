package ics.ci.mfausermanagmentjava.mapper;

import ics.ci.mfausermanagmentjava.dto.UserDTO;
import ics.ci.mfausermanagmentjava.entity.AppUser;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO userToUserDTO(AppUser user);
}
