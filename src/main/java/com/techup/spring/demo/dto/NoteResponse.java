package com.techup.spring.demo.dto;

import lombok.Builder;
import lombok.Value;

/**
 * ใช้ "ส่งออก" ข้อมูลกลับให้ Client เท่านั้น
 * - เลือก field ที่อยากเปิดเผย
 * - @Value = immutable (มีแต่ getter), ปลอดภัยเวลาแชร์ออกนอกระบบ
 */
@Value
@Builder
public class NoteResponse {
  Long id;
  String title;
  String content;
}