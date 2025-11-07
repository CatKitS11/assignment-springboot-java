package com.techup.spring.demo.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity // บอก Spring ว่าคลาสนี้จะถูก map เป็นตารางใน Database
@Table(name = "users") // ระบุชื่อตารางเป็น "users" เพื่อหลีกเลี่ยง reserved keyword "user"
@Data // สร้าง getter/setter/toString/equals/hashCode ให้อัตโนมัติ
@NoArgsConstructor // Constructor ว่าง (จำเป็นสำหรับ JPA)
@AllArgsConstructor // Constructor ที่มีทุก field
@Builder // ใช้สর้าง object แบบ chain
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Primary key

    @Column(nullable = false, unique = true) // ห้ามซ้ำ ห้ามว่าง
    private String email;

    @Column(nullable = false)
    private String password;
}
