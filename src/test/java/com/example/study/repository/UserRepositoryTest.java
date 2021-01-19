package com.example.study.repository;

import com.example.study.StudyApplicationTests;
import com.example.study.model.entity.User;
import com.sun.tools.javac.util.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.Optional;

public class UserRepositoryTest extends StudyApplicationTests {


    @Autowired
    private UserRepository userRepository;

    @Test
    public void create(){
        User user = new User();
        user.setAccount("TestUser03");
        user.setEmail("TestUser03@gmail.com");
        user.setPhoneNumber("010-1882-1111");
        user.setCreated_at(LocalDateTime.now());
        user.setCreated_by("admin");

        User newUser = userRepository.save(user);

        System.out.println(newUser);

    }

    @Test
    @Transactional
    public void read(){
        Optional<User> user = userRepository.findById(4L);

        user.ifPresent(selectUser ->{
            selectUser.getOrderDetailList().stream().forEach(detail -> {
                System.out.println(detail.getItem());
            });
        });

    }

    @Test
    public void update(){
        Optional<User> user = userRepository.findById(2L);

        user.ifPresent(selectUser ->{
            selectUser.setAccount("pppp");
            selectUser.setUpdated_at(LocalDateTime.now());
            selectUser.setUpdated_by("test1");

            User newUser = userRepository.save(selectUser);
        });

    }

    @Test
    @Transactional
    public void delete(){
        Optional<User> user = userRepository.findById(1L);

        assert(user.isPresent());

        user.ifPresent(selectUser ->{
            userRepository.delete(selectUser);
        });
        //Optional<User> deleteUser = userRepository.findById(2L);


    }
}
