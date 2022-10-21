package cl.tamilla.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/") //Es como la ruta principal,, el que maneja los get, put, delete, port ....
public class HomeController {

    @Value("${jose.valores.nombre}") //Esto es una inyeccion de dependencia desde el archivo application.properties
    private String joseValoresNombre;
 
    @GetMapping("/")
    //@ResponseBody //Esto hace que todo lo que retorne el siguiente metodo, lo renderice como texto dentro de la resputesta.
    public String Home(){
        return "home/home"; //Esto siempre debe de ser una plantilla.. o mejor dicho un html
    }


    @GetMapping("/nosotros")
    @ResponseBody
    public String nosotros(){
        return "Desde nosotros";
    }


    @GetMapping("/parametros/{id}/{slug}")
    @ResponseBody
    public String paramtetros (@PathVariable("id") Long id, @PathVariable("slug") String slug){
        return "id =" + id + " | slug = " + slug;
    }    

    @GetMapping("/query-string")
    @ResponseBody                                                                                 //Asi se pasa el requestparam con el signo de interrogacion ? y asugnando los valores 
    public String  query_String(@RequestParam("id") Long id, @RequestParam("slug") String slug){ //http://192.168.0.112:8080/query-string?id=15&slug=chupelo... RequestParam recupera datos de formulario.. ejemplo el Query String
        return "id =" + id + " | slug = " + slug;
    }

    @GetMapping("/valores")
    @ResponseBody                                                                                 //Asi se pasa el requestparam con el signo de interrogacion ? y asugnando los valores 
    public String valores (){
        return  "jose.valores.nombre = " + this.joseValoresNombre;
    }

}
