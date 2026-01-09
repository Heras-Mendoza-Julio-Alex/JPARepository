package JHeras.ProgramacionNCapasNoviembre2025.JPA;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "DIRECCION")
public class Direccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDDIRECCION")
    private int idDireccion;
    
    @NotEmpty(message = "El campo es requerido")
    @NotNull(message = "El campo es requerido")
    @Column(name = "calle")
    private String Calle;
    
    @NotEmpty(message = "El campo es requerido")
    @NotNull(message = "El campo es requerido")
    @Column(name = "numerointerior")
    private String NumeroInterior;
    
    @NotEmpty(message = "El campo es requerido")
    @NotNull(message = "El campo es requerido")
    @Column(name = "numeroexterior")
    private String NumeroExterior;
    
    @ManyToOne
    @JoinColumn(name = "idusuario_fk")
    @JsonIgnore
    public Usuario Usuario;
    
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "idcolonia_fk")
    public Colonia Colonia;

    
//
//    public Usuario getUsuario() {
//        return Usuario;
//    }
//
//    public void setUsuario(Usuario Usuario) {
//        this.Usuario = Usuario;
//    }
//
//    public Colonia getColonia() {
//        return Colonia;
//    }
//
//    public void setColonia(Colonia Colonia) {
//        this.Colonia = Colonia;
//    }

    public int getIdDireccion() {
        return idDireccion;
    }

    public void setIdDireccion(int idDireccion) {
        this.idDireccion = idDireccion;
    }

    public String getCalle() {
        return Calle;
    }

    public void setCalle(String Calle) {
        this.Calle = Calle;
    }

    public String getNumeroInterior() {
        return NumeroInterior;
    }

    public void setNumeroInterior(String NumeroInterior) {
        this.NumeroInterior = NumeroInterior;
    }

    public String getNumeroExterior() {
        return NumeroExterior;
    }

    public void setNumeroExterior(String NumeroExterior) {
        this.NumeroExterior = NumeroExterior;
    }

    public Colonia getColonia() {
        return Colonia;
    }

    public void setColonia(Colonia Colonia) {
        this.Colonia = Colonia;
    }

    
}
