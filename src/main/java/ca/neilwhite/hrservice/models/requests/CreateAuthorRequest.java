package ca.neilwhite.hrservice.models.requests;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateAuthorRequest  {
    @NotNull @NotEmpty String firstName;
    @NotNull @NotEmpty String lastName;
}



