package com.example.study.model.network;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Header<T> {

    //api 통신시간
    //@JsonProperty("transaction_time")  api상에서 반환 이름 바꿔주는거
    private LocalDateTime transactionTime;


    //api 응답코드
    private String resultCode;


    //api 부가설명
    private String description;


    private T data;



    //그냥 OK
    public static <T> Header<T> OK(){
        return (Header<T>)Header.builder()
                .transactionTime(LocalDateTime.now())
                .resultCode("Ok")
                .description("OK")
                .build();
    }

    //data가 들어있는 OK
    public static <T> Header<T> OK(T data){
        return (Header<T>)Header.builder()
                .transactionTime(LocalDateTime.now())
                .resultCode("Ok")
                .description("OK")
                .data(data)
                .build();
    }

    //그냥 error
    public static <T> Header<T> ERROR(String description){
        return (Header<T>)Header.builder()
                .transactionTime(LocalDateTime.now())
                .resultCode("ERROR")
                .description(description)
                .build();
    }



}
