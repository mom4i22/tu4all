package com.momchil.TU4ALL.repository;

import com.momchil.TU4ALL.dbo.PostDBO;
import com.momchil.TU4ALL.dbo.UserDBO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<PostDBO, Long> {

    @Query("SELECT post FROM PostDBO post WHERE post.creator = ?1 ORDER BY post.creationDate desc")
    List<PostDBO> findAllByCreator(UserDBO userDBO);

    @Query("SELECT post FROM PostDBO post WHERE post.creator = ?1 " +
            "AND post.creationDate >= ?2 " +
            "AND post.creationDate <= ?3 ORDER BY post.creationDate desc")
    List<PostDBO> findAllByCreatorAndDate(UserDBO userDBO, Timestamp startDate, Timestamp endDate);

}
