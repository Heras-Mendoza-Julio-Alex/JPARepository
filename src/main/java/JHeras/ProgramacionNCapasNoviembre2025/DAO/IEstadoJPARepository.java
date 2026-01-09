package JHeras.ProgramacionNCapasNoviembre2025.DAO;

import JHeras.ProgramacionNCapasNoviembre2025.JPA.Estado;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;


public interface IEstadoJPARepository extends JpaRepository<Estado, Integer>{

    public List<Estado> findByPais_idPais(Integer idPais);
    
}
