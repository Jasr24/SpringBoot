package cl.tamilla.modelos;

public class ProductosRestModel {

    private Integer id;
    private String nombre;
    private String slug;
    private String descripcion;
    private Integer precio;
    private String foto;

    private CategoriaRestModel categoriaId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

    public CategoriaRestModel getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(CategoriaRestModel categoriaId) {
        this.categoriaId = categoriaId;
    }

    
}
