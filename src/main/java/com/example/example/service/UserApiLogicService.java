package com.example.example.service;

import com.example.example.ifs.CrudInterface;
import com.example.example.model.entity.OrderGroup;
import com.example.example.model.entity.User;
import com.example.example.model.enumclass.UserStatus;
import com.example.example.model.network.Header;
import com.example.example.model.network.request.UserApiRequest;
import com.example.example.model.network.response.ItemApiResponse;
import com.example.example.model.network.response.OrderGroupApiResponse;
import com.example.example.model.network.response.UserApiResponse;
import com.example.example.model.network.response.UserOrderInfoApiResponse;
import com.example.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserApiLogicService extends BaseService<UserApiRequest, UserApiResponse, User> {

    @Autowired
    private OrderGroupApiLogicService orderGroupApiLogicService;

    @Autowired
    private ItemApiLogicService itemApiLogicService;

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

        User newUser = baseRepository.save(user);

        // 3. 생성된 user -> UserApiResponse return


        return Header.OK(response(newUser));
    }

    @Override
    public Header<UserApiResponse> read(Long id) {

        // id -> repository getOne, getById
        Optional<User> optionalUser = baseRepository.findById(id);

        // user -> userApiResponse return

        return optionalUser
                .map(user -> response(user))
                // .map(userApiResponse -> Header.OK(userApiResponse))
                .map(Header::OK)
                .orElseGet(
                        () -> Header.ERROR("데이터 없음")
                );



    }

    @Override
    public Header<UserApiResponse> update(Header<UserApiRequest> request) {

        // 1. data
        UserApiRequest userApiRequest = request.getData();

        // 2. id -> data 찾고
        Optional<User> optionalUser = baseRepository.findById(userApiRequest.getId());

        return optionalUser.map(user -> {
            user.setAccount(userApiRequest.getAccount())
                    .setEmail(userApiRequest.getEmail())
                    .setPhoneNumber(userApiRequest.getPhoneNumber())
                    .setStatus(userApiRequest.getStatus())
                    .setPassword(userApiRequest.getPassword())
                    .setRegisteredAt(userApiRequest.getRegisteredAt())
                    .setUnregisteredAt(userApiRequest.getUnregisteredAt());
            return user;
        }).map(changeUser -> baseRepository.save(changeUser)) // update -> newUser
                .map(newUser-> response(newUser)) // 4. userApiResponse
                .map(userApiResponse -> Header.OK(userApiResponse))
                .orElseGet(()-> Header.ERROR("데이터 없음"));

    }

    @Override
    public Header delete(Long id) {

        // 1. id -> repository -> user
        Optional<User> optionalUser = baseRepository.findById(id);

        // 2. repository -> delete
        return optionalUser.map(user ->{
            baseRepository.delete(user);
            return Header.OK();
        }).
                orElseGet(()->Header.ERROR("데이터 없음"));

    }

    private UserApiResponse response(User user){
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

        return userApiResponse;

    }

    public Header<List<UserApiResponse>> search(Pageable pageable){
        Page<User> users = baseRepository.findAll(pageable);

        List<UserApiResponse> userApiResponseList = users.stream()
                .map(user -> response(user))
                .collect(Collectors.toList());

        // List<UserApiResponse>

        //Header<List<UserApiResponse>>

        return Header.OK(userApiResponseList);
    }

    @Override
    public Header<UserOrderInfoApiResponse> orderInfo(Long id) {

        // user
        User user = baseRepository.getOne(id);
        UserApiResponse userApiResponse = response(user);

        // orderGroup

        List<OrderGroup> orderGroupList = user.getOrderGroupList();
        List<OrderGroupApiResponse> orderGroupApiResponseList = orderGroupList.stream()
                .map(orderGroup -> {

                    OrderGroupApiResponse orderGroupApiResponse = orderGroupApiLogicService.response(orderGroup).getData();

                    List<ItemApiResponse> itemApiResponseList = orderGroup.getOrderDetailList().stream()
                            .map(detail -> detail.getItem())
                            .map(item -> itemApiLogicService.response(item).getData())
                            .collect(Collectors.toList());


                    orderGroupApiResponse.setItemApiResponseList(itemApiResponseList);
                    return orderGroupApiResponse;
                }).collect(Collectors.toList());

        userApiResponse.setOrderGroupApiResponseList(orderGroupApiResponseList);

        UserOrderInfoApiResponse userOrderInfoApiResponse = UserOrderInfoApiResponse.builder()
                .userApiResponse(userApiResponse)
                .build()
                ;

        return Header.OK(userOrderInfoApiResponse);
    }
}
