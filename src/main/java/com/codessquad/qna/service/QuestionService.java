package com.codessquad.qna.service;

import com.codessquad.qna.PageManager;
import com.codessquad.qna.domain.Question;
import com.codessquad.qna.domain.User;
import com.codessquad.qna.exception.IllegalAccessToQuestionException;
import com.codessquad.qna.exception.NotFoundException;
import com.codessquad.qna.repository.QuestionRepository;
import com.codessquad.qna.web.Paginator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public void save(Question question) {
        questionRepository.save(question);
    }

    public Page<Question> getQuestionList(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber - 1, Paginator.QUESTION_BY_PAGE);
        return questionRepository.findAll(pageable);
    }

    public Question findQuestion(Long id) {
        return questionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("해당 게시글이 존재하지 않습니다."));
    }

    public void checkValid(Question question, User user) {
        if (!question.isPostWriter(user)) {
            throw new IllegalAccessToQuestionException("자신의 질문만 접근 가능합니다.");
        }
    }

    public void update(Question question, Question newQuestion, User user) {
        checkValid(question, user);
        question.update(newQuestion);
        questionRepository.save(question);
    }

    public void delete(Question question, User user) {
        checkValid(question, user);
        if (!question.isAnswerWriterSame()) {
            throw new IllegalAccessToQuestionException("다른 사용자의 댓글이 포함되어있습니다.");
        }
        question.delete();
        question.deleteAnswers();
        questionRepository.save(question);
    }
}
