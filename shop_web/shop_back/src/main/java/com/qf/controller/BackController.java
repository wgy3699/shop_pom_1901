package com.qf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @version 1.0
 * @user lao王
 * @date 2019/5/20 17:47
 */
@Controller
@RequestMapping("/back")
public class BackController {

    @RequestMapping("/{toPage}")
    public String toPage(@PathVariable("toPage") String toPage){
        return toPage;
    }
}
