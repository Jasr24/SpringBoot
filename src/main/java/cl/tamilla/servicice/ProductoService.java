package cl.tamilla.servicice;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import cl.tamilla.modelos.CategoriaModel;
import cl.tamilla.modelos.ProductoModel;
import cl.tamilla.repositorios.IProductoRepositorio;

@Service
@Primary
public class ProductoService {
    
    @Autowired
    private IProductoRepositorio repositorio;

    public List<ProductoModel> listar(){ //Listar con metodo Sort para ordenar
        return this.repositorio.findAll(Sort.by("id").descending()); //o al contrario
    }

    public List<ProductoModel> listar2(){
        return this.repositorio.findAll();
    }

    //Listamos por categoria, metodo personalizado desde la interfax
    public List<ProductoModel> listarPorCategorias(CategoriaModel categoria){
        return this.repositorio.findAllByCategoriaId(categoria);
    }

    public List<ProductoModel> listar_Where_In(List<CategoriaModel> categorias){
        return this.repositorio.findAllByCategoriaIdIn(categorias);
    }

    public Page<ProductoModel> listar_paginacion(Pageable pageable){ //Este reotrna un tipo Page
        return this.repositorio.findAll(pageable); 
    }

    public void guardar(ProductoModel producto){
    this.repositorio.save(producto);
    }

    public ProductoModel buscarPorId(Integer id){

        Optional<ProductoModel> optional = this.repositorio.findById(id);
        if(optional.isPresent()){
            return optional.get();
        }
        return null;
    }

    public void eliminar(Integer id){
        this.repositorio.deleteById(id);
    }
}
