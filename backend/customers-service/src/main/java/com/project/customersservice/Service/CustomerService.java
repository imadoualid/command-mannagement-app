package com.project.customersservice.Service;

import com.project.customersservice.model.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerService {

    public Customer saveCustomer(Customer customer);

    public Customer updateCustomer(Customer customer);

    public void deleteCustomer(Integer customerId);

    public boolean checkIfIdexists(Integer id);

    public Customer findCustomerByEmail(String email);

    public Customer findCustomerByNumber(Integer number);


    public List<Customer> findCustomerByLastName(String lastName);

    public List<Customer> findAllCustomers();


    public Optional<Customer> findCustomerById(Integer customerId);

}
