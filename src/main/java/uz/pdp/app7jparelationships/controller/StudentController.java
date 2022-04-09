package uz.pdp.app7jparelationships.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import uz.pdp.app7jparelationships.entity.Student;
import uz.pdp.app7jparelationships.repository.StudentRepository;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    StudentRepository studentRepository;

    //1.Ministry
    @GetMapping("/forMinistry")
    public Page<Student> getStudentsForMinistry(@RequestParam int page){

        Pageable pageable = PageRequest.of(page, 10);// qaysidir page ni 10 tadan olib kelish
        Page<Student> studentPage = studentRepository.findAll(pageable);

        return studentPage;
    }

    @GetMapping("/forUniversity/{universityId}")
    public Page<Student> getStudentsForUniversity(@RequestParam int page, @PathVariable Integer universityId){
        Pageable pageable = PageRequest.of(page, 10);
        Page<Student> studentPage = studentRepository.findByGroup_Faculty_UniversityId(universityId, pageable);

        return studentPage;
    }


    //2.University
    //3.Faculty
    //4.Group
    //5.student

}
