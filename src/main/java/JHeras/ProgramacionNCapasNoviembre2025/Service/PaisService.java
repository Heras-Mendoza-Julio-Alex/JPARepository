package JHeras.ProgramacionNCapasNoviembre2025.Service;

import JHeras.ProgramacionNCapasNoviembre2025.DAO.IPaisJPARepository;
import JHeras.ProgramacionNCapasNoviembre2025.JPA.Pais;
import JHeras.ProgramacionNCapasNoviembre2025.JPA.Result;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaisService {
    
    @Autowired
    private IPaisJPARepository paisJPARepository;

    
     public Result getAll() {
         Result result=new Result();
        try {
            List<Pais> paises = paisJPARepository.findAll();

             result.Objects = new ArrayList<>();

            for (Pais pais : paises) {
                result.Objects.add(pais);
            }

        } catch (Exception ex) {
            result.Correct = false;
            result.ErrorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        result.Correct = true;
        return result;
    }
}
