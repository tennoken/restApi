package com.example.example.service;

import com.example.example.ifs.CrudInterface;
import com.example.example.model.entity.User;
import com.example.example.model.enumclass.UserStatus;
import com.example.example.model.network.Header;
import com.example.example.model.network.request.UserApiRequest;
import com.example.example.model.network.response.UserApiResponse;
import com.example.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserApiLogicService implements CrudInterface<UserApiRequest, UserApiResponse> {

    @Autowired
    private UserRepository userRepository;

    // 1. request data
    // 2. user 생성
    // 3. 생성된 데이터 -> UserApiResponse return

    @Override
    public Header<UserApiResponse> create(Header<UserApiRequest> request) {

        // 1. request data
        UserApiRequest userApiRequest = request.getData();

        // 2. User 생성
        User user = User.builder()
                .account(userApiRequest.getAccount())
                .password(userApiRequest.getPassword())
                .status(UserStatus.REGISTERED)
                .phoneNumber(userApiRequest.getPhoneNumber())
                .email(userApiRequest.getEmail())
                .registeredAt(LocalDateTime.now())
                .build();

        User newUser = userRepository.save(user);

        // 3. 생성된 user -> UserApiResponse return


        return response(newUser);
    }

    @Override
    public Header<UserApiResponse> read(Long id) {

        // id -> repository getOne, getById
        Optional<User> optionalUser = userRepository.findById(id);

        // user -> userApiResponse return

        return optionalUser
                .map(user -> response(user))
                .orElseGet(
                        () -> Header.ERROR("데이터 없음")
                );



    }

    @Override
    public Header<UserApiResponse> update(Header<UserApiRequest> request) {

        // 1. data
        UserApiRequest userApiRequest = request.getData();

        // 2. id -> data 찾고
        Optional<User> optionalUser = userRepository.findById(userApiRequest.getId());

        return optionalUser.map(user -> {
            user.setAccount(userApiRequest.getAccount())
                    .setEmail(userApiRequest.getEmail())
                    .setPhoneNumber(userApiRequest.getPhoneNumber())
                    .setStatus(userApiRequest.getStatus())
                    .setPassword(userApiRequest.getPassword())
                    .setRegisteredAt(userApiRequest.getRegisteredAt())
                    .setUnregisteredAt(userApiRequest.getUnregisteredAt());
            return user;
        }).map(changeUser -> userRepository.save(changeUser)) // update -> newUser
                .map(newUser-> response(newUser)) // 4. userApiResponse
                .orElseGet(()-> Header.ERROR("데이터 없음"));

    }

    @Override
    public Header delete(Long id) {

        // 1. id -> repository -> user
        Optional<User> optionalUser = userRepository.findById(id);

        // 2. repository -> delete
        return optionalUser.map(user ->{
            userRepository.delete(user);
            return Header.OK();
        }).
                orElseGet(()->Header.ERROR("데이터 없음"));

    }

    private Header<UserApiResponse> response(User user){
        // user -> userApiResponse
        UserApiResponse userApiResponse = UserApiResponse.builder()
                .id(user.getId())
                .account(user.getAccount())
                .password(user.getPassword())
                .phoneNumber(user.getPhoneNumber())
                .email(user.getEmail())
                .status(user.getStatus())
                .registeredAt(user.getRegisteredAt())
                .unregisteredAt(user.getUnregisteredAt())
                .build();

        return Header.OK(userApiResponse);

    }
}
