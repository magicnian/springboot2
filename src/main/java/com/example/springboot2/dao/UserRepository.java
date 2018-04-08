package com.example.springboot2.dao;

import com.example.springboot2.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by liunn on 2018/4/8.
 */
public interface UserRepository extends JpaRepository<User,Integer> {
}
