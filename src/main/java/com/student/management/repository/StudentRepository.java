package com.student.management.repository;

import com.student.management.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface StudentRepository extends JpaRepository<Student, UUID> {

    Optional<Student> findByEmail(String email);

    boolean existsByEmail(String email);

    void deleteByEmail(String email);

    List<Student> findByLocation(String location);

    List<Student> findByFirstNameIgnoreCase(String firstName);

    List<Student> findByLastNameIgnoreCase(String lastName);
}