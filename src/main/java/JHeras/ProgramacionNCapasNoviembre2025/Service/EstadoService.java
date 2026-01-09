package JHeras.ProgramacionNCapasNoviembre2025.Service;

import JHeras.ProgramacionNCapasNoviembre2025.DAO.IEstadoJPARepository;
import JHeras.ProgramacionNCapasNoviembre2025.JPA.Estado;
import JHeras.ProgramacionNCapasNoviembre2025.JPA.Result;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstadoService {

    @Autowired
    private IEstadoJPARepository estadoJPARepository;

    public Result getById(Integer idPais) {
        Result result = new Result();
        try {
            List<Estado> estados = (List<Estado>) estadoJPARepository.findByPais_idPais(idPais);

            result.Objects = new ArrayList<>();

            if (estados != null) {
                for (Estado estado : estados) {
                    result.Objects.add(estado);
                }
                result.Correct = true;
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
