package JHeras.ProgramacionNCapasNoviembre2025.Service;

import JHeras.ProgramacionNCapasNoviembre2025.DAO.IMunicipioJPARepository;
import JHeras.ProgramacionNCapasNoviembre2025.JPA.Result;
import JHeras.ProgramacionNCapasNoviembre2025.JPA.Municipio;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MunicipioService {
    
    @Autowired
    private IMunicipioJPARepository municipioJPARepository;
    
     public Result getById(Integer idEstado) {
        Result result = new Result();
        try {
            List<Municipio> municipios = (List<Municipio>) municipioJPARepository.findByEstado_idEstado(idEstado);

            result.Objects = new ArrayList<>();

            if (municipios != null) {
                for (Municipio municipio : municipios) {
                    result.Objects.add(municipio);
                    result.Correct = true;
                }                
            }else{
                result.Correct=false;
                result.ErrorMessage="No hay datos";            
            }                    
        } catch (Exception ex) {
            result.Correct = false;
            result.ErrorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;

    }
    
    

}
