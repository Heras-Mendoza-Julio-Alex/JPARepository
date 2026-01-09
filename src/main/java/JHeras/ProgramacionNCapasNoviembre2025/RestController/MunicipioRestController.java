package JHeras.ProgramacionNCapasNoviembre2025.RestController;

import JHeras.ProgramacionNCapasNoviembre2025.JPA.Result;
import JHeras.ProgramacionNCapasNoviembre2025.Service.MunicipioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/municipio")
@Tag(name = "Municipio", description = "Controlador de operaciones del Municipio")
public class MunicipioRestController {

    @Autowired
    private MunicipioService municipioService;

    
    @GetMapping("/getMunicipioByEstado/{IdEstado}")
    @Operation(summary = "Obtener Municipios según el id de Estado", description = "Obtener los Municipios que exiten en la base de datos según el id del Estado")
    @ApiResponse(responseCode = "200", description = "Se recuperaron los Municipios de manera correcta")
    @ApiResponse(responseCode = "500", description = "Algo salio mal al realizar la operacion")
    @ApiResponse(responseCode = "404", description = "No se encontraron municipios")
    public ResponseEntity getById(@PathVariable("IdEstado") int IdEstado) {
        try {
            Result estado = municipioService.getById(IdEstado);

            return ResponseEntity.ok(estado);

        } catch (Exception e) {
            return ResponseEntity.status(500).body("");
        }
    }

}
