package com.example.sv.Repository;



import com.example.sv.Model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Map;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
     Role findByName(String name);
     Role findRoleById(Long id);

}