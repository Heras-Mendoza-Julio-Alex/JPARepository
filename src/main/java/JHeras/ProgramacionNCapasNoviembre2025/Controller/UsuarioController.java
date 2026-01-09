package JHeras.ProgramacionNCapasNoviembre2025.Controller;

import JHeras.ProgramacionNCapasNoviembre2025.JPA.Direccion;
import JHeras.ProgramacionNCapasNoviembre2025.JPA.ErrorCarga;
import JHeras.ProgramacionNCapasNoviembre2025.JPA.Pais;
import JHeras.ProgramacionNCapasNoviembre2025.JPA.Result;
import JHeras.ProgramacionNCapasNoviembre2025.JPA.Rol;
import JHeras.ProgramacionNCapasNoviembre2025.JPA.Usuario;
import JHeras.ProgramacionNCapasNoviembre2025.Service.ColoniaService;
import JHeras.ProgramacionNCapasNoviembre2025.Service.DireccionService;
import JHeras.ProgramacionNCapasNoviembre2025.Service.EstadoService;
import JHeras.ProgramacionNCapasNoviembre2025.Service.MunicipioService;
import JHeras.ProgramacionNCapasNoviembre2025.Service.PaisService;
import JHeras.ProgramacionNCapasNoviembre2025.Service.RolService;
import JHeras.ProgramacionNCapasNoviembre2025.Service.UsuarioService;
import JHeras.ProgramacionNCapasNoviembre2025.Service.ValidationService;
import jakarta.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.validation.Valid;
import java.util.Optional;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@Controller
@RequestMapping("usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PaisService PaisService;

    @Autowired
    private RolService RolService;

    @Autowired
    private EstadoService EstadoService;

    @Autowired
    private MunicipioService MunicipioService;

    @Autowired
    private ColoniaService ColoniaService;

    @Autowired
    private DireccionService DireccionService;

    @Autowired
    private ValidationService validatorService;

    @GetMapping
    public String GetAll(Model model) {
        Result usuarios = usuarioService.getAll();
        Result roles = RolService.getAll();
        model.addAttribute("Usuarios", usuarios.Objects);
        model.addAttribute("usuarioBusqueda", new Usuario());
        model.addAttribute("Roles", roles.Objects);
        return "Usuario";

    }

    @GetMapping("detail/{IdUsuario}")
    public String Detail(@PathVariable("IdUsuario") int IdUsuario, Model model) {
        Result result = usuarioService.getById(IdUsuario);
        Result Presult = PaisService.getAll();
        Result roles = RolService.getAll();
        model.addAttribute("Paises", Presult.Objects);
        model.addAttribute("Roles", roles.Objects);
        model.addAttribute("usuario", result.object);

        return "UsuarioDetail";
    }

    @GetMapping("form")
    public String Form(Model model) {

        Usuario usuario = new Usuario();
        usuario.Direcciones = new ArrayList<>();
        usuario.Direcciones.add(new Direccion());
        Result Presult = PaisService.getAll();
        Result roles = RolService.getAll();
        model.addAttribute("Paises", Presult.Objects);
        model.addAttribute("Roles", roles.Objects);
        model.addAttribute("Usuario", usuario);
        return "FormUsu";
    }

    @PostMapping("/add")
    public String addUsuario(@Valid @ModelAttribute("Usuario") Usuario usuario, BindingResult bindingResult,
            @RequestParam("imagenFile") MultipartFile imagenFile, Model model) throws IOException {

        if (bindingResult.hasErrors()) {
            model.addAttribute("Usuario", usuario);
            return "FormUsu";
        }

        if (imagenFile != null && !imagenFile.isEmpty()) {
            String extencion = imagenFile.getOriginalFilename().split("\\.")[1];
            if (extencion.equals("png") || extencion.equals("jpg") || extencion.equals("jpeg")) {
                byte[] bytes = imagenFile.getBytes();
                String base64Image = Base64.getEncoder().encodeToString(bytes);
                usuario.setImagen(base64Image);
            }
        }
        usuario.setEstatus(1);
        Result result;

        result = usuarioService.addUsuario(usuario);

        model.addAttribute("result", result);

        return "redirect:/usuario";
    }

    @PostMapping("/editar")
    public String updateUsuario(
            //            @Valid
            @ModelAttribute("Usuario") Usuario usuario,
            BindingResult bindingResult,
            Model model) {
//if (bindingResult.hasErrors()) {
//            model.addAttribute("Usuario", usuario);
//            return "FormUsu";
//        }
        int idUsuario = usuario.getIdUsuario();

        Result result = usuarioService.edit(idUsuario, usuario);

        model.addAttribute("result", result);

        return "redirect:/usuario/detail/" + idUsuario;
    }

    @GetMapping("getEstadosByPais/{idPais}")
    @ResponseBody
    public Result EstadosByPais(@PathVariable("idPais") int idPais) {
        Result resultEstados = EstadoService.getById(idPais);
        return resultEstados;

    }

    @GetMapping("getMunicipioByEstado/{idEstado}")
    @ResponseBody
    public Result MunicipioByPais(@PathVariable("idEstado") int idEstado) {
        Result resultMunicipios = MunicipioService.getById(idEstado);
        return resultMunicipios;
    }

    @GetMapping("getColoniaByMunicipio/{idMunicipio}")
    @ResponseBody
    public Result ColoniaByMunicipio(@PathVariable("idMunicipio") int idMunicipio) {
        Result resultMunicipios = ColoniaService.getById(idMunicipio);
        return resultMunicipios;
    }

    @GetMapping("delete/{IdUsuario}")
    public String Delete(@PathVariable("IdUsuario") int IdUsuario, RedirectAttributes redirectAttributes) {
        Result resultDelete = usuarioService.deleteUsuario(IdUsuario);

        if (resultDelete.Correct) {
            resultDelete.object = "El usuario " + IdUsuario + " se elimino de forma correcta";
        } else {
            resultDelete.object = "No fue posible eliminar";
        }

        redirectAttributes.addFlashAttribute("resultDelete", resultDelete);
        return "redirect:/usuario";
    }

    @PostMapping("/updateImagen")
    public String updateImagen(@RequestParam("idUsuario") int idUsuario,
            @RequestParam("imagenFile") MultipartFile imagenFile,
            RedirectAttributes redirectAttributes,
            Model model) throws IOException {

        String imagenBase64 = Base64.getEncoder().encodeToString(imagenFile.getBytes());

        Result result = usuarioService.imagenUpdate(idUsuario, imagenBase64);

        return "redirect:/usuario/detail/" + idUsuario;
    }

    @PostMapping("addDireccion/{IdUsuario}")
    public String addDireccion(@RequestBody Direccion direccion, @PathVariable("IdUsuario") int IdUsuario,
            Model model) {

        Result result;
        if (direccion.getIdDireccion() == 0) {
            result = DireccionService.addDireccion(direccion, IdUsuario);
        } else {
            int iddireccion = direccion.getIdDireccion();
            result = DireccionService.edit(direccion, iddireccion, IdUsuario);
        }

        model.addAttribute("result", result);

        return "redirect:/usuario/detail/" + IdUsuario;
    }

    @GetMapping("deleteDireccion/{idUsuario}/{idDireccion}")
    public String DeleteDireccion(
            @PathVariable("idUsuario") int idUsuario,
            @PathVariable("idDireccion") int idDireccion,
            RedirectAttributes redirectAttributes) {

        Result resultDelete = DireccionService.deleteDireccion(idDireccion);

        if (resultDelete.Correct) {
            resultDelete.object = "La direccion: " + idDireccion + " se elimino de forma correcta";
        } else {
            resultDelete.object = "No fue posible eliminar";
        }

        redirectAttributes.addFlashAttribute("resultDelete", resultDelete);
        return "redirect:/usuario/detail/" + idUsuario;
    }

    @PostMapping("/search")
    public String BuscarUsuarios(@ModelAttribute("UsuariosBusqueda") Usuario usuario, Model model) {

        Result roles = RolService.getAll();

        Result result = usuarioService.Busqueda(usuario);

        model.addAttribute("usuarioBusqueda", new Usuario());

        model.addAttribute("Roles", roles.object);

        model.addAttribute("Usuarios", result.Objects);
        return "Usuario";
    }

    @GetMapping("CargaMasiva")
    public String CargaMasiva() {
        return "CargaMasiva";
    }

    @PostMapping("CargaMasiva")
    public String CargaMasiva(@ModelAttribute MultipartFile archivo, Model model, HttpSession session) throws IOException {
        String extencion = archivo.getOriginalFilename().split("\\.")[1];

        String path = System.getProperty("user.dir");
        String pathArchivo = "src\\main\\resources\\archivos";
        String fecha = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

        String rutaabsoluta = path + "/" + pathArchivo + "/" + fecha + archivo.getOriginalFilename();

        archivo.transferTo(new File(rutaabsoluta));

        List<Usuario> usuarios = new ArrayList<>();

        if (extencion.equals("txt")) {
            usuarios = LecturaArchivo(new File(rutaabsoluta));
            session.setAttribute("archivoCargaMasiva", rutaabsoluta);

        } else {

            usuarios = LecturaArchivoExcel(new File(rutaabsoluta));
            session.setAttribute("archivoCargaMasiva", rutaabsoluta);

        }

        List<ErrorCarga> errores = ValidarDatos(usuarios);

        if (errores != null || errores.isEmpty()) {;
            model.addAttribute("listaErrores", errores);
        } else {
            model.addAttribute("listaErrores", errores);

        }

        return "CargaMasiva";

    }

    public List<Usuario> LecturaArchivoExcel(File archivo) {
        List<Usuario> usuarios = new ArrayList<>();

        try (XSSFWorkbook workbook = new XSSFWorkbook(archivo)) {
            XSSFSheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                Usuario usuario = new Usuario();
                usuario.setNombre(row.getCell(0).toString());
                usuario.setApellidoPaterno(row.getCell(1).toString());
                usuario.setApellidoMaterno(row.getCell(2).toString());
                usuario.setTelefono(row.getCell(3).toString());
                SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
                String fecha = row.getCell(4).toString();
                Date fechaN = formato.parse(fecha);

                usuario.setFechanacimiento(fechaN);

                usuario.setUsername(row.getCell(5).toString());
                usuario.setEmail(row.getCell(6).toString());
                usuario.setSexo(row.getCell(7).toString());
                usuario.setCelular(row.getCell(8).toString());

                usuario.setCurp(row.getCell(9).toString());
                usuario.setPassword(row.getCell(10).toString());

                usuario.Rol = new Rol();

                int idr = Integer.parseInt(row.getCell(11).toString());

                usuario.Rol.setIdRol(idr);
                
                usuario.setEstatus(Integer.parseInt(row.getCell(12).toString()));
                usuario.setImagen(row.getCell(13).toString());

                usuarios.add(usuario);
            }
        } catch (Exception ex) {
            System.out.println("error: " + ex);
            return usuarios;
        }

        return usuarios;

    }

    public List<Usuario> LecturaArchivo(File archivo) {

        List<Usuario> usuarios = new ArrayList<>();
        try (InputStream inputStream = new FileInputStream(archivo); BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
            bufferedReader.readLine();
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                String[] datos = line.split("\\|");

                Usuario usuario = new Usuario();
                usuario.setNombre(datos[0]);
                usuario.setApellidoPaterno(datos[1]);
                usuario.setApellidoMaterno(datos[2]);
                usuario.setTelefono(datos[3]);

                SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
                java.util.Date fecha = formato.parse(datos[4]);

                usuario.setFechanacimiento(fecha);

                usuario.setUsername(datos[5]);
                usuario.setEmail(datos[6]);
                usuario.setSexo(datos[7]);
                usuario.setCelular(datos[8]);
                usuario.setCurp(datos[9]);
                usuario.setPassword(datos[10]);

                usuario.Rol = new Rol();
                usuario.Rol.setIdRol(Integer.parseInt(datos[11]));

                usuario.setEstatus(Integer.parseInt(datos[12]));
                usuario.setImagen(datos[13]);

                usuarios.add(usuario);
            }

        } catch (Exception ex) {
            return usuarios;
        }

        return usuarios;
    }

    public List<ErrorCarga> ValidarDatos(List<Usuario> usuarios) {

        List<ErrorCarga> erroresCarga = new ArrayList<>();
        int LineaError = 0;

        for (Usuario usuario : usuarios) {
            List<ObjectError> errors = new ArrayList<>();
            LineaError++;

            BindingResult bindingResult = validatorService.validateObjects(usuario);
            if (bindingResult.hasErrors()) {
                errors.addAll(bindingResult.getAllErrors());
            }

            if (usuario.Rol != null) {
                BindingResult bindingRol = validatorService.validateObjects(usuario.Rol);
                if (bindingRol.hasErrors()) {
                    errors.addAll(bindingRol.getAllErrors());
                }
            }

            for (ObjectError error : errors) {
                FieldError fieldError = (FieldError) error;
                ErrorCarga errorCarga = new ErrorCarga();
                errorCarga.Linea = LineaError;
                errorCarga.Campo = fieldError.getField();
                errorCarga.Descripcion = fieldError.getDefaultMessage();
                erroresCarga.add(errorCarga);
            }
        }
        return erroresCarga;
    }

    @GetMapping("/CargaMasiva/procesar")
    public String ProcesarArchivo(HttpSession sesion, RedirectAttributes redirectAttributes) {
        String path = sesion.getAttribute("archivoCargaMasiva").toString();

        File archivo = new File(path);

        String extension = archivo.getName().substring(archivo.getName().lastIndexOf('.') + 1);;

        List<Usuario> usuarios = new ArrayList<>();
        if (extension.equals("txt")) {
            usuarios = LecturaArchivo(archivo);

        } else {
            usuarios = LecturaArchivoExcel(archivo);
        }

        Result result = usuarioService.AddCargaMasiva(usuarios);
        if (result.Correct) {
            result.object = "Se realizo el registro";
        } else {
            result.object = "No se realizo la operación";
        }

        redirectAttributes.addFlashAttribute("result", result);

        return "redirect:/usuario/CargaMasiva";
        //sesion.removeAttribute("archivoCargaMasiva");
    }

}
