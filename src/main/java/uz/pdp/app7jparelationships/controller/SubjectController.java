package uz.pdp.app7jparelationships.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.app7jparelationships.entity.Subject;
import uz.pdp.app7jparelationships.entity.University;
import uz.pdp.app7jparelationships.repository.SubjectRepository;

import java.util.List;
import java.util.Optional;

@RestController
public class SubjectController {

    @Autowired
    SubjectRepository subjectRepository;

    /**
     * This method returns all subjects
     *
     * @return list of all subjects
     */
    @RequestMapping(value = "/subject", method = RequestMethod.GET)
    public List<Subject> getAllSubjects() {
        return subjectRepository.findAll();
    }

    /**
     * This method returns one subject by its id
     *
     * @param id
     * @return one student
     */
    @RequestMapping(value = "/subject/{id}", method = RequestMethod.GET)
    public Subject getSubjectById(@PathVariable Integer id) {
        Optional<Subject> optionalSubject = subjectRepository.findById(id);
        return optionalSubject.orElseGet(Subject::new);
    }

    /**
     * This method adds a new Subject
     *
     * @param subject
     * @return message about added
     */
    @RequestMapping(value = "/subject", method = RequestMethod.POST)
    public String addSubject(@RequestBody Subject subject) {
        boolean exists = subjectRepository.existsByName(subject.getName());
        if (exists)
            return "subject already exists!";

        subjectRepository.save(subject);
        return "subject saved!";
    }

    /**
     * This method deletes a subject by its id
     *
     * @param id
     * @return message about deleted
     */
    @RequestMapping(value = "/subject/{id}", method = RequestMethod.DELETE)
    public String deleteSubjectById(@PathVariable Integer id) {
        Optional<Subject> optionalSubject = subjectRepository.findById(id);
        if (optionalSubject.isEmpty())
            return "subject not found!";
        subjectRepository.deleteById(id);
        return "subject deleted!";
    }

    /**
     * This method edits a subject by its id
     *
     * @param id
     * @param subject
     * @return message about edited
     */
    @RequestMapping(value = "/subject/{id}", method = RequestMethod.PUT)
    public String editeSubject(@PathVariable Integer id, @RequestBody Subject subject) {
        Optional<Subject> optionalSubject = subjectRepository.findById(id);
        if (optionalSubject.isEmpty())
            return "subject not found!";

        Subject editingSubject = optionalSubject.get();
        editingSubject.setName(subject.getName());
        subjectRepository.save(editingSubject);
        return "subject edited!";
    }

}
