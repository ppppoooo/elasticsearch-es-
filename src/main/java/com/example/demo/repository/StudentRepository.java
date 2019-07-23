package com.example.demo.repository;

import com.example.demo.model.Student;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 *
 * @CreatTime 2019/7/8
 **/

public interface StudentRepository extends ElasticsearchRepository<Student, Long> {

}
