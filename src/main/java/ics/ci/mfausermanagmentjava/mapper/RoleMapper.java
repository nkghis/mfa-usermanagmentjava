package ics.ci.mfausermanagmentjava.mapper;

import ics.ci.mfausermanagmentjava.dto.RoleDTO;
import ics.ci.mfausermanagmentjava.entity.AppRole;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    RoleDTO roleToRoleDTO(AppRole role);
}
