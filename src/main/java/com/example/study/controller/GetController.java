package com.example.study.controller;

import com.example.study.model.SearchParam;
import com.example.study.model.network.Header;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api") //
public class GetController {
    @RequestMapping(method = RequestMethod.GET, path = "/getMethod")
    public String getRequest(){

        return "Hi getMethod";
    }

    @GetMapping("/getParameter")    //Localhost:8080/api/getParameter?id=1234&password=abcd
    public String getParameter(@RequestParam String id, @RequestParam(name = "password") String pwd){
        String password = "bbbb";
        System.out.println("id : "+id);
        System.out.println("password : "+pwd);

        return id+pwd;
    }

    // localhost:8080/api/multiParameter?account=abcd&email=fdasdf@gmai....
    @GetMapping("/getMultiParameter")
    public SearchParam getMultiParameter(SearchParam searchParam){
        System.out.println(searchParam.getAccount());
        System.out.println(searchParam.getEmail());
        System.out.println(searchParam.getPage());

        // http로 해보자. jackson을 통해서 자동으로 json으로 변환시켜줌.
        return searchParam;
    }

    @GetMapping("/header")
    public Header getHeader(){
        
        //{"resultCode":"OK" , "description":"OK"}
        return Header.builder().resultCode("OK").description("OK").build();
    }
}
