package com.app.rest.greyseal.mapper;

import com.app.rest.greyseal.dto.UserDTO;
import com.app.rest.greyseal.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.Set;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface UserMapper {
    UserMapper mapper = Mappers.getMapper(UserMapper.class);

    User toUser(final UserDTO userDTO);

    @Mapping(source = "createdBy.email", target = "createdByUser")
    @Mapping(source = "updatedBy.email", target = "updatedByUser")
    UserDTO toUserDTO(final User User);

    Set<UserDTO> toUserDTOSet(Set<User> users);

}