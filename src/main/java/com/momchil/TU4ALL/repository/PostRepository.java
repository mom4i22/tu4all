package com.momchil.TU4ALL.repository;

import com.momchil.TU4ALL.dbo.PostDBO;
import com.momchil.TU4ALL.dbo.UserDBO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<PostDBO, Long> {

    @Query("select post from PostDBO post where post.creator = ?1 order by post.creationDate desc")
    List<PostDBO> findAllByCreator(UserDBO userDBO);
}
