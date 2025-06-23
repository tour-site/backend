// 📁 src/main/java/com/project/tour/entity/KakaoMember.java
package com.project.tour.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KakaoMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "kakao_member_id")
    private Long id;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "profile_image")
    private String profileImage;

    @Column(name = "gender", nullable = false)
    private String gender;

    @Column(name = "birthday")
    private String birthday;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;
}