
package com.paymentcain.transactions.repository;

import com.paymentcain.transactions.entity.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CrudTransactions extends JpaRepository<Transactions, Long> {
    
}
