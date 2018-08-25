package com.controller;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class TeController {

    @RequestMapping("/find")
    public Map<String,String> find(@RequestParam("id")Long id){
        System.out.println("参数:"+id);
        Map<String,String> data=new HashMap<>();
        data.put("id","2");
        data.put("name","天下英豪");
        return  data;
    }

    @PostMapping("/user")
    public Map<String,String> user(@RequestBody Map<String,String> map){

        System.out.println(map.get("name")+"\t"+map.get("age"));
        Map<String,String> data=new HashMap<>();
        data.put("msg",map.get("name") +"您好!");
        return  data;

    }
}
