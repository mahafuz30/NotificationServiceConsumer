package com.learnkafka.repository;

import com.learnkafka.Entity.BlackList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;
import java.util.UUID;

@Component
public class BlackListRepository {
    @Autowired
    private RedisTemplate template;
    private final String BLACKLIST = "blackList";

    public BlackList addBlackList(BlackList blackList) {
        template.opsForHash().put(BLACKLIST,blackList.getNumber(),blackList);
        return blackList;
    }

    public List<BlackList> findAll(){
        return template.opsForHash().values(BLACKLIST);
    }

    public BlackList findBlackListById (Long id){
        BlackList list = (BlackList) template.opsForHash().get(BLACKLIST,id);
       return list;
    }

    public String deleteBlackListById (Long id){
       template.opsForHash().delete(BLACKLIST,id);
       return "Removed From BlackList";
    }
}
