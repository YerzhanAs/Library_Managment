package com.example.library_final.repositories;


import com.example.library_final.entities.Books;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface BookRepository extends JpaRepository<Books, Long> {

}
