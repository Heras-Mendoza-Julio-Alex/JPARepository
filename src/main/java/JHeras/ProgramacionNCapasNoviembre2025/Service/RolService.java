package JHeras.ProgramacionNCapasNoviembre2025.Service;

import JHeras.ProgramacionNCapasNoviembre2025.DAO.IRolJPARepository;
import JHeras.ProgramacionNCapasNoviembre2025.JPA.Result;
import JHeras.ProgramacionNCapasNoviembre2025.JPA.Rol;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RolService {
    
    @Autowired
    private IRolJPARepository rolJPARepository;
    
     public Result getAll() {
         Result result=new Result();
        try {
            List<Rol> roles = rolJPARepository.findAll();

             result.Objects = new ArrayList<>();

            for (Rol rol : roles) {
                result.Objects.add(rol);
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
