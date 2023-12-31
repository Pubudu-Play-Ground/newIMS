package lk.ijse.dep11.api;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lk.ijse.dep11.to.TeacherTO;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/teachers")
@CrossOrigin
public class TeacherHttpController {

    private final HikariDataSource pool;

    public TeacherHttpController(){
        HikariConfig config = new HikariConfig();
        config.setUsername("root");
        config.setPassword("1234");
        config.setJdbcUrl("jdbc:mysql://localhost:3306/dep11_practice_IMS");
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");
        config.addDataSourceProperty("maximumPoolSize", 10);
        pool = new HikariDataSource(config);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(produces = "application/json", consumes = "application/json")
    public TeacherTO addTeacher(@RequestBody @Validated TeacherTO teacherTO){
        try(Connection connection=pool.getConnection()){
            PreparedStatement stm = connection.prepareStatement("INSERT INTO teacher (name,contact) VALUES (?,?)", Statement.RETURN_GENERATED_KEYS);
            stm.setString(1,teacherTO.getName());
            stm.setString(2, teacherTO.getContact());
            stm.executeUpdate();
            ResultSet generatedKeys = stm.getGeneratedKeys();
            generatedKeys.next();
            int id = generatedKeys.getInt(1);
            teacherTO.setId(id);
            return teacherTO;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping(value = "/{id}",consumes = "application/json")
    public void updateTeacher(@PathVariable int id,
                              @RequestBody @Validated TeacherTO teacherTO){
        try(Connection connection= pool.getConnection()) {
            PreparedStatement existSTM = connection.prepareStatement("SELECT * FROM teacher WHERE id=?");
            existSTM.setInt(1,id);

            if(!existSTM.executeQuery().next()){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Teacher Not found");
            }
            PreparedStatement stm = connection.prepareStatement("UPDATE teacher SET name=?, contact=? WHERE id=?");
            stm.setString(1,teacherTO.getName());
            stm.setString(2, teacherTO.getContact());
            stm.setInt(3,id);

            stm.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteTeacher(@PathVariable int id){
        try(Connection connection= pool.getConnection()){
            PreparedStatement existSTM = connection.prepareStatement("SELECT * FROM teacher WHERE id=?");
            existSTM.setInt(1,id);

            if(!existSTM.executeQuery().next()){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Teacher Not found");
            }

            PreparedStatement stm = connection.prepareStatement("DELETE FROM teacher WHERE id =?");
            stm.setInt(1,id);
            stm.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping(value = "/{id}",produces = "application/json")
    public TeacherTO getTeacher(@PathVariable("id") int teacherID){
        try(Connection connection= pool.getConnection()) {
            PreparedStatement stm = connection.prepareStatement("SELECT * FROM teacher WHERE id = ?");
            stm.setInt(1,teacherID);

            ResultSet rst = stm.executeQuery();

            if(!rst.next()){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Teacher do not exists");
            }
            return new TeacherTO(rst.getInt("id"), rst.getString("name"), rst.getString("contact") );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping(produces = "application/json")
    public List<TeacherTO> getALlTeacher(){
        try(Connection connection= pool.getConnection()) {
            Statement stm = connection.createStatement();
            ResultSet rst = stm.executeQuery("SELECT * FROM teacher");

            List<TeacherTO>teacherList=new ArrayList<>();
            while (rst.next()){
                int id = rst.getInt("id");
                String name = rst.getString("name");
                String contact = rst.getString("contact");
                teacherList.add(new TeacherTO(id,name,contact));
            }
            return teacherList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
