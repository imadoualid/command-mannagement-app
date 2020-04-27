package com.project.customersservice.repository;

import com.project.customersservice.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {


    public Customer findCustomerByNumber(Integer number);

    public Customer findCustomerByEmailIgnoreCase(String email);

    public List<Customer> findCustomerByLastNameIgnoreCase(String lastName);


}
