package QnABoard.answer.service;

import QnABoard.answer.dto.AnswerDto;
import QnABoard.answer.entity.Answer;
import QnABoard.answer.repository.AnswerRepository;
import QnABoard.exception.BusinessLogicException;
import QnABoard.exception.ExceptionCode;
import QnABoard.member.entity.Member;
import QnABoard.member.service.MemberService;
import QnABoard.question.entity.Question;
import QnABoard.question.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final MemberService memberService;
    private final QuestionService questionService;

    public Answer createAnswer (Answer answer) {
        answer.setQuestion(questionService.findVerifyQuestion(answer.getQuestion().getQuestionId()));
        answer.setMember(memberService.findByEmail());
        return answerRepository.save(answer);
    }

    @Transactional
    public Answer updateAnswer(Answer answer) {
        Member member = memberService.findByEmail();
        Answer findAnswer = findVerifyAnswer(answer.getAnswerId());

        if(findAnswer.getMember().getMemberId() != member.getMemberId()){
            throw new BusinessLogicException(ExceptionCode.ANSWER_UPDATE_NO_PERMISSION);
        }

        Optional.ofNullable(answer.getContent()).ifPresent(content -> findAnswer.setContent(content));

        return answerRepository.save(findAnswer);
    }

    @Transactional
    public void deleteAnswer(long answerId){
        Answer findAnswer = findVerifyAnswer(answerId);
        Member member = memberService.findByEmail();

        if(findAnswer.getMember().getMemberId() != member.getMemberId()){
            throw new BusinessLogicException(ExceptionCode.ANSWER_DELETE_NO_PERMISSION);
        }

        answerRepository.deleteById(answerId);
    }

    public Answer findVerifyAnswer (long answerId) {
        Optional<Answer> optionalQuestion = answerRepository.findById(answerId);
        Answer findAnswer = optionalQuestion.orElseThrow(()-> new BusinessLogicException(ExceptionCode.ANSWER_NOT_FOUND));
        return findAnswer;
    }
}
