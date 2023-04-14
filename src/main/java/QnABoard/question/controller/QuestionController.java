package QnABoard.question.controller;

import QnABoard.question.dto.QuestionDto;
import QnABoard.question.entity.Question;
import QnABoard.question.mapper.QuestionMapper;
import QnABoard.question.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/questions")
public class QuestionController {

    private final QuestionService questionService;
    private final QuestionMapper mapper;

    @PostMapping
    public ResponseEntity postQuestion(@RequestBody QuestionDto.Post post) {

        Question registerQuestion = questionService.createQuestion(mapper.questionPostDtoToQuestion(post));

        return new ResponseEntity(mapper.questionToQuestionResponseDto(registerQuestion), HttpStatus.CREATED);
    }

    @PatchMapping("/{question-id}")
    public ResponseEntity patchQuestion(@PathVariable ("question-id") long questionId,
                                        @RequestBody QuestionDto.Patch patch) {

        patch.setQuestionId(questionId);
        Question changeQuestion = questionService.updateQuestion(mapper.questionToQuestionPatchDto(patch));

        return new ResponseEntity(mapper.questionToQuestionResponseDto(changeQuestion), HttpStatus.OK);
    }

    @GetMapping("/{question-id}")
    public ResponseEntity getQuestion(@PathVariable ("question-id") long questionId) {

        Question question = questionService.findQuestion(questionId);

        return new ResponseEntity(mapper.questionToQuestionResponseDto(question),HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity searchQuestion (@RequestParam("q") String keyword,
                                          @RequestParam int page, @RequestParam int size) {
        Page<Question> searchQuestions = questionService.searchQuestion(keyword, page-1, size);
        List<Question> questions = searchQuestions.getContent();

        return new ResponseEntity(mapper.questionToQuestionResponseDtos(questions), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getQuestions (@Positive @RequestParam int page,
                                        @Positive @RequestParam int size) {

        Page<Question> pageQuestions = questionService.findQuestions(page -1, size);
        List<Question> questions = pageQuestions.getContent();

        return new ResponseEntity<>(mapper.questionToQuestionResponseDtos(questions), HttpStatus.OK);
    }

    @DeleteMapping("/{question-id}")
    public ResponseEntity deleteQuestion(@PathVariable ("question-id") long questionId) {

        questionService.deleteQuestion(questionId);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
