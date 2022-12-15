
package com.paymentcain.transactions.entity;

import java.io.Serializable;


import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Transactions implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idTransaction;
    
    private String referencia;
    
    private String accountlban;
    
    private LocalDateTime date1;
    
    private double amount;
    
    private double fee;
        
    private String description;
    
    private String status;
    
    private String channel;
    
}
