package com.learning.springbootredis.dao;

import com.learning.springbootredis.bean.Student;
import org.springframework.data.jpa.repository.JpaRepository;


public interface StudentRepository extends JpaRepository<Student, String> {
}
