package com.momchil.TU4ALL.repository;

import com.momchil.TU4ALL.dbo.CommentDBO;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<CommentDBO, Long> {

}
