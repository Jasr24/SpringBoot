package cl.tamilla.servicice;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

//@Qualifier // esta es una variante de primary
@Service //Con esto la podemos inyectar en otro lado
@Primary //permite que si tiene dos clases de servicio escoja este para inyectar
public class EjemploService {
    
    public String saludo(){
        return "Hola desde un service inyectado desde sprint";
    }

}
