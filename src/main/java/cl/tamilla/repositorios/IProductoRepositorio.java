package cl.tamilla.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.tamilla.modelos.CategoriaModel;
import cl.tamilla.modelos.ProductoModel;

public interface IProductoRepositorio extends JpaRepository<ProductoModel, Integer>{
    
    //Consulta personalizada. findBy - seguido del nombre del atributo por el cual se quiere hacer la busqueda personalizada
    List<ProductoModel> findAllByCategoriaId(CategoriaModel categoriaId);

    //Metodo usando WhereIn
    List<ProductoModel> findAllByCategoriaIdIn(List<CategoriaModel> categorias);
}
