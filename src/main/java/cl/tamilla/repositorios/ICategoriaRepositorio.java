package cl.tamilla.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.tamilla.modelos.CategoriaModel;

public interface ICategoriaRepositorio extends JpaRepository<CategoriaModel,Integer>{

    public boolean existsBySlug(String slug); //Esta estructura hay que respetarla.. si quiere sabe si algo existe debe empezar por existsBy seguido del nombre del campo.

}
