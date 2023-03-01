package com.momchil.TU4ALL.repository;

import com.momchil.TU4ALL.dbo.PostDBO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Long, PostDBO> {
}
