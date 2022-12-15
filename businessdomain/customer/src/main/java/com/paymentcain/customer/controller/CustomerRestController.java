
package com.paymentcain.customer.controller;


import com.paymentcain.customer.entity.Customer;
import com.paymentcain.customer.repository.CustomerRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
public class CustomerRestController {
    
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
    
    @PostMapping("/{id}")
    public ResponseEntity<?> save(@RequestBody Customer input){
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
}
