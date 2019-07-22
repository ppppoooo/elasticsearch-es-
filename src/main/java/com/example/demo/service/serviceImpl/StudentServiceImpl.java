package com.example.demo.service.serviceImpl;

import com.example.demo.model.Student;
import com.example.demo.repository.StudentRepository;
import com.example.demo.service.StudentService;
import com.google.common.collect.Lists;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author mengxiangzhi
 * @CreatTime 2019/7/10
 **/
@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public Student set(Long id, String name, String nickname, int sex, int mathScore, int englishScore) {
        Student student = new Student();
        student.setId(id);
        student.setName(name);
        student.setNickname(nickname);
        student.setSex(sex);
        student.setMathScore(mathScore);
        student.setEnglishScore(englishScore);
        return student;

    }


    @Override
    public boolean save(Student student) {
        Student s = studentRepository.save(student);
        if (null == s) {
            return false;
        }
        return true;
    }

    @Override
    public Student findById(Long id) {
        return studentRepository.findById(id).orElse(null);
    }

    @Override
    public Iterable<Student> findAll(int page, int size, int type) {

        //只分页
        Pageable pageable1 = PageRequest.of(page, size);

        //按名字倒排
        Pageable pageable2 = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "mathScore"));

        //按名字倒排,按分数正排
        List<Sort.Order> list = Lists.newArrayList();
        list.add(Sort.Order.desc("englishScore"));
        list.add(Sort.Order.asc("mathScore"));
        Pageable pageable3 = PageRequest.of(page, size, Sort.by(list));
        Iterable<Student> students = null;
        switch (type) {
            case 1:
                students = studentRepository.findAll(pageable1);
                break;
            case 2:
                students = studentRepository.findAll(pageable2);
                break;
            case 3:
                students = studentRepository.findAll(pageable3);
                break;
            case 4:
                //不分页只排序
                students = studentRepository.findAll(Sort.by(list));
                break;
            default:
                break;
        }
        return students;
    }

    @Override
    public Iterable<Student> search(int type) {
        //查询全部
        QueryBuilder queryBuilder1 = QueryBuilders.matchAllQuery();
        //查询匹配
        QueryBuilder queryBuilder2 = QueryBuilders.matchQuery("name", "a");
        //查询后面字段其中之一等于第一个值得所有数据(类似:mathScore = 80 or englishScore = 80)
        QueryBuilder queryBuilder3 = QueryBuilders.multiMatchQuery("80", "mathScore", "englishScore");
        //模糊查询
        QueryBuilder queryBuilder4 = QueryBuilders.wildcardQuery("nickname", "*ah*");

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        //相当于and
        boolQueryBuilder.must(queryBuilder1);
        //相当于not
        boolQueryBuilder.mustNot(queryBuilder2);
        //相当于or
        boolQueryBuilder.should(queryBuilder4);
        Iterable<Student> students1 = studentRepository.search(boolQueryBuilder);
        System.out.println(students1.toString());
        Iterable<Student> students = null;
        switch (type) {
            case 1:
                students = studentRepository.search(queryBuilder1);
                break;
            case 2:
                students = studentRepository.search(queryBuilder2);
                break;
            case 3:
                students = studentRepository.search(queryBuilder3);
                break;
            case 4:
                //不分页只排序
                students = studentRepository.search(queryBuilder4);
                break;
            default:
                break;
        }
        return students;
    }

}
