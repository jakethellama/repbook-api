package app.repbook.movement;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SsetRepository extends JpaRepository<Sset, Integer> {

//    @Override
//    default List<Sset> findAll() {
//        return findAll(Sort.by(Sort.Direction.ASC, "id"));
//    }
}
