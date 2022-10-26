package cl.tamilla.controllers;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cl.tamilla.modelos.CategoriaModel;
import cl.tamilla.modelos.ProductoModel;
import cl.tamilla.servicice.CategoriaService;
import cl.tamilla.servicice.ProductoService;
import cl.tamilla.utilidades.Utilidades;

@Controller
@RequestMapping("/jpa-repository")
public class JpaController {
    
    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private ProductoService productoService;

    @Value("${jose.valores.base_url_upload}")
    private String base_url_upload;

    @GetMapping("")
    public String home(Model model){
        return "jpa_repository/home";
    }


    ////////////////////////// GENERICOS //////////////////////
    @ModelAttribute
    public void setGenericos(Model model){
        model.addAttribute("base_url_upload", this.base_url_upload);
    }


    ////////////////////////// CATEGORIAS //////////////////////
    @GetMapping("/categorias")
    public String categorias(Model model){

        /*List<CategoriaModel> categorias = categoriaService.listar(); FORMA LARGA
        model.addAttribute("datos", categorias);*/

        model.addAttribute("datos", categoriaService.listar());
        return "jpa_repository/categorias";
    }

    @GetMapping("/categorias/add")
    public String categorias_add(Model model){

        CategoriaModel categoria = new CategoriaModel();

        model.addAttribute("categoria", categoria);
        return "jpa_repository/categorias_add";
    }

    @PostMapping("/categorias/add")
    public String categorias_add_post(@Valid CategoriaModel categoria, BindingResult result, 
                RedirectAttributes flash, Model model){

        if(result.hasErrors()){
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors()
                .forEach( err -> {
                    errores.put(err.getField(), "El campo ".concat(err.getField()).concat(" ").concat(err.getDefaultMessage()));
                });
            model.addAttribute("errores",errores);
            model.addAttribute("categoria", categoria);
            return "formularios/categorias_add";
        }
        String slug = Utilidades.getSlug(categoria.getNombre());
        if(this.categoriaService.buscarPorSlug(slug) == false){
            flash.addFlashAttribute("clase","danger");
            flash.addFlashAttribute("mensaje","La categoria ingresada ya existe en la base de datos");
            return "redirect:/jpa-repository/categorias/add";
        }

        categoria.setSlug(slug);
        this.categoriaService.guardar(categoria);
        flash.addFlashAttribute("clase","success");
        flash.addFlashAttribute("mensaje","Se creó el registro exitosamente");
        return "redirect:/jpa-repository/categorias/add";      

    }


    @GetMapping("/categorias/editar/{id}")
    public String categorias_editar(@PathVariable("id") Integer id ,Model model){

        CategoriaModel categoria = this.categoriaService.buscarPorId(id);

        model.addAttribute("categoria", categoria);
        return "jpa_repository/categorias_editar";
    }

    @PostMapping("/categorias/editar/{id}")
    public String categorias_editar_post(@Valid CategoriaModel categoria, BindingResult result,
                    @PathVariable("id") int id, RedirectAttributes flash, Model model){
    
        if(result.hasErrors()){
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors()
                .forEach( err -> {
                    errores.put(err.getField(), "El campo ".concat(err.getField()).concat(" ").concat(err.getDefaultMessage()));
                });
            model.addAttribute("errores",errores);
            model.addAttribute("categoria", categoria);
            return "formularios/categorias_add";
        }
        categoria.setSlug(Utilidades.getSlug(categoria.getNombre()));
        this.categoriaService.guardar(categoria);
        flash.addFlashAttribute("clase","success");
        flash.addFlashAttribute("mensaje","Se edito la categoria exitosamente");
        return "redirect:/jpa-repository/categorias/add";   
    }

    @GetMapping("/categorias/delete/{id}")
    public String categorias_delete(@PathVariable("id") Integer id, RedirectAttributes flash){

        try{
            this.categoriaService.eliminar(id);
            flash.addFlashAttribute("clase","success");
            flash.addFlashAttribute("mensaje","Se Elimino la categoria exitosamente");
            return "redirect:/jpa-repository/categorias"; 
        }catch(Exception e){
            flash.addFlashAttribute("clase","danger");
            flash.addFlashAttribute("mensaje","No se pudo eliminar el registro. Intentalo mas tarde");
            return "redirect:/jpa-repository/categorias"; 
        }
    }


    ////////////////////////// PRODUCTOS //////////////////////
    @GetMapping("/productos")
    public String productos(Model model){

        model.addAttribute("datos", productoService.listar());
        return "jpa_repository/productos";
    }

    @GetMapping("/productos/add")
    public String productos_add(Model model){

        ProductoModel producto = new ProductoModel();
        model.addAttribute("producto", producto);

        /*List<CategoriaModel> categorias = categoriaService.listar(); //Forma larga
        model.addAttribute("categorias", categorias);*/

        model.addAttribute("categorias", this.categoriaService.listar());
        return "jpa_repository/productos_add";
    }

    @PostMapping("/productos/add")
    public String productos_add_post(@Valid ProductoModel producto, BindingResult result, 
                            RedirectAttributes flash, Model model, @RequestParam("archivoImagen") MultipartFile multiPart){ //elRequesParam es para cargar la imagen
        
        if(result.hasErrors()){
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors()
                .forEach( err -> {
                    errores.put(err.getField(), "El campo ".concat(err.getField()).concat(" ").concat(err.getDefaultMessage()));
                });
            model.addAttribute("errores",errores);
            model.addAttribute("producto", producto);
            return "jpa-repository/productos/add";
        }

        if(multiPart.isEmpty()){
            flash.addFlashAttribute("clase","danger");
            flash.addFlashAttribute("mensaje","El archivo para la foto es obligatorio, debe ser JPG | JPEG | PNG");
            return "redirect:/jpa-repository/productos/add";
        }

        if(!multiPart.isEmpty()){
            String nombreImagen = Utilidades.guardarArchivo(multiPart, "D:/Autodidacta/1._Cursos_Udemy/4._Spring_Boot/SpringBoot/uploads/");
            if(nombreImagen == "no"){
                flash.addFlashAttribute("clase","danger");
                flash.addFlashAttribute("mensaje","El archivo para la foto no es válido, debe ser JPG | JPEG | PNG");
                return "redirect:/jpa-repository/productos/add";
            }

            if(nombreImagen != null){ //Lo hacemos asi de sencillo por que no estamos conectados a una base de datos. pero en general esto va mas aya.
                producto.setFoto(nombreImagen);
            }
        }

        producto.setSlug(Utilidades.getSlug(producto.getNombre()));
        this.productoService.guardar(producto);
        flash.addFlashAttribute("clase","success");
        flash.addFlashAttribute("mensaje","Se creo el registro exitoxamente");
        return "redirect:/jpa-repository/productos/add";
    }

    @GetMapping("/productos/editar/{id}")
    public String productos_editar(@PathVariable("id") Integer id, Model model) {
        
        ProductoModel producto = this.productoService.buscarPorId(id);

        model.addAttribute("categorias", this.categoriaService.listar());
        //producto.setCategoriaId(producto.getCategoriaId()); en caso de error descomentamos esto.
        model.addAttribute("producto", producto);
        return "jpa_repository/productos_editar";
    }
    
    @PostMapping("/productos/editar/{id}")
    public String productos_editar_post(@Valid ProductoModel producto, BindingResult result,
                    @PathVariable("id") Integer id, RedirectAttributes flash, Model model, @RequestParam("archivoImagen") MultipartFile multiPart){
    
        if(result.hasErrors()){
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors()
                .forEach( err -> {
                    errores.put(err.getField(), "El campo ".concat(err.getField()).concat(" ").concat(err.getDefaultMessage()));
                });
            model.addAttribute("errores",errores);
            model.addAttribute("producto", producto);
            return "formularios/productos_add";
        }

        if(!multiPart.isEmpty()){
            String nombreImagen = Utilidades.guardarArchivo(multiPart, "D:/Autodidacta/1._Cursos_Udemy/4._Spring_Boot/SpringBoot/uploads/");
            if(nombreImagen == "no"){
                flash.addFlashAttribute("clase","danger");
                flash.addFlashAttribute("mensaje","El archivo para la foto no es válido, debe ser JPG | JPEG | PNG");
                return "redirect:/jpa-repository/productos/editar/"+id;
            }

            if(nombreImagen != null){ //Lo hacemos asi de sencillo por que no estamos conectados a una base de datos. pero en general esto va mas aya.
                producto.setFoto(nombreImagen);
            }
        }

        producto.setSlug(Utilidades.getSlug(producto.getNombre()));
        this.productoService.guardar(producto);
        flash.addFlashAttribute("clase","success");
        flash.addFlashAttribute("mensaje","Se edito el registro exitoxamente");
        return "redirect:/jpa-repository/productos/editar/"+id;
    }

    @GetMapping("/productos/delete/{id}")
    public String productos_delete(@PathVariable("id") Integer id, RedirectAttributes flash){

        /* ESTA ES UNA MANERA PERO NO ELIMINA LA FORO
        try{ 
            this.productoService.eliminar(id);
            flash.addFlashAttribute("clase","success");
            flash.addFlashAttribute("mensaje","Se Elimino el producto exitosamente");
            return "redirect:/jpa-repository/productos"; 
        }catch(Exception e){
            flash.addFlashAttribute("clase","danger");
            flash.addFlashAttribute("mensaje","No se pudo eliminar el producto. Intentalo mas tarde");
            return "redirect:/jpa-repository/productos"; 
        }*/

        //CON LO SIGUIENTE ELIMINAMOS EL REGISTRO Y LA FOTO DE LA BASE DE DATOS Y/O DE LA CAERPETA
        ProductoModel producto = this.productoService.buscarPorId(id);
        File myObj = new File("D:/Autodidacta/1._Cursos_Udemy/4._Spring_Boot/SpringBoot/uploads/"+producto.getFoto());

        if(myObj.delete()){ //Aqui eliminamos el myObjet.. pero solo si elimina pasa lo siguiente
            this.productoService.eliminar(id);
            flash.addFlashAttribute("clase","success");
            flash.addFlashAttribute("mensaje","Se Elimino el producto exitosamente");
        } else {
            flash.addFlashAttribute("clase","danger");
            flash.addFlashAttribute("mensaje","No se pudo eliminar el producto. Intentalo mas tarde");
        }
        return "redirect:/jpa-repository/productos"; 
    }
     
}
