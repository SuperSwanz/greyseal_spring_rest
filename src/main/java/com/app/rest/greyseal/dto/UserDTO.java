package com.app.rest.greyseal.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Date;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDTO {

    private Long id;

    private Date createdDate;

    private Date updatedDate;

    private String firstName;

    private String middleName;

    private String lastName;

    private String phoneNumber;

    private String email;

    private String createdByUser;

    private String updatedByUser;

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(id).append(32).toHashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        final UserDTO userDTO = (UserDTO) obj;
        return new EqualsBuilder().append(this.id, userDTO.id).isEquals();
    }
}