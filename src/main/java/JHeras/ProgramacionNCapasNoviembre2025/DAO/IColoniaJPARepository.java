package JHeras.ProgramacionNCapasNoviembre2025.DAO;

import JHeras.ProgramacionNCapasNoviembre2025.JPA.Colonia;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;


public interface IColoniaJPARepository extends JpaRepository<Colonia, Integer>{

     public List<Colonia> findByMunicipio_idMunicipio(Integer idMunicipio);
}
