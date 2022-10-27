package cl.tamilla.controllers;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cl.tamilla.modelos.CategoriaMongoModel;
import cl.tamilla.modelos.ProductoMongoModel;
import cl.tamilla.servicice.CategoriaMongoService;
import cl.tamilla.servicice.ProductoMongoService;
import cl.tamilla.utilidades.Utilidades;



@Controller
@RequestMapping("/jpa-mongodb")
public class JpaMongoController {
    
    
    @Autowired
    private CategoriaMongoService categoriaMongoService;

    @Autowired
    private ProductoMongoService productoMongoService;
    
    @Value("${jose.valores.base_url_upload}")
    private String base_url_upload;

    @Value("${jose.valores.ruta_upload}")
    private String ruta_upload;
    
    @GetMapping("")
    public String home() {
        
        return "jpa_mongodb/home";
    }

    //////////////////////////  CATEGORIAS //////////////////////////
    @GetMapping("/categorias")
    public String categorias(Model model){
        model.addAttribute("datos", this.categoriaMongoService.listar());
        return "jpa_mongodb/categorias";
    }

    @GetMapping("/categorias/add")
    public String categorias_add(Model model){
        
        //CategoriaMongoModel categoria = new CategoriaMongoModel();
        model.addAttribute("categoria", new CategoriaMongoModel());
        return "jpa_mongodb/categorias_add";
    }

    @PostMapping("/categorias/add")
    public String categorias_add_post(@Valid CategoriaMongoModel categoria, BindingResult result, RedirectAttributes flash, Model model){
        
        if(result.hasErrors()){
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors()
                .forEach( err -> {
                    errores.put(err.getField(), "El campo ".concat(err.getField()).concat(" ").concat(err.getDefaultMessage()));
                });
            model.addAttribute("errores",errores);
            model.addAttribute("categoria", categoria);
            return "jpa_mongodb/categoria_add";
        }

        String slug = Utilidades.getSlug(categoria.getNombre());

        if(this.categoriaMongoService.buscarPorSlug(slug) == false){
            flash.addFlashAttribute("clase","danger");
            flash.addFlashAttribute("mensaje","La categoria ingresada ya existe en la base de datos");
            return "redirect:/jpa-mongodb/categorias/add";
        }

        categoria.setSlug(slug);
        this.categoriaMongoService.guardar(categoria);
        flash.addFlashAttribute("clase","success");
        flash.addFlashAttribute("mensaje","Se creó el registro exitosamente");
        return "redirect:/jpa-mongodb/categorias/add"; 
    }


    @GetMapping("/categorias/editar/{id}")
    public String categorias_editar(@PathVariable("id") String id, Model model){
        CategoriaMongoModel categoria = this.categoriaMongoService.buscarPorId(id);
        model.addAttribute("categoria", categoria);
        return "jpa_mongodb/categoria_editar";
    }

    @PostMapping("/categorias/editar/{id}")
    public String categorias_editar_post(@Valid CategoriaMongoModel categoria, BindingResult result, 
                                @PathVariable("id") String id, RedirectAttributes flash, Model model){
        
        if(result.hasErrors()){
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors()
                .forEach( err -> {
                    errores.put(err.getField(), "El campo ".concat(err.getField()).concat(" ").concat(err.getDefaultMessage()));
                });
            model.addAttribute("errores",errores);
            model.addAttribute("categoria", categoria);
            return "jpa_mongodb/categoria_editar";
        }

        categoria.setSlug(Utilidades.getSlug(categoria.getNombre()));
        this.categoriaMongoService.guardar(categoria);
        flash.addFlashAttribute("clase","success");
        flash.addFlashAttribute("mensaje","Se modifico el registro exitosamente");
        return "redirect:/jpa-mongodb/categorias/editar/"+id; 
    }

    @GetMapping("/categorias/delete/{id}")
    public String categorias_delete(@PathVariable("id") String id, RedirectAttributes flash) {

        try{
            this.categoriaMongoService.eliminar(id);
            flash.addFlashAttribute("clase","success");
            flash.addFlashAttribute("mensaje","Se elimino la categoria exitosamente");
            return "redirect:/jpa-mongodb/categorias";
        }catch(Exception e){
            flash.addFlashAttribute("clase","danger");
            flash.addFlashAttribute("mensaje","No se puede eliminar el registro, intenlo mas tarde");
            return "redirect:/jpa-mongodb/categorias";
        }
    }    

    //////////////////////////  PRODUCTOS //////////////////////////
    @GetMapping("/productos")
    public String productos(Model model){
        model.addAttribute("datos", this.productoMongoService.listar());
        return "jpa_mongodb/productos";
    }

    @GetMapping("/productos/add")
    public String productos_add(Model model){
        
        model.addAttribute("producto", new ProductoMongoModel());
        model.addAttribute("categorias", this.categoriaMongoService.listar());
        return "jpa_mongodb/productos_add";
    }

    @PostMapping("/productos/add")
    public String productos_add_post(@Valid ProductoMongoModel producto, BindingResult result, 
                RedirectAttributes flash, Model model, @RequestParam("archivoImagen") MultipartFile multiPart){
        
        if(result.hasErrors()){
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors()
                .forEach( err -> {
                    errores.put(err.getField(), "El campo ".concat(err.getField()).concat(" ").concat(err.getDefaultMessage()));
                });
            model.addAttribute("errores",errores);
            model.addAttribute("producto", producto);
            return "jpa_mongodb/productos_add";
        }

        if(multiPart.isEmpty()){
            flash.addFlashAttribute("clase","danger");
            flash.addFlashAttribute("mensaje","El archivo para la foto es obligatorio, debe ser JPG | JPEG | PNG");
            return "redirect:/jpa-mongodb/productos/add";
        }

        if(!multiPart.isEmpty()){
            String nombreImagen = Utilidades.guardarArchivo(multiPart, "D:/Autodidacta/1._Cursos_Udemy/4._Spring_Boot/SpringBoot/uploads/");
            if(nombreImagen == "no"){
                flash.addFlashAttribute("clase","danger");
                flash.addFlashAttribute("mensaje","El archivo para la foto no es válido, debe ser JPG | JPEG | PNG");
                return "redirect:/jpa-mongodb/productos/add";
            }

            if(nombreImagen != null){ //Lo hacemos asi de sencillo por que no estamos conectados a una base de datos. pero en general esto va mas aya.
                producto.setFoto(nombreImagen);
            }
        }
        
        producto.setSlug(Utilidades.getSlug(producto.getNombre()));
        this.productoMongoService.guardar(producto);
        flash.addFlashAttribute("clase","success");
        flash.addFlashAttribute("mensaje","Se creo el registro exitoxamente");
        return "redirect:/jpa-mongodb/productos/add";
    }


    @GetMapping("/productos/editar/{id}")
    public String productos_editar(@PathVariable("id") String id, Model model){
        ProductoMongoModel producto = this.productoMongoService.buscarPorId(id);
        producto.setCategoriaId(producto.getCategoriaId());//Para que pueda hacer la carga de los select.
        model.addAttribute("producto", producto);
        model.addAttribute("categorias", this.categoriaMongoService.listar()); 
        return "jpa_mongodb/producto_editar";
    }

    @PostMapping("/productos/editar/{id}")
    public String categorias_editar_post(@Valid ProductoMongoModel producto, BindingResult result, 
                                @PathVariable("id") String id, RedirectAttributes flash, Model model, @RequestParam("archivoImagen") MultipartFile multiPart){
        
        if(result.hasErrors()){
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors()
                .forEach( err -> {
                    errores.put(err.getField(), "El campo ".concat(err.getField()).concat(" ").concat(err.getDefaultMessage()));
                });
            model.addAttribute("errores",errores);
            model.addAttribute("producto", producto);
            return "jpa-mongodb/productos_add";
        }
                            
        if(!multiPart.isEmpty()){
            String nombreImagen = Utilidades.guardarArchivo(multiPart, "D:/Autodidacta/1._Cursos_Udemy/4._Spring_Boot/SpringBoot/uploads/");
            if(nombreImagen == "no"){
                flash.addFlashAttribute("clase","danger");
                flash.addFlashAttribute("mensaje","El archivo para la foto no es válido, debe ser JPG | JPEG | PNG");
                return "redirect:/jpa-mongodb/productos/editar/"+id;
            }
                            
            if(nombreImagen != null){ //Lo hacemos asi de sencillo por que no estamos conectados a una base de datos. pero en general esto va mas aya.
                producto.setFoto(nombreImagen);
            }
        }
                            
        producto.setSlug(Utilidades.getSlug(producto.getNombre()));
        this.productoMongoService.guardar(producto);
        flash.addFlashAttribute("clase","success");
        flash.addFlashAttribute("mensaje","Se edito el registro exitoxamente");
        return "redirect:/jpa-mongodb/productos/editar/"+id;
    }
    
    @GetMapping("/productos/delete/{id}")
    public String productos_delete(@PathVariable("id") String id, RedirectAttributes flash) {

        ProductoMongoModel producto = this.productoMongoService.buscarPorId(id);
        File myObj = new File(this.ruta_upload+producto.getFoto()); //Esto para poder eliminar la foto de la carpeta y / 0 base de datos
        if(myObj.delete()){
            this.productoMongoService.eliminar(id);
            flash.addFlashAttribute("clase","success");
            flash.addFlashAttribute("mensaje","Se elimino el producto exitosamente");
        }else {
            flash.addFlashAttribute("clase","danger");
            flash.addFlashAttribute("mensaje","No se puede eliminar el registro, intenlo mas tarde");
        }
        return "redirect:/jpa-mongodb/productos";
    }        

     ////////////////////////// GENERICOS //////////////////////
    @ModelAttribute
    public void setGenericos(Model model){
        model.addAttribute("base_url_upload", this.base_url_upload);
    }
    
}
