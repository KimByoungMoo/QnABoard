package QnABoard.question.service;

import QnABoard.exception.BusinessLogicException;
import QnABoard.exception.ExceptionCode;
import QnABoard.member.entity.Member;
import QnABoard.member.repository.MemberRepository;
import QnABoard.member.service.MemberService;
import QnABoard.question.entity.Question;
import QnABoard.question.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@RequiredArgsConstructor
@Service
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final MemberService memberService;
    private final MemberRepository memberRepository;

    public Question createQuestion (Question question) {
        question.setMember(memberService.findByEmail());
        return questionRepository.save(question);
    }

    @Transactional
    public Question updateQuestion (Question question) {

        Member member = memberService.findByEmail();
        Question findQuestion = findVerifyQuestion(question.getQuestionId());

        if(findQuestion.getMember().getMemberId() != member.getMemberId()){
            throw new BusinessLogicException(ExceptionCode.QUESTION_UPDATE_NO_PERMISSION);
        }

        Optional.ofNullable(question.getTitle()).ifPresent(title -> findQuestion.setTitle(title));
        Optional.ofNullable(question.getContent()).ifPresent(content -> findQuestion.setContent(content));

        return questionRepository.save(findQuestion);
    }

    public Question findQuestion (long questionId) {

        Question findQuestion = findVerifyQuestion(questionId);

        return findQuestion;
    }

    public Page<Question> findQuestions(int page, int size) {
        return questionRepository.findAll(PageRequest.of(page, size, Sort.by("questionId").descending()));
    }

    @Transactional
    public Page<Question> searchQuestion (String keyword, int page, int size) {
        Page<Question> questions = questionRepository.findByTitleContainingIgnoreCase(keyword, PageRequest.of(page, size, Sort.by("questionId").descending()));
        return questions;
    }

    public void deleteQuestion (long questionId) {

        Member member = memberService.findByEmail();
        Question findQuestion = findVerifyQuestion(questionId);

        if(findQuestion.getMember().getMemberId() != member.getMemberId()){
            throw new BusinessLogicException(ExceptionCode.QUESTION_DELETE_NO_PERMISSION);
        }

        questionRepository.deleteById(questionId);
    }

    public Question findVerifyQuestion (long questionId) {
        Optional<Question> optionalQuestion = questionRepository.findById(questionId);
        Question findQuestion = optionalQuestion.orElseThrow(()-> new BusinessLogicException(ExceptionCode.QUESTION_NOT_FOUND));
        return findQuestion;
    }


}
