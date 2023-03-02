package com.momchil.TU4ALL.repository;

import com.momchil.TU4ALL.dbo.UserDBO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserDBO, Long> {
    UserDBO findByAlias(String alias);
}
