package JHeras.ProgramacionNCapasNoviembre2025.Service;

import JHeras.ProgramacionNCapasNoviembre2025.DAO.IUsuarioJPARepository;
import JHeras.ProgramacionNCapasNoviembre2025.JPA.Direccion;
import JHeras.ProgramacionNCapasNoviembre2025.JPA.Result;
import JHeras.ProgramacionNCapasNoviembre2025.JPA.Usuario;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private IUsuarioJPARepository usuarioJPARepository;

    public Result getAll() {
        Result result = new Result();
        try {
            List<Usuario> usuarios = usuarioJPARepository.findAll();
            result.Objects = new ArrayList<>();

            for (Usuario usuario : usuarios) {
                result.Objects.add(usuario);
            }

        } catch (Exception ex) {
            result.Correct = false;
            result.ErrorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        result.Correct = true;
        return result;
    }

    public Result getById(Integer idUsuario) {
        Result result = new Result();
        try {
            Usuario usuario = usuarioJPARepository.findById(idUsuario).orElse(null);
            if (usuario != null) {
                result.object = usuario;
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

    public Result addUsuario(Usuario usuario) {
        Result result = new Result();
        try {
            if (usuario.Direcciones != null && !usuario.Direcciones.isEmpty()) {
                Direccion dir = usuario.Direcciones.get(0);
                dir.Usuario = usuario;
            }
            usuarioJPARepository.save(usuario);

            result.Correct = true;
        } catch (Exception ex) {
            result.Correct = false;
            result.ErrorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;

    }

    public Result bajaLogica(Integer idUsuario, int estadoUsuario) {
        Result result = new Result();
        try {
            Usuario usuarioDB = usuarioJPARepository.findById(idUsuario).orElse(null);

            if (usuarioDB != null) {
                usuarioDB.setEstatus(estadoUsuario);
                usuarioJPARepository.save(usuarioDB);
                result.Correct = true;
            } else {
                result.ErrorMessage = "Usuario no encontrado";
                result.Correct = false;
            }
        } catch (Exception ex) {
            result.Correct = false;
            result.ErrorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }

    public Result edit(Integer idUsuario, Usuario usuario) {
        Result result = new Result();
        try {
            Usuario usuarioDB = usuarioJPARepository.findById(idUsuario).orElse(null);

            if (usuarioDB != null) {
                usuario.setIdUsuario(usuarioDB.getIdUsuario());
                usuario.setRol(usuario.getRol() == null || usuario.getRol().getIdRol() == 0 ? usuarioDB.getRol() : usuario.getRol());
                usuario.setSexo(usuario.getSexo() == null ? usuarioDB.getSexo() : usuario.getSexo());
                usuario.setPassword(usuario.getPassword() == null ? usuarioDB.getPassword() : usuario.getPassword());
                usuario.setImagen(usuario.getImagen() == null ? usuarioDB.getImagen() : usuario.getImagen());
                Integer estatusRecibido = usuario.getEstatus();

                if (estatusRecibido == null) {
                    usuario.setEstatus(usuarioDB.getEstatus());
                } else if (estatusRecibido.intValue() == 0) {
                    usuario.setEstatus(usuarioDB.getEstatus());
                }
                if (usuario.Direcciones != null && !usuario.Direcciones.isEmpty()) {
                    usuario.Direcciones.get(0).Usuario = usuario;
                } else {
                    usuario.Direcciones = usuarioDB.Direcciones;
                }
                usuarioJPARepository.save(usuario);
                result.Correct = true;
            } else {
                result.ErrorMessage = "Usuario no encontrado";
                result.Correct = false;
            }
        } catch (Exception ex) {
            result.Correct = false;
            result.ErrorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }

    public Result imagenUpdate(Integer idUsuario, String imagenCadena) {
        Result result = new Result();
        try {
            Usuario usuarioDB = usuarioJPARepository.findById(idUsuario).orElse(null);

            if (usuarioDB != null) {
                usuarioDB.setImagen(imagenCadena);
                usuarioJPARepository.save(usuarioDB);
                result.Correct = true;
            } else {
                result.ErrorMessage = "Usuario no encontrado";
                result.Correct = false;
            }
        } catch (Exception ex) {
            result.Correct = false;
            result.ErrorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;

    }

    public Result deleteUsuario(Integer idUsuario) {
        Result result = new Result();
        try {
            Usuario usuario = usuarioJPARepository.findById(idUsuario).orElse(null);
            if (usuario != null) {
                usuarioJPARepository.deleteById(idUsuario);
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

    public Result Busqueda(Usuario usuario) {
        Result result = new Result();

        try {
            String nombre = (usuario.getNombre() == null || usuario.getNombre().isEmpty() ? null : usuario.getNombre());
            String apellidoP = (usuario.getApellidoPaterno() == null || usuario.getApellidoPaterno().isEmpty() ? null : usuario.getApellidoPaterno());
            String apellidoM = (usuario.getApellidoMaterno() == null || usuario.getApellidoMaterno().isEmpty() ? null : usuario.getApellidoMaterno());

            Integer idRol = (usuario.getRol() != null && usuario.getRol().getIdRol() > 0) ? usuario.getRol().getIdRol() : null;

            List<Usuario> usuarios = usuarioJPARepository.busqueda(nombre, apellidoP, apellidoM, idRol);

            result.Objects = new ArrayList<>();

            if (usuarios != null) {
                for (Usuario u : usuarios) {
                    result.Objects.add(u);
                }
            }

        } catch (Exception ex) {
            result.Correct = false;
            result.ErrorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }

    public Result AddCargaMasiva(List<Usuario> usuarios) {
        Result result = new Result();
                
        try {
            for (Usuario usuario : usuarios) {
                if (usuario.Direcciones != null && !usuario.Direcciones.isEmpty()) {
                    Direccion dir = usuario.Direcciones.get(0);
                    dir.Usuario = usuario;
                }
                usuarioJPARepository.saveAll(usuarios);

                result.Correct = true;
            }

        } catch (Exception ex) {
            result.Correct = false;
            result.ErrorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }

}
