package lk.ijse.dep11.to;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import javax.validation.groups.Default;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeacherTO implements Serializable {
    @Null(message = "Id should be empty")
    private Integer id;

    @NotBlank(message = "name should not be empty")
    private String name;

    @NotBlank(message = "contact should not be empty")
    private String contact;



}
