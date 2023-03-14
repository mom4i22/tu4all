package com.momchil.TU4ALL.repository;

import com.momchil.TU4ALL.dbo.FriendDBO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendRepository extends JpaRepository<FriendDBO, Long> {

}
