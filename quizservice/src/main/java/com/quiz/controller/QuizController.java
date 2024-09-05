package com.quiz.controller;

import com.quiz.entity.Quiz;
import com.quiz.service.QuestionClient;
import com.quiz.service.QuizService;
import com.quiz.service.QuizServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quiz")
public class QuizController {
    @Autowired
    private QuizServiceImpl quizService;



    @PostMapping("/addQuiz")
    public Quiz addQuiz(@RequestBody Quiz quiz){
        return quizService.addQuiz(quiz);
    }
    @GetMapping("/getAll")
    List<Quiz> getAllQuiz(){
        return quizService.getAllQuiz();
    }

    @GetMapping("/getQuizById/{id}")
    Quiz getById(@PathVariable Long id){
        return  quizService.getById(id);
    }
}
