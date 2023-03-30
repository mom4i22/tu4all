package com.momchil.TU4ALL.repository;

import com.momchil.TU4ALL.dbo.PostDBO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<PostDBO, Long> {

}
