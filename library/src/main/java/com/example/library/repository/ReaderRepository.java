package com.example.library.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.library.model.Reader;

@Repository
public interface ReaderRepository extends JpaRepository<Reader, String> {
    // Tìm khi tên các biến sau chứa từ khóa (ignore case)
    List<Reader> findByNameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrPhoneNumberContainingIgnoreCase(
        String name, String email, String phoneNumber);

    Optional<Reader> findByNameAndEmail(String name, String email);
}
