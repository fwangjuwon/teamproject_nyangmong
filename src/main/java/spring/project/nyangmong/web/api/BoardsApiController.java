package spring.project.nyangmong.web.api;

import javax.servlet.http.HttpSession;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import spring.project.nyangmong.domain.boardlikes.BoardLikes;
import spring.project.nyangmong.domain.boards.Boards;
import spring.project.nyangmong.domain.user.User;
import spring.project.nyangmong.service.BoardsService;
import spring.project.nyangmong.web.dto.members.ResponseDto;
import spring.project.nyangmong.web.dto.members.boards.DetailResponseDto;
import spring.project.nyangmong.web.dto.members.boards.WriteDto;

@RequiredArgsConstructor
@RestController
public class BoardsApiController {
    private final BoardsService boardsService;
    private final HttpSession session;

    @DeleteMapping("/s/api/post/{id}")
    public ResponseDto<?> deleteById(@PathVariable Integer id) {
        boardsService.글삭제하기(id);

        return new ResponseDto<>(1, "성공", null);
    }

    //
    @GetMapping("/api/post/{id}")
    public ResponseDto<?> detail(@PathVariable Integer id) {
        //

        Boards boardsEntity = boardsService.글상세보기(id);
        User principal = (User) session.getAttribute("principal");
        boolean auth = false;

        if (principal != null) {

            if (principal.getId() == boardsEntity.getUser().getId()) {
                auth = true;
            }
        }

        DetailResponseDto detailResponseDto = new DetailResponseDto(boardsEntity, auth); // comment null
        return new ResponseDto<>(1, "성공", detailResponseDto); // comment 생성됨 = MessageConverter
        //
    }
    //

    @GetMapping("/api/post")
    public ResponseDto<?> list(Integer page) {
        Page<Boards> boards = boardsService.게시글목록(page);
        // 응답의 DTO를 만들어서 <- posts 를 옮김. (라이브러리 있음)
        return new ResponseDto<>(1, "성공", boards);
    }

    @PostMapping("/s/post")
    public ResponseDto<?> write(@RequestBody WriteDto writeDto) {

        User principal = (User) session.getAttribute("principal");
        Boards boards = writeDto.toEntity(principal);
        // 원래는 그냥 dto바로 넘겼는데, 지금 dto를 넘기면 session값 두개 넘겨야 해서 하나로 합쳐서 넘김
        boardsService.글쓰기(boards);

        return new ResponseDto<>(1, "성공", null);
    }

    @GetMapping("/api/likelist")
    public ResponseDto<?> likeList(Integer page) {
        Page<Boards> board = boardsService.게시글목록(page);
        // 응답의 DTO를 만들어서 <- posts 를 옮김. (라이브러리 있음)
        return new ResponseDto<>(1, "성공", board);
    }
}