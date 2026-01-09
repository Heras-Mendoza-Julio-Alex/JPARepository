package JHeras.ProgramacionNCapasNoviembre2025.DAO;

import JHeras.ProgramacionNCapasNoviembre2025.JPA.Municipio;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;


public interface IMunicipioJPARepository extends JpaRepository<Municipio, Integer>{
    
    public List<Municipio> findByEstado_idEstado(Integer idEstado);    

}
