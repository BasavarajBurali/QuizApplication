package com.quiz.service;

import com.quiz.entity.Question;
import com.quiz.entity.Quiz;
import com.quiz.repo.QuizRepo;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuizServiceImpl implements QuizService{


    private QuizRepo quizRepo;


    private QuestionClient questionClient;


    public QuizServiceImpl(QuizRepo quizRepo, QuestionClient questionClient) {
        this.quizRepo = quizRepo;
        this.questionClient = questionClient;
    }

    @Override
    public Quiz addQuiz(Quiz quiz) {
        return quizRepo.save(quiz);
    }

    @Override
//    public List<Quiz> getAllQuiz() {
//      List<Quiz> quizzes= quizRepo.findAll();
//        List<Quiz> newQuizList=   quizzes.stream().map(quiz -> {quiz.setQuestions(questionClient.
//                        getQuestionOfQuiz(quiz.getId()));return quiz;}).collect(Collectors.toList());
//                       return newQuizList;
//
//    }


    public List<Quiz> getAllQuiz() {
        List<Quiz> quizzes = quizRepo.findAll();
        return quizzes.stream().map(quiz -> {
            try {
                quiz.setQuestions(questionClient.getQuestionOfQuiz(quiz.getId()));
            } catch (FeignException e) {
                if (e.status() == 404) {
                    // Handle the 404 Not Found case, e.g., return an empty list
                    quiz.setQuestions(Collections.emptyList());
                } else {
                    // Rethrow the exception or handle other HTTP statuses
                    throw e;
                }
            }
            return quiz;
        }).collect(Collectors.toList());
    }



    @Override
//    public Quiz getById(Long id) {
//        Quiz quiz=quizRepo.findById(id).orElseThrow(()->new RuntimeException("quiz not found"));
//        quiz.setQuestions(questionClient.getQuestionOfQuiz(quiz.getId()));
//        return quiz;
//
//    }

    public Quiz getById(Long id) {
        // Retrieve the quiz, or throw an exception if not found
        Quiz quiz = quizRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Quiz not found with ID: " + id));

        try {
            // Retrieve questions associated with the quiz
            List<Question> questions = questionClient.getQuestionOfQuiz(quiz.getId());
            quiz.setQuestions(questions);
        } catch (FeignException e) {
            // Handle Feign exception (e.g., 404 or other errors)
            if (e.status() == 404) {
                // Handle case where questions are not found
                quiz.setQuestions(Collections.emptyList());
            } else {
                // Log and/or rethrow other Feign exceptions
                System.err.println("Error fetching questions for quiz ID " + quiz.getId() + ": " + e.getMessage());
                throw new RuntimeException("Error fetching questions", e);
            }
        }

        return quiz;
    }
}
