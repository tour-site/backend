// 📁 src/main/java/com/project/tour/entity/BoardLike.java
package com.project.tour.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "board_like", uniqueConstraints = @UniqueConstraint(columnNames = { "board_id", "writer_id",
        "writer_type" }))
public class BoardLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @Column(name = "writer_id", nullable = false)
    private Long writerId;

    @Column(name = "writer_type", nullable = false)
    private String writerType; // "USER" 또는 "KAKAO"
}
