
package com.paymentcain.product.controller;

import com.paymentcain.product.entity.Product;
import com.paymentcain.product.repository.CrudProduct;
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
@RequestMapping("/product")
public class ProductRestController {
    
    @Autowired
    CrudProduct product;
    
  
    @GetMapping("/")
    public List<Product> list(){
        return  product.findAll();
    }
    
    @GetMapping("/{id}")
    public Product getById(@PathVariable long id){
        return  product.findById(id).get();
    }
    
    @PostMapping("/{id}")
    public ResponseEntity<?> save(@RequestBody Product input){
       Product Save =  product.save(input);
       return ResponseEntity.ok(Save);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable long id,@RequestBody Product input){
        Product save =  product.save(input);
        return ResponseEntity.ok(save);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable long id){
        Optional<Product> findById =  product.findById(id);
        if(findById.get() != null){
             product.delete(findById.get());
        }
        return ResponseEntity.ok().build();
    }

}
