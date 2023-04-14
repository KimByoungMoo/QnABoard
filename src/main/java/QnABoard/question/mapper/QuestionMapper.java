package QnABoard.question.mapper;

import QnABoard.question.dto.QuestionDto;
import QnABoard.question.entity.Question;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface QuestionMapper {

    Question questionPostDtoToQuestion (QuestionDto.Post post);

    Question questionToQuestionPatchDto (QuestionDto.Patch patch);

    default QuestionDto.Response questionToQuestionResponseDto (Question question) {
        QuestionDto.Response response = new QuestionDto.Response();

        response.setMemberId(question.getMember().getMemberId());
        response.setNickname(question.getMember().getNickname());
        response.setQuestionId(question.getQuestionId());
        response.setTitle(question.getTitle());
        response.setContent(question.getContent());
        response.setCreateAt(question.getCreatedAt());

        return response;
    }

    List<QuestionDto.Response> questionToQuestionResponseDtos (List<Question> questions);
}
