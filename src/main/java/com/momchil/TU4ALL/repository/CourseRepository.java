package com.momchil.TU4ALL.repository;

import com.momchil.TU4ALL.dbo.CourseDBO;
import com.momchil.TU4ALL.dbo.UserDBO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<CourseDBO, Long> {
    @Query("SELECT c FROM CourseDBO c WHERE c.teacher = ?1")
    List<CourseDBO> findAllByTeacher(UserDBO userDBO);

    @Query("SELECT c FROM CourseDBO c WHERE c.teacher = ?1")
    CourseDBO findByTeacher(UserDBO userDBO);

    @Query("SELECT u FROM CourseDBO c JOIN c.students u WHERE c.courseId = :courseId")
    List<UserDBO> findAllUsersEnrolled(@Param("courseId") long courseId);

    @Query("SELECT u FROM UserDBO u JOIN u.courses c WHERE c.courseId = :courseId")
    List<UserDBO> findUsersByCourseId(@Param("courseId") long courseId);

}
