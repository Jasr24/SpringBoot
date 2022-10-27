package cl.tamilla.modelos;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "producto")  //Document para Mongo. en MySQL era Table y Entity
public class ProductoMongoModel {

    //en mongo el id es un String
    @Id
    private String id;

    private String nombre;
    private String slug;
    private String descricion;
    private Integer precio;
    private String foto;

    @DBRef  //Este es como el OneToOne pero para mongo
    private CategoriaMongoModel categoriaId;

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

    public String getDescricion() {
        return descricion;
    }

    public void setDescricion(String descricion) {
        this.descricion = descricion;
    }

    public Integer getPrecio() {
        return precio;
    }

    public void setPrecio(Integer precio) {
        this.precio = precio;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public CategoriaMongoModel getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(CategoriaMongoModel categoriaId) {
        this.categoriaId = categoriaId;
    }
}
