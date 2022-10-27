package cl.tamilla.servicice;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import cl.tamilla.modelos.CategoriaMongoModel;
import cl.tamilla.repositorios.ICategoriaMongoRepository;

@Service
@Primary
public class CategoriaMongoService {

    @Autowired
    private ICategoriaMongoRepository repositorio;

    public List<CategoriaMongoModel> listar(){
        return this.repositorio.findAll(Sort.by("id").descending());
    }

    public void guardar(CategoriaMongoModel categoria){
        this.repositorio.save(categoria);
    }

    public CategoriaMongoModel buscarPorId(String id){
        Optional<CategoriaMongoModel> optional = repositorio.findById(id);
        if(optional.isPresent()){
            return optional.get();
        }
        return null;
    }

    public boolean buscarPorSlug(String slug){
        
        if(this.repositorio.existsBySlug(slug)){
            return false;
        }

        return true;
    }

    public void eliminar(String id) {
		this.repositorio.deleteById(id);		
	}
    
}
