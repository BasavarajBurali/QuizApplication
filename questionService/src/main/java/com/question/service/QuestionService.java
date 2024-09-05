package com.question.service;

import com.question.QuestionServiceApplication;
import com.question.entity.Question;

import java.util.List;

public interface QuestionService {
    Question addQuestion(Question question);

    List<Question> getAllQuestion();

    Question getById(long id);

    List<Question> getQuestionByquizId(long quizId);
}
