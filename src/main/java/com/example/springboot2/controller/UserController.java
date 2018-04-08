package com.example.springboot2.controller;

import com.example.springboot2.dao.UserRepository;
import com.example.springboot2.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * webflux初体验
 * Created by liunn on 2018/4/8.
 */
@RestController
@RequestMapping("/api/user")
@Slf4j
public class UserController {

    @Resource
    private UserRepository userRepository;

    private List<User> userList = new ArrayList<>();

    @PostConstruct
    public void init() {
        userList = userRepository.findAll();
    }

    @GetMapping("queryAll")
    @ResponseBody
    public List<User> queryAll() {
        List<User> list = userRepository.findAll();
        return list;
    }


    @GetMapping("queryById/{id}")
    @ResponseBody
    public User queryById(@PathVariable Integer id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElse(null);
    }


    /**
     * 获取所有用户
     *
     * @return
     */
    @GetMapping("/index")
    public Flux<User> getAll() {
        return Flux.fromStream(userList.stream());
    }

    /**
     * 根据id获取指定的用户
     *
     * @param id
     * @return
     */
    @GetMapping("/id")
    public Mono<User> getCustomer(@PathVariable Integer id) {
        return Mono.justOrEmpty(userRepository.findById(id));
    }

    /**
     * 创建用户
     *
     * @param user
     * @return
     */
    @PostMapping("/post")
    public Mono<ResponseEntity<String>> postUser(@RequestBody User user) {
        userRepository.save(user);
        log.info("########### POST:" + user);
        return Mono.just(new ResponseEntity<String>("Post Successfully!", HttpStatus.OK));
    }


    /**
     * 修改用户
     *
     * @param id
     * @return
     */
    @GetMapping("/put/{id}")
    public Mono<ResponseEntity<String>> putUser(@PathVariable Integer id) {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            return Mono.just(new ResponseEntity<String>("User is not present!", HttpStatus.OK));
        } else {
            user.get().setName("putUser");
            userRepository.save(user.get());
            return Mono.just(new ResponseEntity<String>("pust User success!", HttpStatus.OK));
        }
    }

    /**
     * 删除用户
     *
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public Mono<ResponseEntity<String>> deleteUser(@PathVariable Integer id) {
        userRepository.deleteById(id);
        return Mono.just(new ResponseEntity<String>("delet User success!", HttpStatus.OK));
    }

}
