package QnABoard.member.controller;

import QnABoard.member.dto.MemberDto;
import QnABoard.member.entity.Member;
import QnABoard.member.mapper.MemberMapper;
import QnABoard.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;
    private final MemberMapper mapper;

    @PostMapping("/signup")
    public ResponseEntity postMember (@Valid @RequestBody MemberDto.Post post) {
        Member createdMember = memberService.createMember(mapper.memberPostDtoToMember(post));
        return new ResponseEntity(mapper.memberToMemberCreateResponseDto(createdMember), HttpStatus.CREATED);
    }

    @PatchMapping
    public ResponseEntity updateMemberInfo(@Valid @RequestBody MemberDto.Patch patch) {
        Member updatedMember = memberService.updateMemberInfo(mapper.memberPatchDtoToMember(patch));
        return new ResponseEntity(mapper.memberToMemberResponseDto(updatedMember), HttpStatus.OK);
    }

    @GetMapping("/mypage")
    public ResponseEntity getMember () {
        Member findMember = memberService.findByEmail();
        MemberDto.GetResponse response = mapper.memberToMemberResponseDto(findMember);
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity logout (HttpServletRequest request) {
        memberService.logout(request);
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity deleteMember (HttpServletRequest request) {
        String accessToken = request.getHeader("Authorization").substring(7);
        memberService.deleteMember(request);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
