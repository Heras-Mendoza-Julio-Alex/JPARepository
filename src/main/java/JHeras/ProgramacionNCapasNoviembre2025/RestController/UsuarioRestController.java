package JHeras.ProgramacionNCapasNoviembre2025.RestController;

import JHeras.ProgramacionNCapasNoviembre2025.JPA.Result;
import JHeras.ProgramacionNCapasNoviembre2025.JPA.Usuario;
import JHeras.ProgramacionNCapasNoviembre2025.Service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/usuario")
@Tag(name = "Usuario", description = "Controlador de operaciones del Usuario")
public class UsuarioRestController {

    @Autowired
    private UsuarioService usuarioService;

    /*------------------------------- Usuario ----------------------------*/
 /*---------  Obtener los usuarios  ---------*/
    @GetMapping
    @Operation(summary = "Obtener usuarios", description = "Obtener los usuarios que exiten en la base de datos")
    @ApiResponse(responseCode = "200", description = "Se recuperaron los usuarios de manera correcta")
    @ApiResponse(responseCode = "500", description = "Algo salio mal al realizar la operacion")
    public ResponseEntity getAll() {
        try {
            Result usuarios = usuarioService.getAll();
            return ResponseEntity.ok(usuarios);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("");
        }

    }

    /*---------  Obtener un usuario por su ID  ---------*/
    @GetMapping("/{idUsuario}")
    @Operation(summary = "Obtener usuario por id", description = "Obtener los datos del usuario según su ID")
    @ApiResponse(responseCode = "200", description = "Se recuperaron los datos de manera correcta")
    @ApiResponse(responseCode = "500", description = "Algo salio mal al realizar la operacion")
    @ApiResponse(responseCode = "404", description = "No existe un usuario con ese id en la base de datos")
    public ResponseEntity getById(@PathVariable("idUsuario") int idUsuario) {
        try {
            Result usuario = usuarioService.getById(idUsuario);
            if (usuario.Correct) {
                return ResponseEntity.ok(usuario);
            } else {
                return ResponseEntity.status(404).body(usuario);
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("");
        }
    }

    //    /*---------  Añadir un usuario ---------*/
    @PostMapping(value = "/add", consumes = "application/json")
    public ResponseEntity addUsuario(@RequestBody Usuario usuario) {
        try {
            usuarioService.addUsuario(usuario);
            return ResponseEntity.ok(usuario);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("");
        }

    }

    /*---------  Borrar usuario ---------*/
    @Operation(summary = "Eliminar usuario", description = "Eliminar 1 usuario según el ID")
    @ApiResponse(responseCode = "200", description = "Se eliminaron los datos de manera correcta")
    @ApiResponse(responseCode = "500", description = "Algo salio mal al realizar la operacion")
    @ApiResponse(responseCode = "404", description = "No existe un usuario con ese id en la base de datos")
    @DeleteMapping("/{IdUsuario}")
    public ResponseEntity Delete(@PathVariable int IdUsuario) {
        try {
            Result result = usuarioService.deleteUsuario(IdUsuario);
            if (result.Correct) {
                return ResponseEntity.ok(result);
            } else {
                return ResponseEntity.status(404).body(result);
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("");
        }
    }

    /*---------  Estatus usuario (baja logica) ---------*/
    @Operation(summary = "Cambiar estatus el usuario", description = "Cambiar estatus del usuario según el ID")
    @ApiResponse(responseCode = "200", description = "Se actualizo el estatus de manera correcta")
    @ApiResponse(responseCode = "500", description = "Algo salio mal al realizar la operacion")
    @ApiResponse(responseCode = "204", description = "No existe un usuario con ese id en la base de datos")
    @PatchMapping("/{IdUsuario}/baja")
    public ResponseEntity bajaL(@PathVariable int IdUsuario, @RequestBody int estado) {
        try {
            Result result = usuarioService.bajaLogica(IdUsuario, estado);
            if (result.Correct) {
                return ResponseEntity.ok(result);
            } else {
                return ResponseEntity.status(404).body(result);
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("");
        }
    }

    /*---------  Editar usuario ---------*/
    @Operation(summary = "Actualizar usuario por id", description = "Actualizar los datos del usuario según su ID")
    @ApiResponse(responseCode = "200", description = "Se actualizaron los datos de manera correcta")
    @ApiResponse(responseCode = "500", description = "Algo salio mal al realizar la operacion")
    @ApiResponse(responseCode = "404", description = "No existe un usuario con ese id en la base de datos")
    @PutMapping("/{IdUsuario}")
    public ResponseEntity Update(@RequestBody Usuario usuario, @PathVariable int IdUsuario) {
        try {
            Result result = usuarioService.edit(IdUsuario, usuario);
            if (result.Correct) {
                return ResponseEntity.ok(result);
            } else {
                return ResponseEntity.status(404).body(result);
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("");
        }
    }

    /*---------  Imagen usuario (actualizacion) ---------*/
    @Operation(summary = "Actualización de la imagen", description = "Actualizar imagen  de un usuario")
    @ApiResponse(responseCode = "200", description = "Se actualizo de manera correcta")
    @ApiResponse(responseCode = "500", description = "Algo salio mal al realizar la operacion")
    @ApiResponse(responseCode = "204", description = "No existe un usuario con ese id en la base de datos")
    @RequestMapping(value = "/{IdUsuario}/imagen", method = {RequestMethod.POST, RequestMethod.PATCH})
    public ResponseEntity Imagen(@PathVariable int IdUsuario, @RequestBody String imagenBase64) {
        try {
            Result result = usuarioService.imagenUpdate(IdUsuario, imagenBase64);
            if (result.Correct) {
                return ResponseEntity.ok(result);
            } else {
                return ResponseEntity.status(204).body(result);
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("");
        }
    }

    /*---------  Busqueda usuario ---------*/
    @Operation(summary = "Busqueda de usuarios", description = "Buscar Usuarios segun el nombre y/o ap,am y rol")
    @ApiResponse(responseCode = "200", description = "Se encontraron datos")
    @ApiResponse(responseCode = "500", description = "Algo salio mal al realizar la operacion")
    @ApiResponse(responseCode = "204", description = "Se realizo la operación, pero no se encontro coincidencias")
    @PostMapping("/getAllDinamico")
    public ResponseEntity getAllD(@RequestBody Usuario usuario) {
        try {
            Result result = usuarioService.Busqueda(usuario);

            return ResponseEntity.ok(result);

        } catch (Exception e) {
            return ResponseEntity.status(500).body("");
        }
    }

//
//    /*     Prueba de add con imagen*/
//    @Operation(summary = "Añadir usuario", description = "Añadir usuario ")
//    @ApiResponse(responseCode = "200", description = "Se insertaron los datos de manera correcta")
//    @ApiResponse(responseCode = "500", description = "Algo salio mal al realizar la operacion")
//    @PostMapping(
//            value = "/add",
//            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
//    )
//    public ResponseEntity addUsuario(
//            @RequestPart("usuario") Usuario usuario,
//            @RequestPart(value = "Imagen", required = false) MultipartFile Imagen
//    ) throws IOException {
//
//        if (Imagen != null) {
//            String extencion = Imagen.getOriginalFilename().split("\\.")[1];
//            //imagen.png
//            //[imagen,png]
//            if (extencion.equals("png") || extencion.equals("jpg") || extencion.equals("jpeg")) {
//                byte[] bytes = Imagen.getBytes();
//                String base64Image = Base64.getEncoder().encodeToString(bytes);
//                usuario.setImagen(base64Image);
//
//            }
//        }
//        usuario.setEstatus(1);
//        Result result = usuarioJPADAOImplementation.add(usuario);
//        return ResponseEntity.status(result.StatusCode).body(result);
//    }
//
//
//
//
//
//
//    /*---------  Carga Masiva  ---------*/
//    @Operation(summary = "Generación de token", description = "Se recibe un archivo txt"
//            + "o xlsx, se generea un token SHA-256, se copia el archivo recibido a la carpeta"
//            + "de archivos y logs")
//    @PostMapping(value = "/cargamasiva", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public Result cargaMasiva(@RequestParam("archivo") MultipartFile archivo) {
//        Result result = cargaMasivaService.tokenArchivo(archivo);
//        return result;
//    }
//
//    @Operation(summary = "Inserción de datos en la DB", description = "Se recibe el token generado en la carga masiva"
//            + ", Se realiza una comparación de tiempo y de tipo de archivo"
//            + "para verificar antes de la inserción")
//    @PostMapping("/cargamasiva/procesar")
//    public String procesarArchivo(@RequestHeader("Authorization") String token) {
//        String tokenReal = token.replace("Bearer ", "").trim();
//        String result = cargaMasivaService.ProcesarArchivo(tokenReal);
//        return result;
//    }
}
