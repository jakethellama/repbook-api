package app.repbook.movement;

import java.util.List;

public interface MovTypeService {
    Integer createMovType(String name, Integer user_id);
    void patchMovTypeById(Integer movType_id, String name, Integer user_id);
    void deleteMovTypeById(Integer movType_id, Integer user_id);
    List<MovTypeDTO> getMovTypesByUsername(String username, Integer user_id);
}
