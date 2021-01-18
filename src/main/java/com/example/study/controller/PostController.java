package com.example.study.controller;

import com.example.study.model.SearchParam;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class PostController {

    // HTML <form> tag
    // ajax 검색
    // json,xml, multipart-form / test-plain


    @PostMapping(value = "/postMethod")
    public SearchParam PostMethod(@RequestBody SearchParam searchParam){

        return searchParam;
    }

    @PutMapping
    public void put(){

    }

    @PatchMapping
    public void patch(){

    }
}
