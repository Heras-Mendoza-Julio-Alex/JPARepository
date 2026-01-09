package JHeras.ProgramacionNCapasNoviembre2025.Service;

import JHeras.ProgramacionNCapasNoviembre2025.DAO.IDireccionJPARepository;
import JHeras.ProgramacionNCapasNoviembre2025.JPA.Direccion;
import JHeras.ProgramacionNCapasNoviembre2025.JPA.Result;
import JHeras.ProgramacionNCapasNoviembre2025.JPA.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DireccionService {

    @Autowired
    private IDireccionJPARepository direccionJPARepository;

    public Result getById(Integer idDireccion) {
        Result result = new Result();
        try {
            Direccion direccion = direccionJPARepository.findById(idDireccion).orElse(null);
            if (direccion != null) {
                result.object = direccion;
                result.Correct = true;
            } else {
                result.Correct = false;
                result.ErrorMessage = "no se encontro un usuario con ese id";
            }
        } catch (Exception ex) {
            result.Correct = false;
            result.ErrorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;

    }

    public Result addDireccion(Direccion direccion, Integer idUsuario) {
        Result result = new Result();
        try {
            direccion.Usuario = new Usuario();
            direccion.Usuario.setIdUsuario(idUsuario);
            direccionJPARepository.save(direccion);
            result.Correct = true;
        } catch (Exception ex) {
            result.Correct = false;
            result.ErrorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;

    }

    public Result edit(Direccion direccion, Integer idDireccion, Integer idUsuario) {
        Result result = new Result();
        try {
            Direccion direcionDB = direccionJPARepository.findById(idDireccion).orElse(null);
            if (direcionDB != null) {

                direccion.Usuario=new Usuario();
                
                direccion.Usuario.setIdUsuario(idUsuario);

                direccionJPARepository.save(direccion);
                result.Correct = true;
            } else {
                result.ErrorMessage = "Direccion no encontrada";
                result.Correct = false;
            }
        } catch (Exception ex) {
            result.Correct = false;
            result.ErrorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }

    public Result deleteDireccion(Integer idDireccion) {
        Result result = new Result();
        try {
            Direccion direccion = direccionJPARepository.findById(idDireccion).orElse(null);
            if (direccion != null) {
                direccionJPARepository.deleteById(idDireccion);
                result.Correct = true;
            } else {
                result.Correct = false;
                result.ErrorMessage = "no se encontro un usuario con ese id";
            }
        } catch (Exception ex) {
            result.Correct = false;
            result.ErrorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }

}
