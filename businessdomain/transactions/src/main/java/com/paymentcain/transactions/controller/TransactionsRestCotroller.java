package com.paymentcain.transactions.controller;

import com.paymentcain.transactions.entity.Transactions;
import com.paymentcain.transactions.repository.CrudTransactions;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions")
public class TransactionsRestCotroller {
    
    @Autowired
    CrudTransactions crudTransactions;
    
    @GetMapping("/")
    public List<Transactions> post(){
        return crudTransactions.findAll();
    }
    
    @PostMapping("/{id}")
    public ResponseEntity<?> post(@RequestBody Transactions input){
        Transactions Save = crudTransactions.save(input);
        return ResponseEntity.ok(Save);
    }
}
