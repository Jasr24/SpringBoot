package cl.tamilla.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import cl.tamilla.modelos.ProductosRestModel;
import cl.tamilla.servicice.ClienteRestService;

@Controller
@RequestMapping("/cliente-rest")
public class ClienteRestController {

    @Value("${jose.valores.base_url_upload}")
    private String base_url_upload;

    @Autowired
    private ClienteRestService clienteRestService;


    @GetMapping("")
    public String home(Model model){
        List<ProductosRestModel> datos = this.clienteRestService.listar();
        model.addAttribute("datos", datos);
        /*//Aqui lo trabajando sin inyectarla con el @Value("xd") desde el archivo .properties
        model.addAttribute("base_url_upload", "http://localhost/Autodidacta/1._Cursos_Udemy/4._Spring_Boot/SpringBoot/uploads/");*/
        model.addAttribute("base_url_upload", base_url_upload);

        return "cliente_rest/home";
    } 
}
