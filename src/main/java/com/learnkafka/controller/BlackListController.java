package com.learnkafka.controller;

import com.learnkafka.Entity.BlackList;
import com.learnkafka.repository.BlackListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("v1/blacklist")
public class BlackListController {
    @Autowired
    BlackListRepository repository;

    @GetMapping
    public List<BlackList> getAllBlackList(){
        return repository.findAll();
    }

    @PostMapping
    public BlackList addBlackList(@RequestBody BlackList blackList){
        return repository.addBlackList(blackList);
    }

    @DeleteMapping("{number}")
    public String deleteBlackListById(@PathVariable Long number){
        return repository.deleteBlackListById(number);
    }

    @GetMapping("{number}")
    public ResponseEntity<BlackList> getBlackListByNumber(@PathVariable Long number){
        BlackList list = repository.findBlackListById(number);
        return list!=null?ResponseEntity.status(HttpStatus.FOUND).body(list):
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
}
