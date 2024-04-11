package ca.neilwhite.hrservice.models.requests;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


    
public record CreateAuthorRequest(@NotNull @NotEmpty String firstName, @NotNull @NotEmpty String lastName) {
}



