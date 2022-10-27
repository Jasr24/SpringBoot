package cl.tamilla.repositorios;

import org.springframework.data.mongodb.repository.MongoRepository;

import cl.tamilla.modelos.ProductoMongoModel;

public interface IProductoMongoRepository extends MongoRepository<ProductoMongoModel,String>{
    
}
