package com.question.service;

import com.question.entity.Question;
import com.question.repo.QuestionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionServiceimpl implements QuestionService {

    @Autowired
    private QuestionRepo questionRepo;

    @Override
    public Question addQuestion(Question question) {
        return questionRepo.save(question);
    }

    @Override
    public List<Question> getAllQuestion() {
        return questionRepo.findAll();
    }

    @Override
    public Question getById(long id) {
        return questionRepo.findById(id).orElseThrow(()->new RuntimeException("question not found"));
    }

    @Override
    public List<Question> getQuestionByquizId(long quizId) {
        return questionRepo.findByquizId(quizId);
    }
}
