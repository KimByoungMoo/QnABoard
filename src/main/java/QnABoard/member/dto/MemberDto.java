package QnABoard.member.dto;

import QnABoard.answer.dto.AnswerDto;
import QnABoard.member.entity.Member;
import QnABoard.question.dto.QuestionDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.List;

public class MemberDto {

    @AllArgsConstructor
    @Getter
    public static class Post {
        @NotBlank
        @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+.(com|net)$", message = "올바른 이메일 형식이 아닙니다.")
        private String email;

        @NotBlank
        @Length(min = 4, max = 20, message = "비밀번호는 최소 4자 이상, 최대 20자 이하로 입력해주세요.")
        private String password;

        @NotBlank(message = "닉네임을 입력 해주세요")
        @Length(min = 2, max = 10, message = "닉네임은 2자 이상 10자 이하로 입력 해주세요.")
        private String nickname;

        @Nullable
        private String comment;
    }

    @Getter
    @Setter
    public static class Patch {

        @Length(min = 4, max = 20, message = "비밀번호는 최소 4자 이상, 최대 20자 이하로 입력해주세요.")
        private String password;

        @Length(min = 2, max = 10, message = "닉네임은 2자 이상 10자 이하로 입력 해주세요.")
        private String nickname;

        private String comment;
    }

    @Getter
    @Setter
    public static class CreateResponse {
        private MemberInfoResponse memberInfo;
        private LocalDateTime createDate;
    }

    @Getter
    @Setter
    public static class MemberInfoResponse {
        private long memberId;
        private String email;
        private String nickname;
        private Member.MemberStatus memberStatus;
    }

    @Getter
    @Setter
    public static class GetResponse {
        private MemberInfoResponse memberInfo;
        private List<QuestionDto.QuestionTitleResponse> writtenQuestion;
        private List<AnswerDto.AnswerResponse> writtenAnswer;
        private LocalDateTime createDate;
    }




}
