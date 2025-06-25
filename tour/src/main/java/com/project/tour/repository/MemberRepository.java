// 📁 src/main/java/com/project/tour/repository/MemberRepository.java
package com.project.tour.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.tour.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);

    Optional<Member> findByPhoneNumber(String phoneNumber);

    Optional<Member> findByEmailAndPhoneNumber(String email, String phoneNumber);

    // 🔍 검색용
    List<Member> findByNameContainingOrNicknameContainingOrPhoneNumberContaining(
            String name, String nickname, String phoneNumber);
}
