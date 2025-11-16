package com.techup.spring.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * ใช้รับข้อมูลจาก Client เวลา "สร้าง/แก้ไข" Note
 * - ใส่ Validation ป้องกัน input ไม่ครบ/format ผิด
 */
@Data
public class NoteRequest {

  @NotBlank(message = "title ห้ามว่าง")
  @Size(max = 255, message = "title ต้องไม่เกิน 255 ตัวอักษร")
  private String title;

  @Size(max = 10_000, message = "content ต้องไม่เกิน 10,000 ตัวอักษร")
  private String content;
}