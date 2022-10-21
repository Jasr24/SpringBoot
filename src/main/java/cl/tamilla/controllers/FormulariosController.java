package cl.tamilla.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cl.tamilla.modelos.InteresesModel;
import cl.tamilla.modelos.PaisModel;
import cl.tamilla.modelos.Usuario2Model;
import cl.tamilla.modelos.Usuario3Model;
import cl.tamilla.modelos.UsuarioCheckboxModel;
import cl.tamilla.modelos.UsuarioModel;
import cl.tamilla.modelos.UsuarioUploadModel;
import cl.tamilla.utilidades.Utilidades;

@Controller
@RequestMapping("/formularios")
public class FormulariosController {

    @Value("jose.valores.base_url_upload")
    private String base_url_upload;
    
    @GetMapping("")
    public String home(){
        return "formularios/home";
    }

    //Formulario Simple
    @GetMapping("/simple")
    public String simple(){
        return "formularios/simple";
    }

    @PostMapping("/simple")
    @ResponseBody
    public String simple_post(@RequestParam(name = "username") String username, @RequestParam(name ="correo") String correo, @RequestParam(name = "password") String password){
        return "Username= " + username + " <br/>Correo=" + correo + "<br/>Password=" + password;
    }

    //Formularios de Objetos
    @GetMapping("/objeto")
    public String objeto(){
        return "formularios/objeto";
    }

    @PostMapping("/objeto") //para hacerlo de esta manera se debe de crear una entidad con los datos que va a recibir
    @ResponseBody
    public String objeto_post(UsuarioModel usuarioModel){ //No es nesesario usar el @RequestParam ya que lo estamos haciendo con una entidad- un modelo - una clase
        return "<h1>Con un Objeto</h1>Username= " + usuarioModel.getUsername() + " <br/>Correo=" + usuarioModel.getCorreo()+ "<br/>Password=" + usuarioModel.getPassword();
    }
    
    //Formulario de Objetos version 2
    @GetMapping("/objeto2")
    public String objeto2(Model model){
        model.addAttribute("usuario", new UsuarioModel());
        return "formularios/objeto2";
    }

    @PostMapping("/objeto2") //para hacerlo de esta manera se debe de crear una entidad con los datos que va a recibir
    @ResponseBody
    public String objeto2_post(UsuarioModel usuarioModel){ //No es nesesario usar el @RequestParam ya que lo estamos haciendo con una entidad- un modelo - una clase
        return "<h1>Con un Objeto version 2</h1>Username= " + usuarioModel.getUsername() + " <br/>Correo=" + usuarioModel.getCorreo()+ "<br/>Password=" + usuarioModel.getPassword();
    }

    //Formulario con Validaciones
    //Para que la validacion funcione tenemos que tener el formulario de la vista envuelta en un th:objeto
    @GetMapping("/validaciones")
    public String validaciones(Model model){ //Los campos los validamos en la propia clase modelo o entity oooo.
        model.addAttribute("usuario", new Usuario2Model()); //Creamos un nuevo modelo para no dañar los ejemplos anteriores
        return "formularios/validaciones";
    }

    @PostMapping("/validaciones") //para hacerlo de esta manera se debe de crear una entidad con los datos que va a recibir
    public String validaciones_post(@Valid Usuario2Model usuario, BindingResult result, Model model){ //para poder aplicar las validaciones se tiene que tener esta anotacion @Valid y siempre justo despues tiene que venir un BindingResult SIEMPRE "VALIDACIONES".
    
        if(result.hasErrors()){
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors()
                .forEach( err -> {
                    errores.put(err.getField(), "El campo ".concat(err.getField()).concat(" ").concat(err.getDefaultMessage()));
                });
            model.addAttribute("errores",errores);
            model.addAttribute("usuario", usuario);
            return "formularios/validaciones";
        }
        model.addAttribute("usuario", usuario);
        return "formularios/validaciones_result";
    }


    //Formulario de Select Dinamico con @ModelAtributos de una base de datos
    //Creamos dos nuevas entidades para este ejemplo Usuario3Model y PaisModel
    @GetMapping("/select-dinamico")
    public String select_dinamico(Model model){ 
        //eN ESTA VISTA USAMOS ALGO QUE VA DLE METODO setGenericosParaTodasLasVistas
        model.addAttribute("usuario", new Usuario3Model()); 
        return "formularios/select_dinamico";
    }

    @PostMapping ("/select-dinamico")
    public String select_dinamico_post(@Valid Usuario3Model usuario, BindingResult result, Model model){ //para poder aplicar las validaciones se tiene que tener esta anotacion @Valid y siempre justo despues tiene que venir un BindingResult SIEMPRE "VALIDACIONES"
        if(result.hasErrors()){
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors()
                .forEach( err -> {
                    errores.put(err.getField(), "El campo ".concat(err.getField()).concat(" ").concat(err.getDefaultMessage()));
                });
            model.addAttribute("errores",errores);
            model.addAttribute("usuario", usuario);
            return "formularios/select_dinamico";
        }
        model.addAttribute("usuario", usuario);
        return "formularios/select_dinamico_result";
    }


    //Formulario CheckBox
    //Creamos dos entidades nuevas para este ejemplo la entidad InteresesModel y la entidad UsuarioCheckbox
    @GetMapping("/checkbox")
    public String checkbox(Model model){ 
        //Los intereses se los pasamos por el metodo generico @ModelAttribute setGenericosParaTodasLasVistas.
        model.addAttribute("usuario", new UsuarioCheckboxModel()); 
        return "formularios/checkbox";
    }

    @PostMapping ("/checkbox")
    public String checkbox_post(@Valid UsuarioCheckboxModel usuario, BindingResult result, Model model){ //para poder aplicar las validaciones se tiene que tener esta anotacion @Valid y siempre justo despues tiene que venir un BindingResult SIEMPRE "VALIDACIONES"
        if(result.hasErrors()){
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors()
                .forEach( err -> {
                    errores.put(err.getField(), "El campo ".concat(err.getField()).concat(" ").concat(err.getDefaultMessage()));
                });
            model.addAttribute("errores",errores);
            model.addAttribute("usuario", usuario);
            return "formularios/checkbox";
        }
        model.addAttribute("usuario", usuario);
        return "formularios/checkbox_resultado";
    }


    //Formulario Mesajes flash
    @GetMapping("/flash")
    public String flash(Model model){ 
        model.addAttribute("usuario", new UsuarioModel());
        return "formularios/flash";
    }

    @PostMapping("/flash") //Para los mensajes de tipo flash se pone el RedirectAttribute //Redirect
    public String flash_post(UsuarioModel usuario, RedirectAttributes flash){
        flash.addFlashAttribute("clase","danger");
        flash.addFlashAttribute("mensaje","Ejemplo de mensaje flah con ñandu");
        return "redirect:/formularios/flash-respuesta";
    }

    @GetMapping("/flash-respuesta")
    public String flash_respuesta(){ 
        return "formularios/flash_respuesta";
    }

    //Formulario uploads de archivos
    //Para este ejemplo se creo la clase Configuration.java.. se modifico el applicatin.properties y se creo la clase Utilidades.java y una clase modelo UsuarioUploadModel
    @GetMapping("/upload")
    public String upload (Model model){
        model.addAttribute("usuario", new UsuarioUploadModel());
        return "formularios/upload";
    }

    @PostMapping("/upload")
    public String upload_post(@Valid UsuarioUploadModel usuario, BindingResult result, Model model, 
                    @RequestParam("archivoImagen") MultipartFile multiPart, RedirectAttributes flash){ //Para recibir la foto o imagen se pone el @RequestParam("archivoImagen") MultipartFile
        
        if(result.hasErrors()){
            Map<String, String> errores = new HashMap<>();
            result.getFieldErrors()
                .forEach( err -> {
                    errores.put(err.getField(), "El campo ".concat(err.getField()).concat(" ").concat(err.getDefaultMessage()));
                });
            model.addAttribute("errores",errores);
            model.addAttribute("usuario", usuario);
            return "formularios/upload";
        }

        if(multiPart.isEmpty()){
            flash.addFlashAttribute("clase","danger");
            flash.addFlashAttribute("mensaje","El archivo para la foto es obligatorio, debe ser JPG | JPEG | PNG");
            return "redirect:/formularios/upload";
        }

        if(!multiPart.isEmpty()){
            String nombreImagen = Utilidades.guardarArchivo(multiPart, "D:/Autodidacta/1._Cursos_Udemy/4._Spring_Boot/SpringBoot/uploads/");
            if(nombreImagen == "no"){
                flash.addFlashAttribute("clase","danger");
                flash.addFlashAttribute("mensaje","El archivo para la foto no es válido, debe ser JPG | JPEG | PNG");
                return "redirect:/formularios/upload";
            }

            if(nombreImagen != null){ //Lo hacemos asi de sencillo por que no estamos conectados a una base de datos. pero en general esto va mas aya.
                usuario.setFoto(nombreImagen);
            }
        }
        model.addAttribute("usuario", usuario);
        return "formularios/upload_respuesta";
    }






    ////////////////////////////////////////// TODA LA APLICACION.
    //////////////////////////////////////////
    //Esto sera generico para toda la aplicacion,, estara presente en todas las vista con las siguiente anotacion
    @ModelAttribute
    public void setGenericosParaTodasLasVistas(Model model){
        List<PaisModel> paises = new ArrayList<>();
        paises.add(new PaisModel(1,"Colombia"));
        paises.add(new PaisModel(2,"Venezuela"));
        paises.add(new PaisModel(3,"Peru"));
        paises.add(new PaisModel(4,"Chile"));
        paises.add(new PaisModel(5,"Ecuador"));
        paises.add(new PaisModel(6,"España"));
        paises.add(new PaisModel(7,"Panama"));
        model.addAttribute("paises", paises);

        List<InteresesModel> intereses = new ArrayList<>();
        intereses.add(new InteresesModel(1, "Vaginas"));
        intereses.add(new InteresesModel(1, "Musica"));
        intereses.add(new InteresesModel(1, "Politica"));
        intereses.add(new InteresesModel(1, "Peliculas"));
        intereses.add(new InteresesModel(1, "Animes"));
    
        model.addAttribute("intereses", intereses);

        /*//Aqui lo trabajando sin inyectarla con el @Value("xd")
        model.addAttribute("base_url_upload", "http://localhost/Autodidacta/1._Cursos_Udemy/4._Spring_Boot/SpringBoot/uploads/");*/

        //Aqui si la inyectamos como atributo
        model.addAttribute("base_url_upload", this.base_url_upload);
    }
    //////////////////////////////////////////
    //////////////////////////////////////////
}
