package com.test.aidemo1.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class HelloController {
    @RequestMapping(value = "/hello",method = RequestMethod.GET)
    @ResponseBody
    public static  String Hello(){
        return "test";
    }
}
