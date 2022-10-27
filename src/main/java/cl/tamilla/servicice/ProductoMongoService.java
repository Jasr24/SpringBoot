package cl.tamilla.servicice;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import cl.tamilla.modelos.ProductoMongoModel;
import cl.tamilla.repositorios.IProductoMongoRepository;

@Service
@Primary
public class ProductoMongoService {

    @Autowired
    private IProductoMongoRepository repositorio;

    public List<ProductoMongoModel> listar(){
        return this.repositorio.findAll(Sort.by("id").descending());
    }

    public void guardar(ProductoMongoModel producto){
		this.repositorio.save(producto);
	}

	public ProductoMongoModel buscarPorId(String id){
		Optional<ProductoMongoModel> optional = this.repositorio.findById(id);
		if(optional.isPresent()){
			return optional.get();
		}
		return null;
	}

	public void eliminar(String id){
		this.repositorio.deleteById(id);
	}
    
}
