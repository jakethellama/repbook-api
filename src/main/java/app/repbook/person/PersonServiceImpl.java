package app.repbook.person;

import app.repbook.BadRequestException;
import app.repbook.UnauthorizedException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class PersonServiceImpl implements PersonService {
    private final PersonRepository personRepository;
    private final CredentialRepository credentialRepository;
    private final Validator validator;

    @Autowired
    public PersonServiceImpl(PersonRepository personRepository, CredentialRepository credentialRepository, Validator validator) {
        this.personRepository = personRepository;
        this.credentialRepository = credentialRepository;
        this.validator = validator;
    }

    @Override
    public PersonDTO createPerson(String username, String password) {
        Credential credential = new Credential(username, password);
        if (!validCredentialEntity(credential)) {
            throw new BadRequestException();
        }

        if (personRepository.existsByUsername(username)) {
            throw new PersonAlreadyExistsException();
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(8);

        credential.setPassword(encoder.encode(password)); // hash is length 60

        credentialRepository.save(credential);
        Person newPerson = personRepository.save(new Person(username));
        return new PersonDTO(newPerson);
    }

    @Override
    public PersonDTO getPersonIdByLogin(String username, String password) {
        Credential credential = new Credential(username, password);
        if (!validCredentialEntity(credential)) {
            throw new BadRequestException();
        }

        Optional<Credential> targetCredential = credentialRepository.findByUsername(username);
        if (targetCredential.isEmpty()) {
            throw new UnauthorizedException();
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(8);

        if (!encoder.matches(password, targetCredential.get().getPassword())) {
            throw new UnauthorizedException();
        }

        Optional<Person> targetPerson = personRepository.findByUsername(username);
        if (targetPerson.isEmpty()) {
            throw new UnauthorizedException();
        }

        return new PersonDTO(targetPerson.get());
    }

    private boolean validPersonEntity(Person person) {
        Set<ConstraintViolation<Person>> violations = validator.validate(person);
        return violations.isEmpty();
    }

    private boolean validCredentialEntity(Credential credential) {
        Set<ConstraintViolation<Credential>> violations = validator.validate(credential);
        return violations.isEmpty();
    }
}
