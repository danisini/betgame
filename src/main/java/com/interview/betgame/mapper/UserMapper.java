package com.interview.betgame.mapper;

import com.interview.betgame.dto.UserDTO;
import com.interview.betgame.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserDTO toUserDTO(User user) {
        if (user == null) {
            return null;
        }

        UserDTO userDTO = new UserDTO();
        userDTO.setName(user.getName());

        return userDTO;
    }


    public User toUserEntity(UserDTO userDTO) {
        if(userDTO == null) {
            return null;
        }
        User user = new User(userDTO.getName());

        return user;
    }
}
