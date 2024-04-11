package com.example.project.repository;

import com.example.project.dto.UserAndRoleDto;
import com.example.project.dto.UserInforDto;
import com.example.project.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {

    @Query(nativeQuery = true,
    value = "SELECT u.user_id as userId, u.user_name as userName, u.full_name as fullName, " +
            "u.coding_language as codingLanguage, u.email as email , c.center_name as centerName " +
            "FROM users u " +
            "INNER JOIN center c " +
            "on c.center_id = u.center_id " +
            "WHERE u.user_name like %?1% and u.full_name like %?2% " +
            "and u.coding_language like %?3% and u.email like %?4% ")
    List<UserInforDto> getUser(String userName, String fullName, String codingLanguage, String email);

    @Query(nativeQuery = true,
    value = "SELECT u.user_id as userId, r.role_name as roleName " +
            "FROM users u " +
            "INNER JOIN user_role ur on ur.user_id = u.user_id " +
            "INNER JOIN role r on r.role_id = ur.role_id " +
            "WHERE u.user_id = ?1 ")
    UserAndRoleDto roleNamefind(Long userId);

    Optional<Users> findByUserName(String username);

    Boolean existsByUserName(String username);

    Boolean existsByEmail(String email);

    Boolean existsByUserId(Long userId);
}
