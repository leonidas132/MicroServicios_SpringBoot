
package com.paymentcain.customer.controller;


import com.fasterxml.jackson.databind.JsonNode;
import com.paymentcain.customer.entity.Customer;
import com.paymentcain.customer.entity.CustomerProducto;
import com.paymentcain.customer.repository.CustomerRepository;
import io.netty.channel.ChannelOption;
import io.netty.channel.epoll.EpollChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

@RestController
@RequestMapping("/customer")
public class CustomerRestController {
    
    //--------------------------------------------------------//
    //  INICIO CLIENTE WEB
    //--------------------------------------------------------//
    
    private final WebClient.Builder webClientBuilder;

     public CustomerRestController(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }
     
    // SE ENCARGARA DE CONSUMIR LA API DE OTRO MICROSERVIVO
    //-----------------------------------------------------------// 
    //  FINAL CLINETE WEB
    //-----------------------------------------------------------// 
    
    //--------------------------------------------------------//
    // CONFIGUTACION CLIENTE WEB
    //--------------------------------------------------------//
      HttpClient client = HttpClient.create() //se define un objeto httpclient que es requerido por webClient para utilizarlo mas adelante
              .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
              .option(ChannelOption.SO_KEEPALIVE,true)
              .option(EpollChannelOption.TCP_KEEPIDLE,300)
              .option(EpollChannelOption.TCP_KEEPINTVL,60)
              .responseTimeout(Duration.ofSeconds(1))
              .doOnConnected(connetion -> { 
                  connetion.addHandlerLast(new ReadTimeoutHandler(5000, TimeUnit.MILLISECONDS)); // tiempo de lectura 
                  connetion.addHandlerLast(new WriteTimeoutHandler(5000, TimeUnit.MILLISECONDS)); // tiempo de escritura
              });
      
    //-----------------------------------------------------------// 
    //  FINAL CONFIGURACION CLINETE WEB
    //-----------------------------------------------------------// 
     
    @Autowired
    CustomerRepository repository;
    
    @GetMapping("/")
    public List<Customer> list(){
        return repository.findAll();
    }
    
    @GetMapping("/{id}")
    public Customer getById(@PathVariable long id){
        return repository.findById(id).get();
    }
    
    @PostMapping()
    public ResponseEntity<?> save(@RequestBody Customer input){
       input.getProducts().forEach(x-> x.setCustomer(input)); // se hara la relacion entre el cliente y los productos
       Customer Save = repository.save(input);
       return ResponseEntity.ok(Save);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable long id,@RequestBody Customer input){
        Customer save = repository.save(input);
        return ResponseEntity.ok(save);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable long id){
        Optional<Customer> findById = repository.findById(id);
        if(findById.get() != null){
            repository.delete(findById.get());
        }
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/full")
    public Customer get(@RequestParam String code){
       Customer customer = repository.findByCode(code);
       List<CustomerProducto> prodcuts = customer.getProducts();
       prodcuts.forEach(x -> {
           String nameProduct = getProduct(x.getIdProduct());
           x.setNameProduct(nameProduct);
       });
       return customer;
    }
    
    
    
     //---------------------------------------------------------------// 
    //  METODO QUE HACE EL LLAMDO Y CONSUMO DE MICROSERVISIO PRODUCT
    //---------------------------------------------------------------// 
     private String getProduct(long id){
         WebClient build = (WebClient) webClientBuilder.clientConnector(new ReactorClientHttpConnector(client))
                 .baseUrl("http://localhost:8082/product")
                 .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                 .defaultUriVariables(Collections.singletonMap("url", "http://localhost:8082/product"))
                 .build();
         
         JsonNode block = build.method(HttpMethod.GET).uri("/" + id)
                 .retrieve().bodyToMono(JsonNode.class).block();
         String name = block.get("name").asText();
         return name;
     }
     //----------------//
     // FINAL METODO
     //---------------//
}
