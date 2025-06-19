// 📁 src/main/java/com/project/tour/repository/MemberRepository.java
package com.project.tour.repository;

import com.project.tour.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
}
