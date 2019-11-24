package com.example.example.repositoryTest;

import com.example.example.ExampleApplicationTests;
import com.example.example.model.entity.User;
import com.example.example.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.util.Optional;


public class UserRepositoryTest extends ExampleApplicationTests {

    @Autowired
    private UserRepository userRepository;



    @Test
    public void create(){
        String account = "Test03";
        String password = "Test03";
        String status = "Registered";
        String email = "Test01@gmail.com";
        String phoneNumber = "010-5555-3333";
        LocalDateTime registeredAt = LocalDateTime.now();
        LocalDateTime createdAt = LocalDateTime.now();
        String createdBy = "AdminServer";

        User user = new User();

        user.setAccount(account);
        user.setPassword(password);
        user.setStatus(status);
        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);
        user.setRegisteredAt(registeredAt);


        User newUser = userRepository.save(user);
        Assertions.assertNotNull(newUser);



    }

    @Test
    @Transactional
    public void read(){

        User user = userRepository.findFirstByPhoneNumberOrderByIdDesc("010-5555-2222");

        if(user != null){
            user.getOrderGroupList().stream().forEach(orderGroup -> {

                System.out.println("------------------  주문 내역 ---------------------");
                System.out.println("수령인 : "+ orderGroup.getRevName());
                System.out.println("배송지 : "+orderGroup.getRevAddress());
                System.out.println("총 금액 : "+orderGroup.getTotalPrice());
                System.out.println("총 수량 : " + orderGroup.getTotalQuantity());

                orderGroup.getOrderDetailList().stream().forEach(orderDetail -> {
                    System.out.println("------------------  주문 내역 ---------------------");
                    System.out.println("회사명 : "+ orderDetail.getItem().getPartner().getName());
                    System.out.println("회사명 : "+ orderDetail.getItem().getPartner().getCategory().getTitle());
                    System.out.println("주문 상품명 : "+ orderDetail.getItem().getName());
                    System.out.println("고객 센터 : "+ orderDetail.getItem().getPartner().getCallCenter());
                    System.out.println("주문 상태 : "+ orderDetail.getStatus());
                    System.out.println("도착 시간 : "+orderDetail.getArrivalDate());
                });


            });


        }

        Assertions.assertNotNull(user);

    }


    @Test
    public void update(){
        Optional<User> user = userRepository.findById(1L);

        user.ifPresent(selectUser -> {
            selectUser.setAccount("PPPPP");
            selectUser.setUpdatedAt(LocalDateTime.now());
            selectUser.setUpdatedBy("Update Method()");

            userRepository.save(selectUser);
        });
    }


    @Test
    @Transactional
    public void delete(){
        Optional<User> user = userRepository.findById(1L);

        Assertions.assertTrue(user.isPresent());

        user.ifPresent(selectUser->{
            userRepository.delete(selectUser);
        });

        Optional<User> deleteUser = userRepository.findById(1L);

        Assertions.assertFalse(deleteUser.isPresent());


    }
}
