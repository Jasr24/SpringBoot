package cl.tamilla.controllers;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cl.tamilla.servicice.EmailService;

@Controller
@RequestMapping("/email")
public class EmailController {
    
    @Autowired
    private EmailService emailService;

    @GetMapping("")
    public String home(Model model){

        return "email/home";
    }

    @GetMapping("/send")
    public String send(Model model, RedirectAttributes flash) throws AddressException, MessagingException{

        String mensaje = "Hola este es un mensaje desde SpringBoot El segundo para ser presiso <hr/> <strong>Mensaje en negrilla</strong>";

        this.emailService.sendMail("josex.1995@hotmail.com", "Mensaje desde Spring", mensaje);
        flash.addFlashAttribute("clase","success");
        flash.addFlashAttribute("mensaje","Se envio el E-mail");
        return "redirect:/email";
    }
}
