package com.quiz.service;

import com.quiz.entity.Quiz;

import java.util.List;

public interface QuizService {

    Quiz addQuiz(Quiz quiz);

    List<Quiz> getAllQuiz();

    Quiz getById(Long id);
}
