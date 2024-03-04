package app.repbook.movement;

import java.math.BigDecimal;

public interface SsetService {
    SsetDTO createSset(Integer movement_id, Integer user_id);
    SsetDTO patchSsetById(Integer sset_id, Integer reps, BigDecimal weight, Integer mm, Integer ss, Integer user_id);
    void deleteSsetById(Integer sset_id, Integer user_id);
}
