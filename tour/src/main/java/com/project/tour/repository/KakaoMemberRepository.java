// 📁 src/main/java/com/project/tour/repository/KakaoMemberRepository.java
package com.project.tour.repository;

import com.project.tour.entity.KakaoMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KakaoMemberRepository extends JpaRepository<KakaoMember, Long> {

    Optional<KakaoMember> findByEmail(String email);

    Optional<KakaoMember> findByPhoneNumber(String phoneNumber);

    Optional<KakaoMember> findByEmailAndPhoneNumber(String email, String phoneNumber);
}
