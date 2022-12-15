
package com.paymentcain.product.repository;

import com.paymentcain.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CrudProduct extends JpaRepository<Product,Long>{
    
}
