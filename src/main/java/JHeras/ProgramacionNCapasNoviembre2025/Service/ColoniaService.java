package JHeras.ProgramacionNCapasNoviembre2025.Service;

import JHeras.ProgramacionNCapasNoviembre2025.DAO.IColoniaJPARepository;
import JHeras.ProgramacionNCapasNoviembre2025.JPA.Colonia;
import JHeras.ProgramacionNCapasNoviembre2025.JPA.Result;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ColoniaService {

    @Autowired
    private IColoniaJPARepository coloniaJPARepository;

    public Result getById(int idMunicipio) {
        Result result = new Result();
        try {
            List<Colonia> colonias = (List<Colonia>) coloniaJPARepository.findByMunicipio_idMunicipio(idMunicipio);

            result.Objects = new ArrayList<>();

            if (colonias != null) {
                for (Colonia colonia : colonias) {
                    result.Objects.add(colonia);
                    result.Correct = true;
                }
            } else {
                result.Correct = false;
                result.ErrorMessage = "No hay datos";
            }
        } catch (Exception ex) {
            result.Correct = false;
            result.ErrorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }

}
