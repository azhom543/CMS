package com.RealProjects.CourseManagmentSystem.repository;

import com.RealProjects.CourseManagmentSystem.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class StudentRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int save(Student student) {
        String sql = "INSERT INTO students (name, student_id, course) VALUES (?, ?, ?)";
        return jdbcTemplate.update(sql, student.getName(), student.getStudentId(), student.getCourse());
    }

    public List<Student> findAll() {
        String sql = "SELECT * FROM students";
        return jdbcTemplate.query(sql, new StudentRowMapper());
    }

    public Student findById(Long id) {
        String sql = "SELECT * FROM students WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new StudentRowMapper(), id);
    }

    public int update(Student student) {
        String sql = "UPDATE students SET name = ?, student_id = ?, course = ? WHERE id = ?";
        return jdbcTemplate.update(sql, student.getName(), student.getStudentId(), student.getCourse(), student.getId());
    }

    public int deleteById(Long id) {
        String sql = "DELETE FROM students WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

    private static final class StudentRowMapper implements RowMapper<Student> {
        @Override
        public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
            Student student = new Student();
            student.setId(rs.getLong("id"));
            student.setName(rs.getString("name"));
            student.setStudentId(rs.getString("student_id"));
            student.setCourse(rs.getString("course"));
            return student;
        }
    }
}
