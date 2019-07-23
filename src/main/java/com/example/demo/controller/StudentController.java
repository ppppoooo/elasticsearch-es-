package com.example.demo.controller;


import com.example.demo.model.Student;
import com.example.demo.service.StudentService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 *
 * @CreatTime 2019/7/4
 **/

@RestController
@RequestMapping("/student")
public class StudentController {


    @Autowired
    private StudentService studentService;

    @RequestMapping("/save")
    public String save(@RequestParam(name = "id") Long id, @RequestParam(name = "name") String name,
                       @RequestParam(name = "nickname") String nickname, @RequestParam(name = "sex") int sex,
                       @RequestParam(name = "mathScore") int mathScore, @RequestParam(name = "englishScore") int englishScore) {
        Student s = studentService.set(id, name, nickname, sex, mathScore, englishScore);
        if (studentService.save(s)) {
            return "success";
        }
        return "fail";
    }

    @RequestMapping("/get")
    public String get(@RequestParam(name = "id") Long id) {
        return studentService.findById(id).toString();
    }

    @RequestMapping("/getAll")
    public String getAll(@RequestParam(name = "type") int type) {

        Iterable<Student> students = studentService.findAll(0, 5, type);
        String s = "";
        for (Student student : students) {
            s += student.toString() + ".....";
        }
        return s;
    }

    @RequestMapping("/search")
    public String search(@RequestParam(name = "type") int type) {

        Iterable<Student> students = studentService.search(type);
        String s = "";
        for (Student student : students) {
            s += student.toString() + ".....";
        }
        return s;
    }

}


