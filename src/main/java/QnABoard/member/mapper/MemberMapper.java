package QnABoard.member.mapper;

import QnABoard.answer.dto.AnswerDto;
import QnABoard.answer.entity.Answer;
import QnABoard.member.dto.MemberDto;
import QnABoard.member.entity.Member;
import QnABoard.question.dto.QuestionDto;
import QnABoard.question.entity.Question;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface MemberMapper {

    Member memberPostDtoToMember(MemberDto.Post postDto);

    Member memberPatchDtoToMember(MemberDto.Patch patchDto);

    default MemberDto.CreateResponse memberToMemberCreateResponseDto(Member member) {
        MemberDto.CreateResponse response = new MemberDto.CreateResponse();

        response.setMemberInfo(memberList(member));
        response.setCreateDate(member.getCreatedAt());

        return response;
    }

    default MemberDto.GetResponse memberToMemberResponseDto(Member member){
        MemberDto.GetResponse response = new MemberDto.GetResponse();
        response.setMemberInfo(memberList(member));
        response.setCreateDate(member.getCreatedAt());
        response.setWrittenQuestion(questionToQuestionResponseDots(member.getQuestions()));
        response.setWrittenAnswer(answerToAnswerResponseDots(member.getAnswers()));
        return response;
    }

    default MemberDto.MemberInfoResponse memberList (Member member) {
        MemberDto.MemberInfoResponse response = new MemberDto.MemberInfoResponse();
        response.setMemberId(member.getMemberId());
        response.setEmail(member.getEmail());
        response.setNickname(member.getNickname());
        response.setMemberStatus(member.getMemberStatus());
        return response;
    }

    default List<QuestionDto.QuestionTitleResponse> questionToQuestionResponseDots (List<Question> questions) {
        return questions.stream().map(question -> {
            QuestionDto.QuestionTitleResponse response = new QuestionDto.QuestionTitleResponse();

            response.setQuestionId(question.getQuestionId());
            response.setTitle(question.getTitle());
            response.setCreateAt(question.getCreatedAt());

            return response;
        }).collect(Collectors.toList());
    }

    default List<AnswerDto.AnswerResponse> answerToAnswerResponseDots (List<Answer> answers) {
        return answers.stream().map(answer -> {
            AnswerDto.AnswerResponse response = new AnswerDto.AnswerResponse();

            response.setAnswerId(answer.getAnswerId());
            response.setContent(answer.getContent());
            response.setCreateAt(answer.getCreatedAt());

            return response;
        }).collect(Collectors.toList());
    }
}
