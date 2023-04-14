package QnABoard.answer.controller;

import QnABoard.answer.dto.AnswerDto;
import QnABoard.answer.entity.Answer;
import QnABoard.answer.mapper.AnswerMapper;
import QnABoard.answer.service.AnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;

@RestController
@RequiredArgsConstructor
@RequestMapping("/answers")
public class AnswerController {

    private final AnswerService answerService;
    private final AnswerMapper mapper;

    @PostMapping
    public ResponseEntity postAnswer (@RequestBody AnswerDto.Post post) {

        Answer registerAnswer = answerService.createAnswer(mapper.answerPostDtoToAnswer(post));

        return new ResponseEntity<>(mapper.answerToAnswerResponseDto(registerAnswer), HttpStatus.CREATED);
    }

    @PatchMapping("/{answerId}")
    public ResponseEntity patchAnswer (@PathVariable @Positive long answerId,
                                       @RequestBody AnswerDto.Patch patch){

        patch.setAnswerId(answerId);
        Answer changeAnswer = answerService.updateAnswer(mapper.answerPatchDtoToAnswer(patch));

        return new ResponseEntity<>(mapper.answerToAnswerResponseDto(changeAnswer), HttpStatus.OK);
    }

    @GetMapping("/{answer-id}")
    public ResponseEntity getAnswer (@PathVariable ("answer-id") @Positive long answerId) {

        Answer findAnswer = answerService.findVerifyAnswer(answerId);

        return new ResponseEntity(mapper.answerToAnswerResponseDto(findAnswer), HttpStatus.OK);
    }

    @DeleteMapping("/{answerId}")
    public ResponseEntity deleteAnswer(@PathVariable @Positive long answerId){
        answerService.deleteAnswer(answerId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
