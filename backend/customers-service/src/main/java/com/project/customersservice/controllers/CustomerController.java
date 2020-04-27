package com.project.customersservice.controllers;

import com.project.customersservice.Service.CustomerService;
import com.project.customersservice.model.Commande;
import com.project.customersservice.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@RestController
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private LoadBalancerClient loadBalancer;

    @Autowired
    private RestTemplate restTemplate;



    @GetMapping("/customers")
    public List<Customer>  findAllCustomers() {
        return  customerService.findAllCustomers();
    }

    @GetMapping("/customers/{id}")
    public ResponseEntity<Customer> findCustomerById(@PathVariable Integer id) {

        Optional<Customer> customer = this.customerService.findCustomerById(id);
        if(customer.isPresent()){
            return new ResponseEntity<Customer>(customer.get(), HttpStatus.OK);

        }
        else{
            return new ResponseEntity<Customer>(HttpStatus.NO_CONTENT);

        }
    }

    @GetMapping("/customers/findbynumber/{number}")
    public ResponseEntity<Customer> findCustomerByNumber(@PathVariable Integer number) {

        Customer customer = this.customerService.findCustomerByNumber(number);
        if(customer != null){
            return new ResponseEntity<Customer>(customer, HttpStatus.OK);

        }
        else{
            return new ResponseEntity<Customer>(HttpStatus.NO_CONTENT);

        }
    }


    @PostMapping("/customers")
    public ResponseEntity<Customer> addCustomer(@RequestBody Customer customerNew) {
        //reste a changer si le customer existe deja on ne le rajout pas
        //////
        Customer customerCheck = this.customerService.findCustomerByNumber(customerNew.getNumber());
        if(customerCheck != null)
                return new ResponseEntity<Customer>(HttpStatus.CONFLICT);



        Customer customer = this.customerService.saveCustomer(customerNew);
        if (customer != null && customer.getId()!= null){

            return new ResponseEntity<Customer>(customer, HttpStatus.OK);

        }

        return new ResponseEntity<Customer>(HttpStatus.NOT_MODIFIED);

    }

    @PutMapping("/customers")
    public ResponseEntity<Customer> updateCustomer(@RequestBody Customer customerNew) {

        if (!customerService.checkIfIdexists(customerNew.getId())) {
            return new ResponseEntity<Customer>(HttpStatus.NOT_FOUND);
        }


        Customer customer = this.customerService.updateCustomer(customerNew);
        if (customer != null){

            return new ResponseEntity<Customer>(customer, HttpStatus.OK);

        }

        return new ResponseEntity<Customer>(HttpStatus.NOT_MODIFIED);

    }


    @DeleteMapping("/customers/{customerId}")
    public ResponseEntity<String> deleteCustomer(@PathVariable Integer customerId) {
        if (!customerService.checkIfIdexists(customerId)) {
            return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
        }
        ServiceInstance serviceCommande = loadBalancer.choose("commande-service");
        ResponseEntity<List<Commande>> response;
        response = restTemplate.exchange(serviceCommande.getUri().toString() + "/commandes/customer/" + customerId  , HttpMethod.GET,null,new ParameterizedTypeReference<List<Commande>>() {});

        List<Commande>  commandes = response.getBody();
        for(Commande com : commandes){
            restTemplate.delete(serviceCommande.getUri().toString() + "/commandes/"+com.getCommandeId());
        }
        customerService.deleteCustomer(customerId);
        return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
    }




}
