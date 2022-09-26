package com.edu.ulab.app.service.impl;

import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.entity.Person;
import com.edu.ulab.app.exception.NotFoundException;
import com.edu.ulab.app.mapper.UserMapper;
import com.edu.ulab.app.repository.UserRepository;
import com.edu.ulab.app.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository,
                           UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        Person user = userMapper.userDtoToPerson(userDto);
        log.info("Mapped user: {}", user);
        Person savedUser = userRepository.save(user);
        log.info("Saved user: {}", savedUser);
        return userMapper.personToUserDto(savedUser);
    }

    @Override
    public UserDto updateUser(UserDto userDto) {
        if (userRepository.findById(userDto.getId()).orElse(null) != null) {
            userRepository.save(userMapper.userDtoToPerson(userDto));
            log.info("Updated user: {}", userDto);
            return userDto;
        }else{
            throw new NotFoundException("User with that id not found");
        }

    }

    @Override
    public UserDto getUserById(Long id) {
        if (userRepository.findById(id).orElse(null) != null) {
            log.info("Got user by id: {}", id);
            return userMapper.personToUserDto(userRepository.findById(id).get());
        }else throw new NotFoundException("User with that id not found");
    }

    @Override
    public void deleteUserById(Long id) {
        if (userRepository.findById(id).orElse(null) != null) {
            userRepository.deleteById(id);
            log.info("Delete user by id: {}", id);
        }else throw new NotFoundException("User with that id not found");
    }
}
