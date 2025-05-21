package com.example.converter.repository;
import com.example.converter.model.Conversion;
import com.example.converter.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ConversionRepository extends JpaRepository<Conversion, Long> {
    Page<Conversion> findByUser(User user, Pageable pageable);
}
