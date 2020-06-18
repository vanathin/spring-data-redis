package com.learning.springbootredis.dao;


import com.learning.springbootredis.bean.Student;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class StudentRepositoryImpl {

    private final StudentRepository repo;
    private final RedisTemplate<String, Student> redisTemplate;

    public List<Student> getAll() {
        //Take it from Redis cache
        return Optional.of(redisTemplate)
                .map(RedisTemplate::<Integer, Student>opsForHash)
                .map(map -> map.values("STU"))
                .orElseGet(repo::findAll);
    }

    public Student save(Student stu) {
        return Optional.ofNullable(stu)
                .filter(s -> s.getId() == 0)
                .map(student -> {
                    Optional.ofNullable(repo.save(student))
                            .ifPresent(s1 -> redisTemplate.opsForHash().put("STU", s1.getId(), s1));
                    return student;
                }).orElseGet(() -> {
                    Optional.ofNullable(stu)
                            .map(student -> {
                                redisTemplate.opsForHash().delete("STU", student.getId());
                                return student;
                            }).ifPresent(repo::save);
                    //TODO Use executer framework to call delayed.
                    return stu;
                });
    }
}
