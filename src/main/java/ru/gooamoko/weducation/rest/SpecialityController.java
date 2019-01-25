package ru.gooamoko.weducation.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import ru.gooamoko.weducation.entity.Speciality;
import ru.gooamoko.weducation.repository.SpecialityRepository;

import javax.validation.Valid;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * REST контроллер для работы со специальностями
 *
 * @author Voronin Leonid
 * @since 24.01.19
 **/
@RestController
@RequestMapping("/rest/specialities")
public class SpecialityController {
    private static final Logger log = LoggerFactory.getLogger(SpecialityController.class);

    private SpecialityRepository repository;

    @GetMapping("/")
    public ResponseEntity<List<Speciality>> list() {
        try {
            List<Speciality> specialities = repository.findAll();
            return specialities == null || specialities.isEmpty() ?
                    new ResponseEntity<>(HttpStatus.NO_CONTENT) :
                    new ResponseEntity<>(specialities, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Ошибка при получении списка специальностей. " + e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Speciality> view(@PathVariable("id") final Integer id) {
        try {
            Optional<Speciality> optional = repository.findById(id);
            return optional.isPresent() ?
                    new ResponseEntity<>(optional.get(), HttpStatus.OK) :
                    new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error("Ошибка при получении специальности. " + e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Speciality> update(@PathVariable("id") final Integer id, @Valid @RequestBody Speciality newSpeciality) {
        try {
            if (newSpeciality == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            Optional<Speciality> optional = repository.findById(id);
            if (optional.isPresent()) {
                Speciality speciality = optional.get();
                speciality.updateFrom(newSpeciality);
                repository.saveAndFlush(speciality);
                return new ResponseEntity<>(speciality, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            log.error("Ошибка при обновлении специальности. " + e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/new", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Speciality> create(@Valid @RequestBody Speciality newSpeciality) {
        try {
            if (newSpeciality == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            repository.saveAndFlush(newSpeciality);
            return new ResponseEntity<>(newSpeciality, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Ошибка при создании новой специальности. " + e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Speciality> delete(@PathVariable("id") final Integer id) {
        try {
            repository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            log.error("Ошибка при удалении специальности. " + e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Autowired
    public void setRepository(SpecialityRepository repository) {
        this.repository = repository;
    }
}
