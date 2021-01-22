package com.example.study.service;

import com.example.study.ifs.CrudInterface;
import com.example.study.model.entity.User;
import com.example.study.model.enumClass.UserStatus;
import com.example.study.model.network.Header;
import com.example.study.model.network.request.UserApiRequest;
import com.example.study.model.network.response.UserApiResponse;
import com.example.study.repository.UserRepository;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Builder
public class UserApiLogicService implements CrudInterface<UserApiRequest, UserApiResponse>{

    @Autowired
    private UserRepository userRepository;

    // 1. request data
    // 2. user 생성
    // 3. 생성된 데이터 -> userApiResponse return
    @Override
    public Header<UserApiResponse> create(Header<UserApiRequest> request) {

        //request data
        UserApiRequest userApiRequest = request.getData();

        //user 생성
        User user = User.builder()
                .account(userApiRequest.getAccount())
                .password(userApiRequest.getPassword())
                .status(UserStatus.REGISTERED)
                .phoneNumber(userApiRequest.getPhoneNumber())
                .email(userApiRequest.getEmail())
                .registeredAt(LocalDateTime.now())
                .build();

        User newUser = userRepository.save(user);

        //생성된 데이터 -> userApiResponse return


        return response(newUser);
    }

    @Override
    public Header<UserApiResponse> read(Long id) {
        //id -> repository getOne, getById

        Optional<User> optional = userRepository.findById(id);

        // user -> userApiResponse return
        return optional
                .map(user -> response(user))
                .orElseGet(()->Header.ERROR("데이터 없음"));


        // 람다를 통해서 한번에 가능
        /*
        return userRepository.findById(id)
                .map(user->response(user))
                .orElseGet(
                        ()->Header.ERROR("데이터 없음")
                );

        */


    }

    @Override
    public Header<UserApiResponse> update(Header<UserApiRequest> request) {

        //data 가져오고
        UserApiRequest userApiRequest = request.getData();

        //id를가지고 user 데이터 찾고
        Optional<User> optional=userRepository.findById(userApiRequest.getId());

        return optional.map(user->{
            // data를 가지고 update
            user.setAccount(userApiRequest.getAccount())
                    .setPassword(userApiRequest.getPassword())
                    .setStatus(userApiRequest.getStatus())
                    .setPhoneNumber(userApiRequest.getPhoneNumber())
                    .setEmail(userApiRequest.getEmail())
                    .setRegisteredAt(userApiRequest.getRegisteredAt())
                    .setUnregisteredAt(userApiRequest.getUnregisteredAt());

            return user;

            //userApiResponse 만들어주면 됌
        })
                .map(user->userRepository.save(user))   //update -> newuser
                .map(user->response(user))              //userApiResponse 만듬
                .orElseGet(()->Header.ERROR("데어터 없음"));


    }

    @Override
    public Header delete(Long id) {
        // id->repository->user

        Optional<User> optional = userRepository.findById(id);

        //repository ->delete
        return optional.map(user->{
            userRepository.delete(user);

            return Header.OK();
        }).orElseGet(()->Header.ERROR("데이터 없음"));

        //response return
    }

    private Header<UserApiResponse> response(User user){
        //user -> userApiResponse

        UserApiResponse userApiResponse = UserApiResponse.builder()
                .id(user.getId())
                .account(user.getAccount())
                .password(user.getPassword())
                .status(user.getStatus())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .registeredAt(user.getRegisteredAt())
                .unregisteredAt(user.getUnregisteredAt())
                .build();

        //Header + data reuturn

        return Header.OK(userApiResponse);
    }
}
