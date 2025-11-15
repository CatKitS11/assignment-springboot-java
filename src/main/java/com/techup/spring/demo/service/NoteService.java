package com.techup.spring.demo.service; // ระบุ package ของคลาสนี้

import com.techup.spring.demo.entity.Note; // import Note entity เพื่อใช้ใน service methods
import com.techup.spring.demo.repository.NoteRepository; // import NoteRepository เพื่อเข้าถึง database
import lombok.RequiredArgsConstructor; // Lombok annotation สร้าง constructor ที่รับ final fields

import org.springframework.http.HttpStatus; // ใช้สำหรับ HTTP status codes (เช่น 404 NOT_FOUND)
import org.springframework.stereotype.Service; // บอก Spring ว่าคลาสนี้เป็น Service component
import org.springframework.web.server.ResponseStatusException; // ใช้ throw exception พร้อม HTTP status code

import java.util.List; // ใช้สำหรับ return list ของ Note

@Service // บอก Spring ว่าคลาสนี้เป็น Service layer (business logic)
@RequiredArgsConstructor // สร้าง constructor ที่รับ final fields (noteRepository) เพื่อ dependency injection
public class NoteService {

  private final NoteRepository noteRepository; // inject NoteRepository เพื่อเข้าถึง database operations

  /** READ ALL: ดึง Note ทั้งหมด */
  public List<Note> getAll() { // method สำหรับดึง Note ทั้งหมดจาก database
    return noteRepository.findAll(); // เรียก repository method เพื่อดึงข้อมูลทั้งหมดจาก database
  }

  /** READ ONE: ดึง Note ตาม id, ไม่พบ -> 404 */
  public Note getById(Long id) { // method สำหรับดึง Note ตาม id
    return noteRepository.findById(id) // เรียก repository method เพื่อค้นหา Note ตาม id
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Note not found")); // ถ้าไม่พบ ให้ throw exception พร้อม HTTP 404
  }

  /** CREATE: สร้าง Note ใหม่ */
  public Note create(Note req) { // method สำหรับสร้าง Note ใหม่
    Note saved = noteRepository.save(req); // เรียก repository method เพื่อบันทึก Note ลง database
    return saved; // return Note ที่บันทึกแล้ว (มี id ที่ database สร้างให้)
  }

  /** UPDATE: แก้ไข Note ตาม id */
  public Note update(Long id, Note req) { // method สำหรับแก้ไข Note ตาม id
    Note existing = noteRepository.findById(id) // เรียก repository method เพื่อค้นหา Note ที่มีอยู่
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Note not found")); // ถ้าไม่พบ ให้ throw exception พร้อม HTTP 404

    existing.setTitle(req.getTitle()); // อัปเดต title จาก request
    existing.setContent(req.getContent()); // อัปเดต content จาก request
    if (req.getImageUrl() != null) { // ตรวจสอบว่า request มี imageUrl หรือไม่
      existing.setImageUrl(req.getImageUrl()); // ถ้ามี ให้อัปเดต imageUrl
    }

    Note saved = noteRepository.save(existing); // เรียก repository method เพื่อบันทึกการเปลี่ยนแปลงลง database
    return saved; // return Note ที่อัปเดตแล้ว
  }

  /** DELETE: ลบ Note ตาม id */
  public void delete(Long id) { // method สำหรับลบ Note ตาม id (return void เพราะไม่ต้อง return อะไร)
    Note existing = noteRepository.findById(id) // เรียก repository method เพื่อค้นหา Note ที่มีอยู่
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Note not found")); // ถ้าไม่พบ ให้ throw exception พร้อม HTTP 404
    noteRepository.delete(existing); // เรียก repository method เพื่อลบ Note จาก database
  }
}
