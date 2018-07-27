package com.atm.hystrixcache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Future;


@RestController
public class CallController {

    @Autowired
    private CallService collService;

    @RequestMapping(value = "/coll", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String testCollapse() throws Exception {
        // 连续执行3次请求
        Future<Person> f1 = collService.getPerson(1);
        Future<Person> f2 = collService.getPerson(2);
        Future<Person> f3 = collService.getPerson(3);
        Person p1 = f1.get();
        Person p2 = f2.get();
        Person p3 = f3.get();

        return "访问成功";
    }
}