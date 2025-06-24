// 📁 src/main/java/com/project/tour/entity/BoardComment.java
package com.project.tour.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "board_comment")
public class BoardComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private String content;

    private LocalDateTime createdAt;

    private Long writerId; // ✅ 일반 or 카카오 ID
    private String writerType; // ✅ "USER" or "KAKAO"

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}
