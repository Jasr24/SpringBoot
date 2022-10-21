package cl.tamilla.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import cl.tamilla.servicice.EjemploService;


@Controller
@RequestMapping("/ejemplo-service")
public class EjemploServiceController {
    
    @Autowired //Con esta anotacion aplicamos la inyeccion de dependecia
    private EjemploService ejemploService;

    @GetMapping("")
    public String home(Model model){
        
        model.addAttribute("saludo", this.ejemploService.saludo());
        return "ejemplo_service/home";
    }
}
