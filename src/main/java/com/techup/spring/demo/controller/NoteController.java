package com.techup.spring.demo.controller; // ระบุ package ของคลาสนี้

import com.techup.spring.demo.entity.Note; // import Note entity เพื่อใช้เป็น request/response
import com.techup.spring.demo.service.NoteService; // import NoteService เพื่อเรียกใช้ business logic
import lombok.RequiredArgsConstructor; // Lombok annotation สร้าง constructor ที่รับ final fields
import org.springframework.http.ResponseEntity; // ใช้สำหรับสร้าง HTTP response พร้อม status code
import org.springframework.web.bind.annotation.*; // import annotations สำหรับ REST API (@GetMapping, @PostMapping, etc.)

import java.net.URI; // ใช้สร้าง URI สำหรับ Location header ใน POST response
import java.util.List; // ใช้สำหรับ return list ของ Note
import java.util.Map; // ใช้สำหรับ return map ของ String, Object

@RestController // บอก Spring ว่าคลาสนี้เป็น REST Controller (return JSON)
@RequestMapping("/api/notes") // กำหนด base path ของทุก endpoint ในคลาสนี้
@RequiredArgsConstructor // สร้าง constructor ที่รับ final fields (noteService) เพื่อ dependency injection
public class NoteController {

  private final NoteService noteService; // inject NoteService เพื่อเรียกใช้ business logic

  // GET /api/notes → 200
  @GetMapping // รับ HTTP GET request ที่ /api/notes
  public ResponseEntity<List<Note>> getNotes() { // return list ของ Note พร้อม HTTP status 200
    return ResponseEntity.ok(noteService.getAll()); // ส่ง HTTP 200 OK พร้อม list ของ Note ทั้งหมด
  }

  // GET /api/notes/{id} → 200 | 404
  @GetMapping("/{id}") // รับ HTTP GET request ที่ /api/notes/{id} โดย {id} เป็น path variable
  public ResponseEntity<Note> getNote(@PathVariable Long id) { // ดึง id จาก URL path
    return ResponseEntity.ok(noteService.getById(id)); // ส่ง HTTP 200 OK พร้อม Note ที่มี id ตรงกัน
  }

  // POST /api/notes → 201 + Location header
  @PostMapping // รับ HTTP POST request ที่ /api/notes
  public ResponseEntity<Note> create(@RequestBody Note req) { // รับ Note object จาก request body (JSON)
    Note created = noteService.create(req); // เรียก service เพื่อสร้าง Note ใหม่ใน database
    URI location = URI.create("/api/notes/" + created.getId()); // สร้าง URI สำหรับ Location header
    return ResponseEntity.created(location).body(created); // ส่ง HTTP 201 Created พร้อม Location header และ Note ที่สร้าง
  }

  // PUT /api/notes/{id} → 200 | 404
  @PutMapping("/{id}") // รับ HTTP PUT request ที่ /api/notes/{id}
  public ResponseEntity<Note> update(@PathVariable Long id, // ดึง id จาก URL path
                                     @RequestBody Note req) { // รับ Note object จาก request body (JSON)
    return ResponseEntity.ok(noteService.update(id, req)); // ส่ง HTTP 200 OK พร้อม Note ที่อัปเดตแล้ว
  }

  @PatchMapping("/{id}")
public ResponseEntity<Note> partialUpdate(@PathVariable Long id,
                                          @RequestBody Map<String, Object> updates) {
    Note existing = noteService.getById(id);
    
    // อัปเดตเฉพาะ field ที่ส่งมา
    if (updates.containsKey("title")) {
        existing.setTitle((String) updates.get("title"));
    }
    if (updates.containsKey("content")) {
        existing.setContent((String) updates.get("content"));
    }
    if (updates.containsKey("imageUrl")) {
        existing.setImageUrl((String) updates.get("imageUrl"));
    }
    
    Note saved = noteService.update(id, existing);
    return ResponseEntity.ok(saved);
}

  // DELETE /api/notes/{id} → 204
  @DeleteMapping("/{id}") // รับ HTTP DELETE request ที่ /api/notes/{id}
  public ResponseEntity<Void> delete(@PathVariable Long id) { // ดึง id จาก URL path, return void (ไม่มี body)
    noteService.delete(id); // เรียก service เพื่อลบ Note จาก database
    return ResponseEntity.noContent().build(); // ส่ง HTTP 204 No Content (ลบสำเร็จ ไม่มี body)
  }
}
