package uz.pdp.app7jparelationships.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.pdp.app7jparelationships.entity.Group;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group,Integer> {

    //JPA query
    List<Group> findAllByFaculty_UniversityId(Integer faculty_university_id);

    @Query("select gr from groups gr where gr.faculty.university.id=:universityId")
    List<Group> getGroupsByUniversityId(Integer universityId);

    //Native query
    @Query(value = "select *\n" +
            "from groups g\n" +
            "         join faculty f on f.id = g.faculty_id\n" +
            "         join university u on u.id = f.university_id\n" +
            "where university_id=:universityId", nativeQuery = true)
    List<Group> getGroupsByUniversityIdNative(Integer universityId);

    boolean existsByFacultyId(Integer faculty_id);

}
