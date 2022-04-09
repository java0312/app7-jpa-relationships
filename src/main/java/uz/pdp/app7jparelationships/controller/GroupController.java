package uz.pdp.app7jparelationships.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.app7jparelationships.entity.Faculty;
import uz.pdp.app7jparelationships.entity.Group;
import uz.pdp.app7jparelationships.payload.GroupDto;
import uz.pdp.app7jparelationships.repository.FacultyRepository;
import uz.pdp.app7jparelationships.repository.GroupRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/group")
public class GroupController {


    @Autowired
    GroupRepository groupRepository;

    @Autowired
    FacultyRepository facultyRepository;

    //__Read <for ministry>
    @GetMapping
    public List<Group> getAllGroups() {
        return groupRepository.findAll();
    }

    //__Read <for university>
    @GetMapping("/byUniversityId/{universityId}")
    public List<Group> getGroupsByUniversityId(@PathVariable Integer universityId){
        return groupRepository.findAllByFaculty_UniversityId(universityId);
    }

    //__Create
    @PostMapping
    public String addGroup(@RequestBody GroupDto groupDto){
        Optional<Faculty> optionalFaculty = facultyRepository.findById(groupDto.getFacultyId());
        if (optionalFaculty.isEmpty())
            return "faculty not found!";
        Faculty faculty = optionalFaculty.get();

        Group group = new Group();
        group.setName(groupDto.getName());
        group.setFaculty(faculty);

        return "group added!";
    }

    //__Update
    @PutMapping("/id")
    public String editGroup(@PathVariable Integer id, @RequestBody GroupDto groupDto){
        boolean exists = groupRepository.existsByFacultyId(groupDto.getFacultyId());
        if (exists)
            return "group already exists!";

        Optional<Faculty> optionalFaculty = facultyRepository.findById(groupDto.getFacultyId());
        if (optionalFaculty.isEmpty())
            return "faculty not found!";
        Faculty faculty = optionalFaculty.get();

        Optional<Group> optionalGroup = groupRepository.findById(id);
        if (optionalGroup.isEmpty())
            return "group not found!";
        Group group = optionalGroup.get();

        group.setName(groupDto.getName());
        group.setFaculty(faculty);
        groupRepository.save(group);
        return "group edited!";
    }

    //__Delete
    @DeleteMapping("/id")
    public String deleteGroup(@PathVariable Integer id){
        Optional<Group> optionalGroup = groupRepository.findById(id);
        if (optionalGroup.isEmpty())
            return "group not found!";
        groupRepository.deleteById(id);
        return "group deleted!";
    }

}
