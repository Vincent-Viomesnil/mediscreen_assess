package model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Patient {
    @Id
    @NotNull
    private Long id;
    @NotNull
    private String firstname;
    @NotNull
    private String lastname;

}

