package com.example.library.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.library.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, String> {
    // Tìm khi title hoặc author hoặc category chứa từ khóa (ignore case)
    List<Book> findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCaseOrCategoryContainingIgnoreCase(
        String title, String author, String category);
}
