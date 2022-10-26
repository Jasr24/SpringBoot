package cl.tamilla.servicice;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import cl.tamilla.modelos.ProductoModel;
import cl.tamilla.repositorios.IProductoRepositorio;

@Service
@Primary
public class ProductoService {
    
    @Autowired
    private IProductoRepositorio repositorio;

    public List<ProductoModel> listar(){
        return this.repositorio.findAll();
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
