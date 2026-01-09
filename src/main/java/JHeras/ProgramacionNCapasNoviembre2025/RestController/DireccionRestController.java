package JHeras.ProgramacionNCapasNoviembre2025.RestController;

import JHeras.ProgramacionNCapasNoviembre2025.JPA.Direccion;
import JHeras.ProgramacionNCapasNoviembre2025.JPA.Result;
import JHeras.ProgramacionNCapasNoviembre2025.Service.DireccionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/direccion")
@Tag(name = "Direccion", description = "Controlador de operaciones de la Direccion")
public class DireccionRestController {

    @Autowired

    private DireccionService direccionService;

    @GetMapping("/{idDireccion}")
    @Operation(summary = "Obtener la direccion según su id", description = "Obtener los datos de la direccion según su ID")
    @ApiResponse(responseCode = "200", description = "Se recuperaron los datos de manera correcta")
    @ApiResponse(responseCode = "500", description = "Algo salio mal al realizar la operacion")
    @ApiResponse(responseCode = "404", description = "No existe una direccion con ese id en la base de datos")
    public ResponseEntity getById(@PathVariable("idDireccion") int idDireccion) {
        try {
            Result direccion = direccionService.getById(idDireccion);
            if (direccion.Correct) {
                return ResponseEntity.ok(direccion);
            } else {
                return ResponseEntity.status(404).body(direccion);
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("");
        }
    }

    @PutMapping("/{IdUsuario}/{idDireccion}")
    @Operation(summary = "Actualizar la direccion según su id", description = "Actualizar los datos de la direccion según su ID")
    @ApiResponse(responseCode = "200", description = "Se actualizaron los datos de manera correcta")
    @ApiResponse(responseCode = "500", description = "Algo salio mal al realizar la operacion")
    @ApiResponse(responseCode = "404", description = "No existe una direccion con ese id en la base de datos")
    public ResponseEntity Update(@PathVariable("IdUsuario") int IdUsuario, @PathVariable("idDireccion") int idDireccion, @RequestBody Direccion direccion) {
        try {
            Result result = direccionService.edit(direccion,idDireccion ,IdUsuario);
            if (result.Correct) {
                return ResponseEntity.ok(result);
            } else {
                return ResponseEntity.status(404).body(result);
            }            
        } catch (Exception e) {
            return ResponseEntity.status(500).body("");
        }   
    }
    
    @PostMapping(value = "/add/{IdUsuario}", consumes = "application/json")
    @Operation(summary = "Añadir una direccion", description = "Añadir una direccion a un usuario")
    @ApiResponse(responseCode = "200", description = "Se añadieron los datos de manera correcta")
    @ApiResponse(responseCode = "500", description = "Algo salio mal al realizar la operacion")
    public ResponseEntity añadirDireccion(@PathVariable("IdUsuario") int IdUsuario, @RequestBody Direccion direccion) {
        try {
            Result result = direccionService.addDireccion(direccion, IdUsuario);
            if (result.Correct) {
                return ResponseEntity.ok(result);
            } else {
                return ResponseEntity.status(404).body(result);
            }            
        } catch (Exception e) {
            return ResponseEntity.status(500).body("");
        }
    }

    @DeleteMapping("/{IdUsuario}/{idDireccion}")
    @Operation(summary = "Borrar una direccion según el id", description = "Borrar una direccion a un usuario")
    @ApiResponse(responseCode = "200", description = "Se Borraron los datos de manera correcta")
    @ApiResponse(responseCode = "500", description = "Algo salio mal al realizar la operacion")
    @ApiResponse(responseCode = "404", description = "No existe una direccion con ese id en la base de datos")
    public ResponseEntity Delete(@PathVariable int idDireccion) {
        try {
            Result result = direccionService.deleteDireccion(idDireccion);
            if (result.Correct) {
                return ResponseEntity.ok(result);
            } else {
                return ResponseEntity.status(404).body(result);
            }            
        } catch (Exception e) {
            return ResponseEntity.status(500).body("");
        }}
}
