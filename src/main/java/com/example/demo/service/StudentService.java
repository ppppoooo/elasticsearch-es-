package com.example.demo.service;

import com.example.demo.model.Student;

/**
 * @Author mengxiangzhi
 * @CreatTime 2019/7/8
 **/
public interface StudentService {

    Student set(Long id, String name, String nickname, int sex, int mathScore, int englishScore);

    boolean save(Student student);

    Student findById(Long id);

    Iterable<Student> findAll(int page, int size, int type);

    Iterable<Student> search(int type);
}
