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

        String account = "Test03";
        String password = "Test03";
        String status = "REGISTERED";
        String email = "Test01@gmail.com";
        String phoneNumber = "010-1111-3333";
        LocalDateTime registeredAt = LocalDateTime.now();
        /*LocalDateTime createdAt = LocalDateTime.now();
        String createdBy = "AdminServer";*/


        User user=new User();
        user.setAccount(account);
        user.setPhoneNumber(phoneNumber);
        user.setStatus(status);
        user.setPassword(password);
        user.setEmail(email);
        user.setRegisteredAt(registeredAt);

        //builder pattern
        User u =User.builder().account(account).password(password).status(status).email(email).build();

        //chain -> when update
        user.setEmail("fdf").setPassword("df");

        User newUser = userRepository.save(user);



    }

    @Test
    @Transactional
    public void read(){
        //Optional 아니여서 반드시 매칭 되야함
        User user = userRepository.findFirstByPhoneNumberOrderByIdDesc("010-1111-2222");

        user.getOrderGroupList().stream().forEach(orderGroup -> {
            System.out.println("총금액 : "+orderGroup.getTotalPrice());
            System.out.println("수령인 : "+orderGroup.getRevName());
            System.out.println("수령지 : "+orderGroup.getRevAddress());

            orderGroup.getOrderDetailList().stream().forEach(orderDetail->{
                System.out.println("파트너사 이름 : "+orderDetail.getItem().getPartner().getName());
                System.out.println("파트너사 카테고리 : "+orderDetail.getItem().getPartner().getCategory().getTitle());

                System.out.println("주문 상품 : "+orderDetail.getItem().getName());
                System.out.println("고객센터 번호 : "+orderDetail.getItem().getPartner().getCallCenter());
                System.out.println("주문의 상태 : "+orderDetail.getStatus());
                System.out.println("도착예정일자 : "+orderDetail.getArrivalDate());


            });
        });
    }

    @Test
    public void update(){
        Optional<User> user = userRepository.findById(2L);

        user.ifPresent(selectUser ->{
            selectUser.setAccount("pppp");
            selectUser.setUpdatedAt(LocalDateTime.now());
            selectUser.setUpdatedBy("test1");

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
