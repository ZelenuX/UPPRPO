package com.zelenux.upprpo_server_test.testClasses;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    @GetMapping("/")
    public String homePage(){
        return "<h1>Home page:</h1><h2>[Login button]</h2><h2>[Some info]</h2>";
    }
    @GetMapping("/permitted")
    public String permittedPage(@RequestParam(name = "username", required = false, defaultValue = "NONAME") String name){
        return "<h1>Permitted!!! (name - \"" + name + "\")</h1>";
    }
    @PostMapping("/add_data_test")
    public String addData(){
        return "<h1>data add page</h1>";
    }
}
