package JHeras.ProgramacionNCapasNoviembre2025.RestController;

import JHeras.ProgramacionNCapasNoviembre2025.JPA.Pais;
import JHeras.ProgramacionNCapasNoviembre2025.JPA.Result;
import JHeras.ProgramacionNCapasNoviembre2025.Service.PaisService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/pais")
@Tag(name = "Pais", description = "Controlador de operaciones del Pais")
public class PaisRestController {

    @Autowired
    private PaisService paisService;

    @GetMapping
    @Operation(summary = "Obtener paises", description = "Obtener los paises que exiten en la base de datos")
    @ApiResponse(responseCode = "200", description = "Se recuperaron los paises de manera correcta")
    @ApiResponse(responseCode = "500", description = "Algo salio mal al realizar la operacion")
    public ResponseEntity GetallPais() {
        try {
            Result pais = paisService.getAll();
            return ResponseEntity.ok(pais);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("");
        }
    }

}
