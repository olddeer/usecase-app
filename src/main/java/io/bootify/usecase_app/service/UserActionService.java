package io.bootify.usecase_app.service;


import io.bootify.usecase_app.domain.UserAction;
import io.bootify.usecase_app.model.UserActionDTO;
import io.bootify.usecase_app.repos.UserActionRepository;
import io.bootify.usecase_app.repos.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserActionService {

    private final UserActionRepository userActionRepository;

    private final UserRepository userRepository;


    public List<UserAction> findAll() {
        return userActionRepository.findAll();
    }

    public Optional<UserAction> findById(Long id) {
        return userActionRepository.findById(id);
    }

    public UserAction save(UserActionDTO userAction) {
        return userActionRepository.save(new UserAction(userAction.getActionId(), userAction.getActionName(),
                userAction.getActionDate(),
                userRepository.findById(userAction.getUserId()).get()));
    }

    public void deleteById(Long id) {
        userActionRepository.deleteById(id);
    }
}
