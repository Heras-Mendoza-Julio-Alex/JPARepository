package JHeras.ProgramacionNCapasNoviembre2025.Service;

import JHeras.ProgramacionNCapasNoviembre2025.DAO.IUsuarioJPARepository;
import JHeras.ProgramacionNCapasNoviembre2025.JPA.Usuario;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsJPAService implements UserDetailsService {

    private IUsuarioJPARepository iUsuarioJPARepository;

    public UserDetailsJPAService(IUsuarioJPARepository iUsuarioJPARepository) {
        this.iUsuarioJPARepository = iUsuarioJPARepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = iUsuarioJPARepository.findByUsername(username);

        boolean deshabilidato = (usuario.getEstatus() == 0);

        boolean cuentaExpirada = (usuario.getEstatus() == 2);

        return User.withUsername(usuario.getUsername())
                .password(usuario.getPassword())
                .roles(usuario.Rol.getNombre())
                
//                .accountExpired(cuentaExpirada)
//                .disabled(deshabilidato)
//                
                .build();
    }

}
