package gregoire.kevin.location_crud.repository;
import gregoire.kevin.location_crud.model.Plane;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaneRepository extends JpaRepository<Plane, Long> {
    Plane findByModel(String username);
}