package spring.project.nyangmong.web;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.RequiredArgsConstructor;
import spring.project.nyangmong.domain.pet.Pet;
import spring.project.nyangmong.domain.user.User;
import spring.project.nyangmong.handle.ex.CustomException;
import spring.project.nyangmong.service.PetService;

@RequiredArgsConstructor
@Controller
public class PetController {

    private final PetService petService;
    private final HttpSession session;

    // 회원 정보 페이지 - 반려동물 정보 최초 등록하기
    @PostMapping("/s/pet/{userId}/info")
    public String petInfo(@PathVariable Integer userId, Pet pet) {
        // 권한 - 세션의 아이디와 {id}를 비교
        User principal = (User) session.getAttribute("principal");
        if (principal.getId() == userId) {
            petService.펫등록하기(userId, pet);
            return "redirect:/s/user/{userId}/detail";
        } else {
            throw new CustomException("회원 정보 보기 권한이 없습니다.");
        }
    }
}