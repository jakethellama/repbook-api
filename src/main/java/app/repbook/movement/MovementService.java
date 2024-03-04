package app.repbook.movement;


public interface MovementService {
    MovementDTO createMovement(String movType, Integer workout_id, Integer user_id);
    MovementDTO patchMovementInfoById(Integer movement_id, String movType, String notes, Integer restAfter, Integer user_id);
    void deleteMovementById(Integer movement_id, Integer user_id);
}
