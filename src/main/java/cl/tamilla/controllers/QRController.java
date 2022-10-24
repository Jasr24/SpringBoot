package cl.tamilla.controllers;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.zxing.WriterException;

import cl.tamilla.servicice.QrCodeService;

@Controller
@RequestMapping("/qr")
public class QRController {

    @Autowired
    private QrCodeService qrCodeService;
    
    @GetMapping("")
    public String home(){
        
        return "qr/home";
    }

    @GetMapping("/crear") 
    public String crear(Model model){
        String url = "https://github.com/Jasr24/";
        byte[] image = new byte[0];
        try{

            image = this.qrCodeService.crearQR(url, 250, 250);

        }catch(WriterException | IOException e){
            //podriamos generar un redirec con un setFlash diciendo que no se pudo generar
            e.printStackTrace();
        }

        //Ahora convertimos el byte array en base 64 String
        String qrcode = Base64.getEncoder().encodeToString(image);
        
        model.addAttribute("qrcode", qrcode);
		model.addAttribute("url", url);

        return "qr/crear";
    }
    
}
