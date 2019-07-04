package huan.yan.myproject.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CdmiController {

    @GetMapping(value = "/aa/bb")
    public String getStr(){

        return "index";
    }

}
