package com.momchil.TU4ALL.repository;

import com.momchil.TU4ALL.dbo.CommentDBO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Long, CommentDBO> {
}
