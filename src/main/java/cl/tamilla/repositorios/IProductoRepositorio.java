package cl.tamilla.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.tamilla.modelos.ProductoModel;

public interface IProductoRepositorio extends JpaRepository<ProductoModel, Integer>{
    
}
