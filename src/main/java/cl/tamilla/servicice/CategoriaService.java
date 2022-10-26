package cl.tamilla.servicice;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import cl.tamilla.modelos.CategoriaModel;
import cl.tamilla.repositorios.ICategoriaRepositorio;

@Service
@Primary
public class CategoriaService {

    @Autowired
    ICategoriaRepositorio repositorio;

    public List<CategoriaModel> listar(){ //Listar con metodo Sort para ordenar
        return this.repositorio.findAll(Sort.by("id").descending()); //o al contrario
    }

    public List<CategoriaModel> listar2(){
        return this.repositorio.findAll();
    }

    public void guardar(CategoriaModel categoria){
        this.repositorio.save(categoria);
    }

    public boolean buscarPorSlug(String slug){
        if(this.repositorio.existsBySlug(slug)){
            return false;
        }
        return true;
    }

    public CategoriaModel buscarPorId(Integer id){

        Optional<CategoriaModel> optional = this.repositorio.findById(id);
        if(optional.isPresent()){
            return optional.get();
        }
        return null;
    }

    public void eliminar (Integer id){
        this.repositorio.deleteById(id);
    }
}
