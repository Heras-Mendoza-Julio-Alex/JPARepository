package JHeras.ProgramacionNCapasNoviembre2025.Service;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

public class CustomFailureHandle implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        HttpSession session = request.getSession();
        String mensaje;

        if (exception instanceof DisabledException) {
            mensaje = "Tu cuenta esta desabilitada, comunicate con un administrador";
        } else if (exception instanceof AccountExpiredException) {
            mensaje = "Tu cuenta a expirado, comunicate con un administrador";
        } else if (exception instanceof BadCredentialsException) {
            mensaje = "Usuario o contraseña incorrectos";
        } else {
            mensaje = "Error de inicio de sesión: " + exception.getMessage();
        }

        session.setAttribute("errorMsg", mensaje);

        response.sendRedirect("login");
    }
}
