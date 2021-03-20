package com.codessquad.qna.service;

import com.codessquad.qna.domain.User;
import com.codessquad.qna.exception.NotFoundException;
import com.codessquad.qna.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public List<User> getUserList() {
        return userRepository.findAll();
    }

    public User findUserByUserId(String userId) {
        return userRepository.findByUserId(userId).orElseThrow(() -> new NotFoundException("해당 유저가 존재하지 않습니다."));
    }

    public void checkValidByPassword(User user, String password) {
        if (!user.isPasswordMatching(password)) {
            throw new IllegalStateException("아이디 혹은 비밀번호가 일치하지 않습니다.");
        }
    }

    public void checkValidById(User user, Long id) {
        if (!user.isIdMatching(id)) {
            throw new IllegalStateException("자신의 정보만 수정할 수 있습니다.");
        }
    }

    public void update(User user, User updatedUser) {
        user.update(updatedUser);
        save(user);
    }
}
