package com.example.example.controller;

import com.example.example.model.SearchParam;
import com.example.example.model.network.Header;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api") // localhost:8080/api
public class GetController {

    @RequestMapping(method = RequestMethod.GET, path = "/getMethod")
    public String getRequest(){

        return "Hi getMethod";
    }

    @GetMapping("/getParameter")  // localhost:8080/api/getParameter?id=1234&password=addd
    public String getParameter(@RequestParam String id, @RequestParam(name = "password") String pwd){


        return id + pwd;
    }

    @GetMapping("/getMultiParameter")
    public SearchParam getMultiParameter(SearchParam searchParam){
        System.out.println(searchParam.getAccount());
        System.out.println(searchParam.getEmail());
        System.out.println(searchParam.getPage());
        return searchParam;
    }

    @GetMapping("/header")
    public Header getHeader(){
        //{"resultCode" : "OK", ""description" : "OK"}
        return Header.builder().resultCode("OK").description("OK").build();
    }
}
