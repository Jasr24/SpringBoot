package cl.tamilla.modelos;

import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "categoria")  //Document para Mongo. en MySQL era Table y Entity
public class CategoriaMongoModel {
    
    //en mongo el id es un String
    @Id
    private String id;

    @NotEmpty
    private String nombre;

    private String slug;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }
}
