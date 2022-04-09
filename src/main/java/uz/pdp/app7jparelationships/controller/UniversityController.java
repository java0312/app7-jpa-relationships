package uz.pdp.app7jparelationships.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.app7jparelationships.entity.Address;
import uz.pdp.app7jparelationships.entity.University;
import uz.pdp.app7jparelationships.payload.UniversityDto;
import uz.pdp.app7jparelationships.repository.AddressRepository;
import uz.pdp.app7jparelationships.repository.UniversityRepository;

import java.util.List;
import java.util.Optional;

@RestController
public class UniversityController {

    @Autowired
    UniversityRepository universityRepository;

    @Autowired
    AddressRepository addressRepository;


    /**
     * This method returns all universities
     *
     * @return list of all universities
     */
    @RequestMapping(value = "/university", method = RequestMethod.GET)
    public List<University> getUniversities() {
        return universityRepository.findAll();
    }

    /**
     * This method adds a new university
     *
     * @param universityDto
     * @return message about saved!
     */
    @RequestMapping(value = "/university", method = RequestMethod.POST)
    public String addUniversity(@RequestBody UniversityDto universityDto) {
        Address address = new Address();
        address.setCity(universityDto.getCity());
        address.setDistrict(universityDto.getDistrict());
        address.setStreet(universityDto.getStreet());

        Address savedAddress = addressRepository.save(address);

        University university = new University();
        university.setName(universityDto.getName());
        university.setAddress(savedAddress);

        universityRepository.save(university);

        return "university saved!";
    }

    /**
     * This method returns one university by its id
     *
     * @param id
     * @return one university
     */
    @RequestMapping(value = "/university/{id}", method = RequestMethod.GET)
    public University getUniversityById(@PathVariable Integer id) {
        Optional<University> optionalUniversity = universityRepository.findById(id);
        return optionalUniversity.orElseGet(University::new);
    }

    /**
     * This method deletes a university by its id
     *
     * @param id
     * @return message about deleted
     */
    @RequestMapping(value = "/university/{id}", method = RequestMethod.DELETE)
    public String deleteUniversityById(@PathVariable Integer id) {
        Optional<University> optionalUniversity = universityRepository.findById(id);
        if (optionalUniversity.isEmpty())
            return "University not found!";
        universityRepository.deleteById(id);
        return "University deleted!";
    }

    /**
     * This method edits a university by its id
     *
     * @param id
     * @param universityDto
     * @return message about edited
     */
    @RequestMapping(value = "/university/{id}", method = RequestMethod.PUT)
    public String editUniversity(@PathVariable Integer id, @RequestBody UniversityDto universityDto) {
        Optional<University> optionalUniversity = universityRepository.findById(id);
        if (optionalUniversity.isEmpty())
            return "University not found!";

        University university = optionalUniversity.get();
        university.setName(universityDto.getName());

        Address address = university.getAddress();
        address.setCity(universityDto.getCity());
        address.setDistrict(universityDto.getDistrict());
        address.setStreet(universityDto.getStreet());
        addressRepository.save(address);

        universityRepository.save(university);
        return "university edited!";
    }


}

/**
 * ###
 * GET http://localhost:8080/university
 *
 *
 * ###
 * POST http://localhost:8080/university
 * Content-Type: application/json
 *
 * {
 *   "name": "Urdu",
 *   "city": "Khorazm",
 *   "district": "Urganch",
 *   "street": "Darital"
 * }
 *
 *
 * ###
 * GET http://localhost:8080/university/1
 *
 * ###
 * DELETE http://localhost:8080/university/1
 *
 * ###
 * PUT http://localhost:8080/university/2
 * Content-Type: application/json
 *
 * {
 *   "name": "Urdu1",
 *   "city": "Khorazm1",
 *   "district": "Urganch1",
 *   "street": "Darital1"
 * }
 *
 * <> 2022-04-03T083310.200.txt
 */
