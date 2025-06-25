// 📁 src/main/java/com/project/tour/controller/admin/AdminMemberController.java
package com.project.tour.controller;

import com.project.tour.dto.MemberAdminDto;
import com.project.tour.service.AdminMemberService;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/members")
@RequiredArgsConstructor
public class AdminMemberController {

    private final AdminMemberService adminMemberService;

    // ✅ 전체 사용자 목록
    @GetMapping
    public List<MemberAdminDto> getAllMembers() {
        return adminMemberService.getAllMembers();
    }

    // 🔍 사용자 검색
    @GetMapping("/search")
    public List<MemberAdminDto> searchMembers(@RequestParam String keyword) {
        return adminMemberService.searchMembers(keyword);
    }

    // ✏️ 사용자 수정
    @PutMapping("/{id}")
    public MemberAdminDto updateMember(@PathVariable Long id, @RequestBody MemberAdminDto dto) {
        return adminMemberService.updateMember(id, dto);
    }

    // 🗑️ 사용자 삭제
    @DeleteMapping("/{id}")
    public void deleteMember(@PathVariable Long id) {
        adminMemberService.deleteMember(id);
    }
}
