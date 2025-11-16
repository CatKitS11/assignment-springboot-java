package com.techup.spring.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.techup.spring.demo.entity.Note;

public interface NoteRepository extends JpaRepository<Note, Long> {
}