package QnABoard.question.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.StandardException;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

public class QuestionDto {

    @AllArgsConstructor
    @Getter
    public static class Post {

        @NotBlank(message = "제목을 입력해주세요")
        private String title;

        @NotBlank(message = "내용을 입력해주세요")
        private String content;
    }

    @Getter
    @Setter
    public static class Patch {

        private Long questionId;
        private String title;
        private String content;
    }

    @Getter
    @Setter
    public static class Response {
        private Long memberId;
        private String nickname;
        private Long questionId;
        private String title;
        private String content;
        private LocalDateTime createAt;
    }

    @Getter
    @Setter
    public static class QuestionTitleResponse {
        private Long questionId;
        private String title;
        private LocalDateTime createAt;
    }
}
