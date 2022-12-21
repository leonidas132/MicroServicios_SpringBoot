
package com.paymentcain.customer.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import lombok.Data;



@Entity
@Data
public class Customer implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long idCustomer;
    
    private String code;
     
    private String iban;
     
    private String name;
    
    private String surname;
    
    private String phone;
    
    private String addres;
    
    
    //cascade= CascadeType.All
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer", orphanRemoval = true,cascade= CascadeType.ALL)
    private List<CustomerProducto> products;
    
    @Transient
    private List<?> transaction;

  
     
}
