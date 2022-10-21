package cl.tamilla.servicice;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service //Con esto la podemos inyectar en otro lado
@Primary //permite que si tiene dos clases de servicio escoja este para inyectar
//@Qualifier // esta es una variante de primary
public class EjemploService {
    
    public String saludo(){
        return "Hola desde un service inyectado desde sprint";
    }

}
