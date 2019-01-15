package com.loveayc.utildemo.controller;

import com.loveayc.utildemo.util.IDCardValidDate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 王文博
 * @Title: IDCardValidDateController
 * @ProjectName utildemo
 * @Description: TODO
 * @date 2019/1/1517:10
 */
@RestController
public class IDCardValidDateController {
    @PostMapping("checkIdcard")
    public boolean checkIDCard(String idNum){
        return IDCardValidDate.check(idNum);
    }
}
