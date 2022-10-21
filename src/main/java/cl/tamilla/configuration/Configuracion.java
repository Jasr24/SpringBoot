package cl.tamilla.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class Configuracion implements WebMvcConfigurer{

    //En este metodo se le dira a Spring Boot que nesesito informar la ruta acia donde yo nesesite que apunten los recursos estaticos que boy a utilizar como las imagenes
    //Se puede hacer esa y muchas mas CLASE 43 DE UDEMY
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        
        /*@Value("${jose.valores.ruta_upload}")  //POR ALGUNA RAZON NO ME FUNCIONO ESTO SE HACE CON EL ARCHIVO APLICATION.PROPERTIES
        private String ruta_upload;*/


        //WebMvcConfigurer.super.addResourceHandlers(registry);//esto NO LO NESESITAMOS
        
        //D:\Autodidacta\13 Proyectos\ImagenesGenerales
        registry.addResourceHandler("/uploads/**")
        .addResourceLocations("file: D:/Autodidacta/1._Cursos_Udemy/4._Spring_Boot/SpringBoot/uploads/");
    }
}

