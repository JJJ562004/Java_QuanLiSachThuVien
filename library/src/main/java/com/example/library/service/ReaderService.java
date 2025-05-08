package com.example.library.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.library.model.Reader;
import com.example.library.repository.ReaderRepository;

@Service
public class ReaderService {
    @Autowired
    private ReaderRepository repository;

    public List<Reader> getAllReaders() {
        return repository.findAll();
    }

    public Reader getReaderById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Reader saveReader(Reader reader) {
        return repository.save(reader);
    }

    public void deleteReader(Long id) {
        repository.deleteById(id);
    }

    // Tìm bạn đọc theo keyword
    //Trả về tất cả nếu keyword trống, hoặc kết quả lọc.
    public List<Reader> searchReaders(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return repository.findAll();
        }
        return repository.findByNameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrPhoneNumberContainingIgnoreCase(
            keyword, keyword, keyword
        );
    }
}
