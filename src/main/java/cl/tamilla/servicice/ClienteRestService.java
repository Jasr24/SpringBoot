package cl.tamilla.servicice;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Primary;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import cl.tamilla.modelos.ProductosRestModel;

@Service
@Primary
public class ClienteRestService {
    
    @Autowired
    private RestTemplate clienteRest; //Se tiene que configurar primaro como un Bean en la clase principa de este proyecto.. caso contrario no funcionaria

    @Value("${jose.valores.api}") //Importada desde el archivo aplication.properties.
    private String apiHost;
    //private String apiHost = http://http://192.168.1.9:8080/api/v1/ 

    public ClienteRestService(RestTemplateBuilder builder) {
        this.clienteRest = builder.build(); //Con esta manera queda un poco mas desacoplado.
    }

    private HttpHeaders setHeaders(){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.set("Authorization", "XXXXXXXXXXXXXXXX  Este valor es el bearer.. el token que autoriza XXXXXXXXXXXXXXXXX");
        return headers;
    }

    public List<ProductosRestModel> listar(){
        HttpEntity<String> entity = new HttpEntity<>(this.setHeaders());

        ResponseEntity <List<ProductosRestModel>> response = this.clienteRest.exchange(apiHost+"productos", HttpMethod.GET, entity, 
                new ParameterizedTypeReference<List<ProductosRestModel>>(){});
        return response.getBody();
    }

    
}
