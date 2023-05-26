package com.gameparty.ScriptStore.repository;

import com.gameparty.ScriptStore.entity.Script;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScriptRepository extends JpaRepository<Script, Long> {
    List<Script> findByPostedTrue();

    List<Script> findByTitleContainsIgnoreCaseAndPosted(String title, Boolean posted, Pageable pageable);
    List<Script> findByTitleContainsIgnoreCaseAndPosted(String title, Boolean posted);


}
