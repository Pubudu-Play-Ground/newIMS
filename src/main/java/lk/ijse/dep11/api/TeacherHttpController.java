package lk.ijse.dep11.api;

import lk.ijse.dep11.to.TeacherTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/teachers")
@CrossOrigin
public class TeacherHttpController {

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public TeacherTO addTeacher(@RequestBody TeacherTO teacherTO){}

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("/{id}")
    public void updateTeacher(@RequestBody TeacherTO teacherTO){}

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteTeacher(){}

    @GetMapping("/{id}")
    public TeacherTO getTeacher(){
        return null;
    }

    @GetMapping
    public TeacherTO getALlTeacher(){
        return null;
    }
}
