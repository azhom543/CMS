package com.RealProjects.CourseManagmentSystem.service;

import com.RealProjects.CourseManagmentSystem.exception.ResourceNotFoundException;
import com.RealProjects.CourseManagmentSystem.model.Student;
import com.RealProjects.CourseManagmentSystem.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student createStudent(Student student) {
        studentRepository.save(student);
        return student;
    }

    public Student updateStudent(Long id, Student studentDetails) {
        Student student = studentRepository.findById(id);
        if (student == null) {
            throw new ResourceNotFoundException("Student not found");
        }
        student.setName(studentDetails.getName());
        student.setStudentId(studentDetails.getStudentId());
        student.setCourse(studentDetails.getCourse());
        studentRepository.update(student);
        return student;
    }

    public void deleteStudent(Long id) {
        Student student = studentRepository.findById(id);
        if (student == null) {
            throw new ResourceNotFoundException("Student not found");
        }
        studentRepository.deleteById(id);
    }
}
