package com.momchil.TU4ALL.service;

import com.momchil.TU4ALL.dbo.CourseDBO;
import com.momchil.TU4ALL.dbo.UserDBO;
import com.momchil.TU4ALL.repository.CourseRepository;
import com.momchil.TU4ALL.repository.UserRepository;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@Service
public class CourseService {
    static org.slf4j.Logger logger = LoggerFactory.getLogger(CourseService.class);

    private CourseRepository courseRepository;

    private UserRepository userRepository;

    public CourseService(CourseRepository courseRepository, UserRepository userRepository) {
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
    }

    public CourseDBO readById(long id) {
        return courseRepository.findById(id).get();
    }

    public CourseDBO readByCreator(long id) {
        UserDBO userDBO = userRepository.findById(id).get();
        CourseDBO courseDBO = courseRepository.findByTeacher(userDBO);
        return courseDBO;
    }

    public List<CourseDBO> readAllByTeacher(long id) {
        UserDBO userDBO = userRepository.findById(id).get();
        List<CourseDBO> courseDBO = courseRepository.findAllByTeacher(userDBO);
        return courseDBO;
    }

    public void createCourse(long teacherId, String description, String subject) {

        UserDBO teacher = userRepository.findById(teacherId).get();
        CourseDBO courseDBO = new CourseDBO();
        courseDBO.setDescription(description);
        courseDBO.setSubject(subject);

        courseDBO.setTeacher(teacher);
        courseRepository.save(courseDBO);
    }

    public void deleteCourse(long id) {
        UserDBO userDBO = userRepository.findById(id).get();
        CourseDBO courseDBO = courseRepository.findByTeacher(userDBO);
        courseRepository.delete(courseDBO);
    }

    public void addStudentToCourse(long studentId, long courseId) {
        UserDBO student = userRepository.findById(studentId).orElseThrow(() ->
                new IllegalArgumentException("Student not found with ID: " + studentId));

        CourseDBO course = courseRepository.findById(courseId).orElseThrow(() ->
                new IllegalArgumentException("Course not found with ID: " + courseId));

        course.getStudents().add(student);
        courseRepository.save(course);
    }

    public void removeStudentFromCourse(long studentId, long courseId) {
        UserDBO student = userRepository.findById(studentId).orElseThrow(() ->
                new IllegalArgumentException("Student not found with ID: " + studentId));

        CourseDBO course = courseRepository.findById(courseId).orElseThrow(() ->
                new IllegalArgumentException("Course not found with ID: " + courseId));

        course.getStudents().remove(student);
        courseRepository.save(course);
    }

    public List<UserDBO> readAllByCourse(long courseId) {
        return courseRepository.findAllUsersEnrolled(courseId);
    }

}
