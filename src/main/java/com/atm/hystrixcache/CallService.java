package com.atm.hystrixcache;

import org.springframework.stereotype.Service;
import  java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

import org.springframework.stereotype.Service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCollapser;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

@Service
public class CallService {

    // 查询单个Person
    // @HystrixCollapser请求收集器
    // batchMethod：收集的id给谁处理？给getPersons方法处理
    // timerDelayInMilliseconds：收集1s内请求这个服务的id
    // 这个方法只是负责收集参数，实际执行的方法是getPersonList
    @HystrixCollapser(batchMethod = "getPersonList", collapserProperties = { @HystrixProperty(name = "timerDelayInMilliseconds", value = "1000") })
    public Future<Person> getPerson(Integer id) {
        // 无需实现，自动帮我们实现收集
        System.out.println("执行单个查询方法...");
        return null;
    }

    // 查询多个，这个就是实际处理的方法
    // ids：就是上面方法帮我们收集的
    // 这个方法会放到命令中执行
    @HystrixCommand
    public List<Person> getPersonList(List<Integer> ids) {
        List<Person> persons = new ArrayList<Person>();
        for (Integer id : ids) {
            System.out.println(id);
            Person p = new Person();
            p.setAge(18);
            p.setId(id);
            p.setName("atm");
            persons.add(p);
        }
        return persons;
    }
}