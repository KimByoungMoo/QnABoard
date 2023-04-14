package QnABoard.answer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

public class AnswerDto {

    @AllArgsConstructor
    @Getter
    public static class Post {
        private Long questionId;
        @NotBlank(message = "내용을 입력해주세요")
        private String content;
    }

    @Getter
    @Setter
    public static class Patch {
        private Long answerId;
        private String content;
    }

    @Getter
    @Setter
    public static class Response {
        private Long memberId;
        private String nickname;
        private Long questionId;
        private String questionTitle;
        private Long answerId;
        private String content;
        private LocalDateTime createAt;
    }

    @Getter
    @Setter
    public static class AnswerResponse {
        private Long answerId;
        private String content;
        private LocalDateTime createAt;
    }
}
