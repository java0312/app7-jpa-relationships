package uz.pdp.app7jparelationships.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.app7jparelationships.entity.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

    Page<Student> findByGroup_Faculty_UniversityId(Integer group_faculty_university_id, Pageable pageable);

}
