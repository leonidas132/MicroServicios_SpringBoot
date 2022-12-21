/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paymentcain.customer.repository;

import com.paymentcain.customer.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author Luis Nuñez 
 */
public interface CustomerRepository extends JpaRepository<Customer,Long>{
    
    @Query("Select c from Customer c where c.code = ?1")
    public Customer findByCode(String code);
    
    @Query("Select c from Customer c where c.iban = ?1")
    public Customer findByIban(String iban);
    
    
    
}
