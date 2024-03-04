package app.repbook.person;

public interface PersonService {
    PersonDTO createPerson(String username, String password);
    PersonDTO getPersonIdByLogin(String username, String password);
}
