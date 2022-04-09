package uz.pdp.app7jparelationships.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.app7jparelationships.entity.Faculty;
import uz.pdp.app7jparelationships.entity.University;
import uz.pdp.app7jparelationships.payload.FacultyDto;
import uz.pdp.app7jparelationships.repository.FacultyRepository;
import uz.pdp.app7jparelationships.repository.UniversityRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/faculty")
public class FacultyController {

    @Autowired
    FacultyRepository facultyRepository;

    @Autowired
    UniversityRepository universityRepository;

    //__Create
    @PostMapping
    public String addFaculty(@RequestBody FacultyDto facultyDto){
        //Getting information in FacultyDto
        String facultyName = facultyDto.getName();
        Integer universityId = facultyDto.getUniversityId();

        //If this faculty exists at this university, then stop the work and return the error message
        boolean exists = facultyRepository.existsByNameAndUniversityId(facultyName, universityId);
        if (exists)
            return "faculty already exists in this university";

        //if this university exists, then get it.
        Optional<University> optionalUniversity = universityRepository.findById(universityId);
        if (optionalUniversity.isEmpty())
            return "university not found!";
        University university = optionalUniversity.get();

        //if faculty and university exists, then give information and save Faculty
        Faculty faculty = new Faculty();
        faculty.setName(facultyName);
        faculty.setUniversity(university);
        facultyRepository.save(faculty);
        return "faculty saved successfully!";
    }

    //__Read <for university>
    @GetMapping("/byUniversityId/{universityId}")
    public List<Faculty> getFacultiesByUniversityId(@PathVariable Integer universityId){
        List<Faculty> facultiesOfOneUniversity = facultyRepository.findAllByUniversity(universityId);
        return facultiesOfOneUniversity;
    }

    //__Read <for Ministry>
    @GetMapping
    public List<Faculty> getAllFaculties(){
        return facultyRepository.findAll();
    }

    //__Read <fot faculty>
    @GetMapping("/id")
    public Faculty getFacultyById(@PathVariable Integer id){
        Optional<Faculty> optionalFaculty = facultyRepository.findById(id);
        return optionalFaculty.orElseGet(Faculty::new);
    }

    //__Update
    @PutMapping("/id")
    public String editFaculty(@PathVariable Integer id, @RequestBody FacultyDto facultyDto){

        String name = facultyDto.getName();
        Integer uId = facultyDto.getUniversityId();

        boolean existsByNameAndUniversityId = facultyRepository.existsByNameAndUniversityId(name, uId);
        if (existsByNameAndUniversityId)
            return "this faculty already exists!";

        Optional<Faculty> optionalFaculty = facultyRepository.findById(id);
        if (optionalFaculty.isEmpty()) {
            return "faculty not found!";
        }
        Faculty faculty = optionalFaculty.get();

        Optional<University> optionalUniversity = universityRepository.findById(uId);
        if(optionalUniversity.isEmpty()) {
            return "University not found!";
        }
        University university = optionalUniversity.get();

        faculty.setName(name);
        faculty.setUniversity(university);
        facultyRepository.save(faculty);
        return "faculty edited!";
    }

    //__Delete
    @DeleteMapping("/id")
    public String deleteFaculty(@PathVariable Integer id){
        Optional<Faculty> optionalFaculty = facultyRepository.findById(id);
        if (optionalFaculty.isEmpty())
            return "faculty not found!";

        facultyRepository.deleteById(id);
        return "faculty deleted!";
    }

}
