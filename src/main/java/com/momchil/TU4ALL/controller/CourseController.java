package com.momchil.TU4ALL.controller;

import com.momchil.TU4ALL.dbo.CourseDBO;
import com.momchil.TU4ALL.dbo.UserDBO;
import com.momchil.TU4ALL.model.UserCourseRequest;
import com.momchil.TU4ALL.service.CourseService;
import com.momchil.TU4ALL.service.UserService;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/courses")
public class CourseController {
    static org.slf4j.Logger logger = LoggerFactory.getLogger(PostController.class);

    private CourseService courseService;

    private UserService userService;

    public CourseController(CourseService courseService, UserService userService) {
        this.courseService = courseService;
        this.userService = userService;
    }

    @PostMapping(value = "/create-course/{id}", headers = "content-type=multipart/*")
    public ResponseEntity<?> createCourse(@PathVariable long id, @RequestParam("subject") String subject,
                                          @RequestParam("description") String description) {
        courseService.createCourse(id, subject, description);
        return ResponseEntity.ok("Course created successfully!");
    }

    @GetMapping("/get-course/{id}")
    public ResponseEntity<CourseDBO> getCourseOfTeacher(@PathVariable long id) {
        logger.info("[getCourseOfTeacher] teacher id " + id);
        CourseDBO courseDBO = courseService.readByCreator(id);
        return ResponseEntity.ok(courseDBO);
    }

    @GetMapping("/get-courses/{id}")
    public ResponseEntity<List<CourseDBO>> getCoursesOfTeacher(@PathVariable long id) {
        List<CourseDBO> courseDBOS = courseService.readAllByTeacher(id);
        return ResponseEntity.ok(courseDBOS);
    }

    @DeleteMapping("/delete-course/{id}")
    public ResponseEntity<?> deleteCourseOfTeacher(@PathVariable long id) {
        logger.info("[deleteCourseOfTeacher] teacher id " + id);
        courseService.deleteCourse(id);
        return ResponseEntity.ok("Course deleted successfully");
    }

    @PutMapping("/add-student-to-course/{id}")
    public ResponseEntity<?> addStudentToCourse(@PathVariable long id, @RequestBody UserCourseRequest userCourseRequest) {
        courseService.addStudentToCourse( userCourseRequest.getStudentId(),id);
        return ResponseEntity.ok("User successfully added to course");
    }

    @PutMapping("/remove-student-from-course/{id}")
    public ResponseEntity<?> removeStudentFromCourse(@PathVariable long id, @RequestBody UserCourseRequest userCourseRequest) {
        courseService.removeStudentFromCourse( userCourseRequest.getStudentId(),id);
        return ResponseEntity.ok("User successfully removed from course");
    }

    @GetMapping("/get-students-for-course/{id}")
    public ResponseEntity<List<UserDBO>> getAllStudentsForCourse(@PathVariable long id) {
        List<UserDBO> students = courseService.readAllByCourse(id);
        return ResponseEntity.ok(students);
    }
    @GetMapping("/get-all-courses-for-user-teacher/{id}")
    public ResponseEntity<List<CourseDBO>> getAllCoursesForStudent(@PathVariable long id) {
        List<CourseDBO> courseDBOS = userService.readAllCoursesForUser(id);
        return ResponseEntity.ok(courseDBOS);
    }

}
