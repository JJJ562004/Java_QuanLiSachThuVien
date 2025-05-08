package com.example.library.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.library.model.Reader;

@Repository
public interface ReaderRepository extends JpaRepository<Reader, Long> {
    // Tìm khi tên các biến sau chứa từ khóa (ignore case)
    List<Reader> findByNameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrPhoneNumberContainingIgnoreCase(
        String name, String email, String phoneNumber);
}
