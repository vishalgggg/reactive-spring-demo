package ca.neilwhite.hrservice.models.requests;

import javax.validation.constraints.NotEmpty;
// import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateBookRequest {
        @NotNull(message = "name should not be null")
        @NotEmpty(message = "name should not be empty")
        
        String name;
}

