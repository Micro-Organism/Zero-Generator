package com.zero.generator.controller;

import com.zero.generator.common.util.GeneratorUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/generator/mysql")
public class MySQLGeneratorController {

    @GetMapping("execute")
    public void execute() {
        //执行 mysql
//        GeneratorUtil.getInstance().generatorMySQL();
    }

}
