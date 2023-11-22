package lk.ijse.dep11.to;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeacherTO implements Serializable {
    private int id;
    private String name;
    private String contact;
}
