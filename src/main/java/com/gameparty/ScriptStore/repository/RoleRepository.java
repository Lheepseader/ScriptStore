package com.gameparty.ScriptStore.repository;

import com.gameparty.ScriptStore.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
