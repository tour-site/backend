package com.project.tour.repository;

import com.project.tour.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
// 이 인터페이스는 Member 엔티티에 대한 CRUD 작업을 자동으로 처리합니다.
// JpaRepository를 상속받아 기본적인 CRUD 메서드와 페이징, 정렬 기능을 제공합니다.
// 추가적인 쿼리 메서드를 정의할 수도 있습니다.
// 예를 들어, 이메일로 회원을 찾는 메서드를 추가할 수 있습니다.