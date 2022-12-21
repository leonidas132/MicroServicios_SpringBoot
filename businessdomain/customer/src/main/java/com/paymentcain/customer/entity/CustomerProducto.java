
package com.paymentcain.customer.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import lombok.Data;

@Entity
@Data
public class CustomerProducto implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long idCustomerProduct;
    private long idProduct;
    
    @Transient // se usara para transportar el nombre del producto
    private String nameProduct;
    
    @JsonIgnore // para eviart una recursividad infinita 
    //esras dos anotaciones indican la relacion entre un cliente y un producto
    @ManyToOne (fetch = FetchType.LAZY, targetEntity = Customer.class)
    @JoinColumn (name = "idCustomer",nullable = true)
    private Customer customer;
}
