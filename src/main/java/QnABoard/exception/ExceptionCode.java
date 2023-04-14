package QnABoard.exception;

import lombok.Getter;

public enum ExceptionCode {
    TOKEN_NOT_VALID(401, "유효하지 않은 토큰입니다."),
    MEMBER_NOT_FOUND(404, "회원이 존재하지 않습니다."),
    MEMBER_EXISTS(409, "이미 존재하는 회원 입니다."),
    MEMBER_NICKNAME_EXISTS(409, "이미 존재하는 닉네임 입니다."),
    MEMBER_NO_PERMISSION(403, "인가되지 않은 사용자 입니다"),

    QUESTION_UPDATE_NO_PERMISSION(404, "질문 작성자만 수정할 수 있습니다."),
    QUESTION_NOT_FOUND(404, "질문 글이 존재하지 않습니다."),
    QUESTION_DELETE_NO_PERMISSION(404, "질문 작성자만 삭제할 수 있습니다."),

    ANSWER_UPDATE_NO_PERMISSION(404, "답변 작성자만 수정할 수 있습니다."),
    ANSWER_NOT_FOUND(404, "답변을 찾을 수 없습니다."),
    ANSWER_DELETE_NO_PERMISSION(404, "답변 작성자만 삭제할 수 있습니다.");

    @Getter
    private int status;

    @Getter
    private String message;

    ExceptionCode(int code, String message) {
        this.status = code;
        this.message = message;
    }
}
