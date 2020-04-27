package com.project.customersservice.Service;

import com.project.customersservice.model.Customer;
import com.project.customersservice.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImp implements CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }


    @Override
    public Customer updateCustomer(Customer customer) {
        return this.customerRepository.save(customer);
    }

    @Override
    public void deleteCustomer(Integer customerId) {
            this.customerRepository.deleteById(customerId);
    }

    @Override
    public boolean checkIfIdexists(Integer id) {
        return customerRepository.existsById(id);
    }

    @Override
    public Customer findCustomerByEmail(String email) {
        return customerRepository.findCustomerByEmailIgnoreCase(email);
    }

    @Override
    public Customer findCustomerByNumber(Integer number) {
        return this.customerRepository.findCustomerByNumber(number);
    }

    @Override
    public List<Customer> findCustomerByLastName(String lastName) {
        return this.customerRepository.findCustomerByLastNameIgnoreCase(lastName);
    }

    @Override
    public List<Customer> findAllCustomers() {
        return this.customerRepository.findAll();
    }

    @Override
    public Optional<Customer> findCustomerById(Integer customerId) {
        return this.customerRepository.findById(customerId);
    }
}
