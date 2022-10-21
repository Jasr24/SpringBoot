package cl.tamilla.modelos;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class Usuario3Model {
    @NotEmpty(message = "está vacío")
    private String username;
    @NotEmpty(message = "está vacío")
    @Email(message = "Ingresado no es válido")
    private String correo;
    @NotEmpty(message = "está vacío")
    private String password;

    private PaisModel paisId;
    
    
    public Usuario3Model() {
    }
    
    public Usuario3Model(String username, String correo, String password, PaisModel paisId) {
        this.username = username;
        this.correo = correo;
        this.password = password;
        this.paisId = paisId;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getCorreo() {
        return correo;
    }
    public void setCorreo(String correo) {
        this.correo = correo;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public PaisModel getPaisId() {
        return paisId;
    }

    public void setPaisId(PaisModel paisId) {
        this.paisId = paisId;
    }
}
