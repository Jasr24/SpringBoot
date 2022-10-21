package cl.tamilla.modelos;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class Usuario2Model {

    //Para poder hacer validaciones tenemos que incorporar - instalar una dependencia en el pom.xml que se llama spring validator

    @NotEmpty(message = "está vacío")
    private String username;
    @NotEmpty(message = "está vacío")
    @Email(message = "Ingresado no es válido")
    private String correo;
    @NotEmpty(message = "está vacío")
    private String password;

    public Usuario2Model() {
    }
    
    public Usuario2Model(String username, String correo, String password) {
        this.username = username;
        this.correo = correo;
        this.password = password;
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
}
