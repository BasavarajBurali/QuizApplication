package com.question.controller;

import com.question.entity.Question;
import com.question.service.QuestionServiceimpl;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/question")
public class QuestionController {
    @Autowired
    private QuestionServiceimpl questionService;
    @PostMapping
    Question saveQuestion(@RequestBody Question question){
        return  questionService.addQuestion(question);
    }

    @GetMapping
    List<Question> getAllQuestion(){
        return  questionService.getAllQuestion();
    }

    @GetMapping("/{id}")
    Question getById(@PathVariable long id){
        return  questionService.getById(id);
    }

//    @GetMapping("/getQuestionsByQuizId/{quizId}")
//    List<Question> getQuestionsByQuizId(@PathVariable long quizId){
//        return  questionService.getQuestionByquizId(quizId);
//    }

    @GetMapping("/quiz/{quizId}")
    public ResponseEntity<List<Question>> getQuestionsByQuizId(@PathVariable("quizId") long quizId) {
        List<Question> questions = questionService.getQuestionByquizId(quizId);
        if (questions.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(questions);
    }
}
