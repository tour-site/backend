// 📁 src/main/java/com/project/tour/service/AdminMemberService.java
package com.project.tour.service;

import com.project.tour.dto.MemberAdminDto;
import com.project.tour.entity.Member;
import com.project.tour.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminMemberService {

        private final MemberRepository memberRepository;

        public List<MemberAdminDto> searchMembers(String keyword) {
                List<Member> members = memberRepository
                                .findByNameContainingOrNicknameContainingOrPhoneNumberContaining(keyword, keyword,
                                                keyword);

                return members.stream().map(m -> MemberAdminDto.builder()
                                .id(m.getId())
                                .email(m.getEmail())
                                .name(m.getName())
                                .nickname(m.getNickname())
                                .phoneNumber(m.getPhoneNumber())
                                .role(m.getRole())
                                .build()).collect(Collectors.toList());
        }

        private String formatRole(String role) {
                return (role != null && role.startsWith("ROLE_")) ? role : "ROLE_" + (role == null ? "USER" : role);
        }

        public MemberAdminDto updateMember(Long id, MemberAdminDto dto) {
                Member member = memberRepository.findById(id)
                                .orElseThrow(() -> new IllegalArgumentException("사용자 없음"));

                member.setNickname(dto.getNickname());
                member.setPhoneNumber(dto.getPhoneNumber());
                member.setRole(formatRole(dto.getRole())); // 항상 ROLE_ prefix

                Member saved = memberRepository.save(member);

                return MemberAdminDto.builder()
                                .id(saved.getId())
                                .email(saved.getEmail())
                                .name(saved.getName())
                                .nickname(saved.getNickname())
                                .phoneNumber(saved.getPhoneNumber())
                                .role(saved.getRole())
                                .build();
        }

        // 📁 src/main/java/com/project/tour/service/AdminMemberService.java

        public List<MemberAdminDto> getAllMembers() {
                return memberRepository.findAll().stream()
                                .map(m -> MemberAdminDto.builder()
                                                .id(m.getId())
                                                .email(m.getEmail())
                                                .name(m.getName())
                                                .nickname(m.getNickname())
                                                .phoneNumber(m.getPhoneNumber())
                                                .role(m.getRole())
                                                .build())
                                .collect(Collectors.toList());
        }

        public void deleteMember(Long id) {
                Member member = memberRepository.findById(id)
                                .orElseThrow(() -> new IllegalArgumentException("사용자 없음"));
                memberRepository.delete(member);
        }

}
