package JHeras.ProgramacionNCapasNoviembre2025.Controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class loginController {

    @GetMapping("/login")
    public String login(
//            HttpSession session, Model model
    ) {
        
//        Object errorMsg = session.getAttribute("errorMsg");
//
//        if (errorMsg != null) {
//            model.addAttribute("errorMsg", errorMsg); 
//            session.removeAttribute("errorMsg");      
//        }

        return "login";
    }

    @GetMapping("/acceso-denegado")
    public String accesoDenegado() {
        return "errorAcceso";
    }
}
