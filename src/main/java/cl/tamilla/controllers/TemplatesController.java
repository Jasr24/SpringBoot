package cl.tamilla.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/templates")
public class TemplatesController {


    
    @GetMapping("")
    public String home(Model model){

        String nombre ="Jose Saavedra";
        String pias ="Colombia";
        model.addAttribute("nombre", nombre);
        model.addAttribute("pais", pias);
        return "templates/home";
    }

    @GetMapping("/atributos")
    public String atributos(Model model){
        Integer num1 = 12;
        Integer num2 = 13;
        Integer cifra = 12345;
        Date fecha = new Date();
        List<String> paises = new ArrayList<>();
        paises.add("chile");
        paises.add("colombia");
        paises.add("peru");
        paises.add("nicaragua");
        paises.add("reino unido");
        model.addAttribute("num1", num1);
        model.addAttribute("num2", num2);
        model.addAttribute("cifra", cifra);
        model.addAttribute("fecha", fecha);
        model.addAttribute("paises", paises);
        return "templates/atributos";
    }

    @GetMapping("/estaticos")
    public String estaticos(Model model){        
        
        return "templates/estaticos";
    }

    @GetMapping("/estaticos2")
    public String estaticos2(Model model){
        return "templates/estaticos2";
    }

    @GetMapping("/ajax")
	public String ajax(Model model) 
	{
		
		return "templates/ajax";
	}

    @GetMapping("/peticion")
	public String peticion(Model model, @RequestParam("valor") String valor) 
	{
		model.addAttribute("valor", valor);
		return "templates/peticion";
	}
	@GetMapping("/fancybox")
	public String fancybox(Model model) 
	{
		 
		return "templates/fancybox";
	}
	
}
