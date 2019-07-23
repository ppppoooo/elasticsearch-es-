package com.example.demo.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

/**
 *
 * @CreatTime 2019/7/8
 **/
@Data
@Document(indexName = "test_student", type = "test_student")
public class Student {

    @Id
    private Long id;
    @Field
    private String name;
    @Field
    private String nickname;
    @Field
    private int sex;
    @Field
    private int mathScore;
    @Field
    private int englishScore;
}
