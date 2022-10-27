package cl.tamilla.repositorios;

import org.springframework.data.mongodb.repository.MongoRepository;

import cl.tamilla.modelos.CategoriaMongoModel;

public interface ICategoriaMongoRepository extends MongoRepository<CategoriaMongoModel,String>{
    
    public boolean existsBySlug (String slug);
}
