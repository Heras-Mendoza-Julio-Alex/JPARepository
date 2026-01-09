package JHeras.ProgramacionNCapasNoviembre2025.DAO;

import JHeras.ProgramacionNCapasNoviembre2025.JPA.Result;
import JHeras.ProgramacionNCapasNoviembre2025.JPA.Usuario;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface IUsuarioJPARepository extends JpaRepository<Usuario, Integer>{

    @Query("SELECT u FROM Usuario u WHERE " +
           "(:nombre IS NULL OR u.Nombre LIKE %:nombre%) AND " +
           "(:apellidoPaterno IS NULL OR u.ApellidoPaterno LIKE %:apellidoPaterno%) AND " +
           "(:apellidoMaterno IS NULL OR u.ApellidoMaterno LIKE %:apellidoMaterno%) AND " +
           "(:idRol IS NULL OR u.Rol.idRol = :idRol)")
   
    List<Usuario> busqueda(@Param("nombre") String nombre,
                           @Param("apellidoPaterno") String apellidoPaterno,
                           @Param("apellidoMaterno") String apellidoMaterno,
                           @Param("idRol")Integer idRol);
    
    
}
