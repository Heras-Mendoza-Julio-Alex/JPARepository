package JHeras.ProgramacionNCapasNoviembre2025.JPA;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "USUARIO")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDUSUARIO")
    private int IdUsuario;

    @Column(name = "NOMBRE", nullable = false)
    @NotEmpty(message = "El campo es requerido")
    @NotNull(message = "El campo es requerido")
    @Pattern(regexp = "[a-zA-Z]+", message = "Ingresa solo letras")
    private String Nombre;

    @NotEmpty(message = "El campo es requerido")
    @NotNull(message = "El campo es requerido")
    @Pattern(regexp = "[a-zA-Z]+", message = "Ingresa solo letras")
    @Column(name = "APELLIDOPATERNO", nullable = false)
    private String ApellidoPaterno;

    @NotEmpty(message = "El campo es requerido")
    @NotNull(message = "El campo es requerido")
    @Pattern(regexp = "[a-zA-Z]+", message = "Ingresa solo letras")
    @Column(name = "APELLIDOMATERNO")
    private String ApellidoMaterno;

    @NotEmpty(message = "El campo es requerido")
    @NotNull(message = "El campo es requerido")
    @Pattern(regexp = "^\\d{10}$", message = "Ingresa un numero valido")
    @Column(name = "TELEFONO", nullable = false)
    private String Telefono;

    @Column(name = "FECHANACIMIENTO", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "El campo es requerido")
    private Date fechanacimiento;

    @NotEmpty(message = "El campo es requerido")
    @NotNull(message = "El campo es requerido")
    @Column(name = "USERNAME", nullable = false)
    private String Username;

    @NotEmpty(message = "El campo es requerido")
    @NotNull(message = "El campo es requerido")
    @Column(name = "EMAIL", nullable = false)
    private String Email;

    @NotEmpty(message = "El campo es requerido")
    @NotNull(message = "El campo es requerido")
    @Column(name = "SEXO", nullable = false)
    private String Sexo;

    @NotEmpty(message = "El campo es requerido")
    @NotNull(message = "El campo es requerido")
    @Pattern(regexp = "^\\d{10}$", message = "Ingresa un numero valido")
    @Column(name = "CELULAR")
    private String Celular;

    
    @NotEmpty(message = "El campo es requerido")
    @NotNull(message = "El campo es requerido")
    @Column(name = "CURP")
    private String Curp;

    @NotEmpty(message = "El campo es requerido")
    @NotNull(message = "El campo es requerido")
    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "ESTATUS")
    private int estatus;

    @Column(name = "IMAGEN")
    private String Imagen;

    @ManyToOne
    @JoinColumn(name = "IDROL")
    @JsonIgnore
    public Rol Rol;
    
    @JsonIgnore
    @OneToMany(mappedBy = "Usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<Direccion> Direcciones = new ArrayList<>();

    public int getIdUsuario() {
        return IdUsuario;
    }

    public void setIdUsuario(int IdUsuario) {
        this.IdUsuario = IdUsuario;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getApellidoPaterno() {
        return ApellidoPaterno;
    }

    public void setApellidoPaterno(String ApellidoPaterno) {
        this.ApellidoPaterno = ApellidoPaterno;
    }

    public String getApellidoMaterno() {
        return ApellidoMaterno;
    }

    public void setApellidoMaterno(String ApellidoMaterno) {
        this.ApellidoMaterno = ApellidoMaterno;
    }

    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String Telefono) {
        this.Telefono = Telefono;
    }

    public Date getFechanacimiento() {
        return fechanacimiento;
    }

    public void setFechanacimiento(Date fechanacimiento) {
        this.fechanacimiento = fechanacimiento;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String Username) {
        this.Username = Username;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getSexo() {
        return Sexo;
    }

    public void setSexo(String Sexo) {
        this.Sexo = Sexo;
    }

    public String getCelular() {
        return Celular;
    }

    public void setCelular(String Celular) {
        this.Celular = Celular;
    }

    public String getCurp() {
        return Curp;
    }

    public void setCurp(String Curp) {
        this.Curp = Curp;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Rol getRol() {
        return Rol;
    }

    public void setRol(Rol Rol) {
        this.Rol = Rol;
    }

    public List<Direccion> getDirecciones() {
        return Direcciones;
    }

    public void setDirecciones(List<Direccion> Direcciones) {
        this.Direcciones = Direcciones;
    }
    public int getEstatus() {
        return estatus;
    }

    public void setEstatus(int estatus) {
        this.estatus = estatus;
    }

    public String getImagen() {
        return Imagen;
    }

    public void setImagen(String Imagen) {
        this.Imagen = Imagen;
    }

}
