package com.momchil.TU4ALL.repository;

import com.momchil.TU4ALL.dbo.UserDBO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserDBO, Long> {
    UserDBO findByAlias(String alias);

    UserDBO findByEmail(String email);

    @Query("SELECT user FROM UserDBO user WHERE user.id != ?1 ORDER BY user.alias desc")
    List<UserDBO> findAllExceptActiveUser(long id);

}
