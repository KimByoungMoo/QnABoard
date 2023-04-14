package QnABoard.answer.mapper;

import QnABoard.answer.dto.AnswerDto;
import QnABoard.answer.entity.Answer;
import QnABoard.question.entity.Question;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AnswerMapper {

    default Answer answerPostDtoToAnswer (AnswerDto.Post post) {
        if (post == null) {
            return null;
        }

        Question question = new Question();
        question.setQuestionId(post.getQuestionId());

        Answer answer = new Answer();
        answer.setQuestion(question);
        answer.setContent(post.getContent());

        return answer;
    }

    Answer answerPatchDtoToAnswer (AnswerDto.Patch patch);

    default AnswerDto.Response answerToAnswerResponseDto (Answer answer) {
        AnswerDto.Response response = new AnswerDto.Response();

        response.setMemberId(answer.getMember().getMemberId());
        response.setNickname(answer.getMember().getNickname());
        response.setQuestionId(answer.getQuestion().getQuestionId());
        response.setQuestionTitle(answer.getQuestion().getTitle());
        response.setAnswerId(answer.getAnswerId());
        response.setContent(answer.getContent());
        response.setCreateAt(answer.getCreatedAt());

        return response;
    }
}
