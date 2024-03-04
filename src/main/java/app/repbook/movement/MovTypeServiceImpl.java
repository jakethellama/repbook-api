package app.repbook.movement;

import app.repbook.BadRequestException;
import app.repbook.ForbiddenException;
import app.repbook.person.Person;
import app.repbook.person.PersonDoesNotExistException;
import app.repbook.person.PersonRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.jsoup.nodes.Entities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MovTypeServiceImpl implements MovTypeService {
    private final MovTypeRepository movTypeRepository;
    private final PersonRepository personRepository;
    private final Validator validator;

    @Autowired
    public MovTypeServiceImpl(MovTypeRepository movTypeRepository, PersonRepository personRepository, Validator validator) {
        this.movTypeRepository = movTypeRepository;
        this.personRepository = personRepository;
        this.validator = validator;
    }

    @Override
    public Integer createMovType(String name, Integer user_id) {
        Optional<Person> user = personRepository.findById(user_id);
        if (user.isEmpty()) {
            throw new BadRequestException();
        }

        if (name != null) {name = Entities.escape(name.strip());}

        MovType movType = new MovType(user.get(), name);
        if (!validMovTypeEntity(movType)) {
            throw new BadRequestException();
        }

        MovType newMovType = movTypeRepository.save(movType);
        return newMovType.getId();
    }

    @Override
    public void patchMovTypeById(Integer movType_id, String name, Integer user_id) {
        Optional<MovType> movType = movTypeRepository.findById(movType_id);
        if (movType.isEmpty()) {
            throw new MovTypeDoesNotExistException();
        } else if (movType.get().getPerson() == null || !Objects.equals(movType.get().getPerson().getId(), user_id)) {
            throw new ForbiddenException();
        }

        if (name != null) {movType.get().setName(Entities.escape(name.strip()));}

        if (!validMovTypeEntity(movType.get())) {
            throw new BadRequestException();
        }

        movTypeRepository.save(movType.get());
    }

    @Override
    public void deleteMovTypeById(Integer movType_id, Integer user_id) {
        Optional<MovType> movType = movTypeRepository.findById(movType_id);
        if (movType.isEmpty()) {
            throw new MovTypeDoesNotExistException();
        } else if (movType.get().getPerson() == null || !Objects.equals(movType.get().getPerson().getId(), user_id)) {
            throw new ForbiddenException();
        }

        movTypeRepository.delete(movType.get());
    }

    @Override
    public List<MovTypeDTO> getMovTypesByUsername(String username, Integer user_id) {
        Person person = new Person(username);
        if (!validPersonEntity(person)) {
            throw new BadRequestException();
        }

        Optional<Person> targetPerson = personRepository.findByUsername(username);
        if (targetPerson.isEmpty()) {
            throw new PersonDoesNotExistException();
        } else if (!Objects.equals(targetPerson.get().getId(), user_id)) {
            throw new ForbiddenException();
        }

        List<MovType> movTypes = movTypeRepository.findAllByPerson_UsernameIsNullOrPerson_UsernameOrderByPerson_UsernameAsc(username);
        List<MovTypeDTO> movTypeDTOs = new ArrayList<>();

        for (MovType mT: movTypes) {
            movTypeDTOs.add(new MovTypeDTO(mT));
        }

        return movTypeDTOs;
    }

    private boolean validMovTypeEntity(MovType movType) {
        Set<ConstraintViolation<MovType>> violations = validator.validate(movType);
        return violations.isEmpty();
    }

    private boolean validPersonEntity(Person person) {
        Set<ConstraintViolation<Person>> violations = validator.validate(person);
        return violations.isEmpty();
    }
}
