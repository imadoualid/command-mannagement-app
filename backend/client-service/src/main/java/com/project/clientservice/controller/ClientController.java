package com.project.clientservice.controller;

import com.project.clientservice.model.Commande;
import com.project.clientservice.model.CommandeFullInfo;
import com.project.clientservice.model.Customer;
import com.project.clientservice.model.Produit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;
/*@CrossOrigin(origins = "*")*/
@RestController
public class ClientController {

    @Autowired
    private LoadBalancerClient loadBalancer;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/client/commandes")
    public List<CommandeFullInfo> GetAllCommandeWithAllInfo() {
        //on recupere les commandes depuis le service commmnades
        ServiceInstance serviceCommande = loadBalancer.choose("commande-service");
        ResponseEntity<List<Commande>> response;
        response = restTemplate.exchange(serviceCommande.getUri().toString() + "/commandes", HttpMethod.GET,null,new ParameterizedTypeReference<List<Commande>>() {});
        List<Commande>  commandes = response.getBody();



        //on va recuperer pour chaque commande le customer

        ServiceInstance serviceCustomer = loadBalancer.choose("customer-service");

        ServiceInstance serviceProduit = loadBalancer.choose("produit-service");



        return commandes.stream().map(commande -> {
            Customer customer = restTemplate.getForObject(serviceCustomer.getUri().toString()+"/customers/"+commande.getCustomerId(), Customer.class);
            Produit produit = restTemplate.getForObject(serviceProduit.getUri().toString()+"/produits/"+commande.getProduitId(), Produit.class);
            return new CommandeFullInfo(commande,customer, produit);
        }).collect(Collectors.toList());


    }
    @GetMapping("/client/commandes/customers/{id}")
    public List<CommandeFullInfo> GetCommandeForCustomer(@PathVariable Integer id) {

        ServiceInstance serviceCommande = loadBalancer.choose("commande-service");
        ResponseEntity<List<Commande>> response;
        response = restTemplate.exchange(serviceCommande.getUri().toString() + "/commandes/customer/"+ id, HttpMethod.GET,null,new ParameterizedTypeReference<List<Commande>>() {});
        List<Commande>  commandes = response.getBody();

        ServiceInstance serviceCustomer = loadBalancer.choose("customer-service");
        ServiceInstance serviceProduit = loadBalancer.choose("produit-service");
        Customer customer = restTemplate.getForObject(serviceCustomer.getUri().toString()+"/customers/"+id, Customer.class);
        return commandes.stream().map(commande -> {
            Produit produit = restTemplate.getForObject(serviceProduit.getUri().toString()+"/produits/"+commande.getProduitId(), Produit.class);
            return new CommandeFullInfo(commande,customer, produit);
        }).collect(Collectors.toList());

    }


    @GetMapping("/client/commandes/{id}")
    public CommandeFullInfo GetCommandeWithAllInfo(@PathVariable Integer id) {
        ServiceInstance serviceCommande = loadBalancer.choose("commande-service");
        ResponseEntity<Commande> response;
        response = restTemplate.exchange(serviceCommande.getUri().toString() + "/commandes/"+id, HttpMethod.GET,null,new ParameterizedTypeReference<Commande>() {});
        Commande  commande = response.getBody();

        ServiceInstance serviceCustomer = loadBalancer.choose("customer-service");
        ServiceInstance serviceProduit = loadBalancer.choose("produit-service");

        Customer customer = restTemplate.getForObject(serviceCustomer.getUri().toString()+"/customers/"+commande.getCustomerId(), Customer.class);
        Produit produit = restTemplate.getForObject(serviceProduit.getUri().toString()+"/produits/"+commande.getProduitId(), Produit.class);
        return new CommandeFullInfo(commande,customer, produit);

    }

    @PostMapping("/client/commandes")
    public CommandeFullInfo addCommande(@RequestBody CommandeFullInfo fullCommande){
        //faut verfiier si le produit existe
        ServiceInstance serviceCustomer = loadBalancer.choose("customer-service");
        ServiceInstance serviceProduit = loadBalancer.choose("produit-service");


        ResponseEntity<Produit> responseProduit;
        responseProduit = restTemplate.getForEntity(serviceProduit.getUri().toString()+"/produits/"+fullCommande.getProduit().getId(), Produit.class);
        // reste a regle si le produit n existe pas
        System.out.println("dddscsdnvpfvndkfbnqdfnbkdgnbkldfbnklfgnbdgkbndkfbndkfbndklfnbndbnsdivsndpivnsifvns");
        System.out.println(responseProduit.getBody());
        ResponseEntity<Customer> responseCustomer;
        responseCustomer = restTemplate.getForEntity(serviceCustomer.getUri().toString()+"/customers/findbynumber/"+fullCommande.getCustomer().getNumber(), Customer.class);

        System.out.println("---------------------------------------");
        System.out.println(responseCustomer.getBody());

        if(responseCustomer.getBody() == null)
            responseCustomer = restTemplate.postForEntity(serviceCustomer.getUri().toString()+"/customers",fullCommande.getCustomer(),Customer.class);

        System.out.println("ajajajajajajajajajajajajaj");
        System.out.println(responseCustomer.getBody());

        // on rajoute l id du custumer a la commande
        fullCommande.getCommande().setCustomerId(responseCustomer.getBody().getId());
        ServiceInstance serviceCommande = loadBalancer.choose("commande-service");
        ResponseEntity<Commande> response;
        response = restTemplate.postForEntity(serviceCommande.getUri().toString()+"/commandes",fullCommande.getCommande(),Commande.class);
        System.out.println("gloglogloglog");
        System.out.println(response.getBody());


        //reste a regler les excepetion
        return new CommandeFullInfo(response.getBody(),responseCustomer.getBody(),responseProduit.getBody());


    }


    /*pas besoin */
    @PutMapping("/client/commandes")
    public CommandeFullInfo updateCommande(@RequestBody CommandeFullInfo fullCommande){
        //faut verfiier si le produit existe
        ServiceInstance serviceCustomer = loadBalancer.choose("customer-service");
        ServiceInstance serviceProduit = loadBalancer.choose("produit-service");
        ServiceInstance serviceCommande = loadBalancer.choose("commande-service");


        Produit produit = restTemplate.getForObject(serviceProduit.getUri().toString()+"/produits/"+fullCommande.getCommande().getProduitId(), Produit.class);




        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity<Customer> responseCustomer;

        HttpEntity<Customer> entity = new HttpEntity<Customer>(fullCommande.getCustomer(), headers);
        ResponseEntity<Customer> response = restTemplate.exchange(serviceCustomer.getUri().toString()+"/customers", HttpMethod.PUT, entity, Customer.class,fullCommande.getCustomer().getId() );
        System.out.println(response.getBody());
        //faudrait verifier si le customer a ete modifier peut etre qu il n existe pas responde==null





        HttpEntity<Commande>  entityCommande = new HttpEntity<Commande>(fullCommande.getCommande(), headers);

        ResponseEntity<Commande> responseCommande = restTemplate.exchange(serviceCommande.getUri().toString()+"/commandes", HttpMethod.PUT, entityCommande, Commande.class,fullCommande.getCommande().getCommandeId());

        System.out.println(responseCommande.getBody());


        //reste a regler les excepetion
        return new CommandeFullInfo(responseCommande.getBody(),response.getBody(),produit);


    }






    @DeleteMapping("/client/commandes/{id}")
    public void DeleteCommande(@PathVariable Integer id){
        ServiceInstance serviceCommande = loadBalancer.choose("commande-service");
        restTemplate.delete(serviceCommande.getUri().toString() + "/commandes/"+id);

    }


/*
    @GetMapping("/client/customers")
    public List<Customer> GetAllCustomers() {

        ServiceInstance serviceCustomer = loadBalancer.choose("customer-service");
        ResponseEntity<List<Customer>> response;
        response = restTemplate.exchange(serviceCustomer.getUri().toString() + "/customers", HttpMethod.GET,null,new ParameterizedTypeReference<List<Customer>>() {});
        List<Customer>  customers = response.getBody();

        return customers;


    }

    @GetMapping("/client/customers/{id}")
    public Customer GetCustomer(@PathVariable Integer id) {

        ServiceInstance serviceCustomer = loadBalancer.choose("customer-service");
        ResponseEntity<Customer> response;
        response = restTemplate.getForEntity(serviceCustomer.getUri().toString()+"/customers/"+id, Customer.class);
        Customer  customers = response.getBody();

        return customers;


    }


    @PostMapping("/client/customers")
    public Customer AddCustomers(@RequestBody Customer customer) {

        ServiceInstance serviceCustomer = loadBalancer.choose("customer-service");
        ResponseEntity<Customer> response;

        response  = restTemplate.postForEntity(serviceCustomer.getUri().toString()+"/customers",customer,Customer.class);

        return response.getBody();


    }
    @PutMapping("/client/customers")
    public Customer updateCustomer(@RequestBody Customer customer) {

        System.out.println(customer);
        ServiceInstance serviceCustomer = loadBalancer.choose("customer-service");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Customer>  entityCustomer = new HttpEntity<Customer>(customer, headers);

        ResponseEntity<Customer> responseCustomer = restTemplate.exchange(serviceCustomer.getUri().toString()+"/customers", HttpMethod.PUT, entityCustomer, Customer.class,customer.getId());
        System.out.println("aajajajajaajaj");


        return responseCustomer.getBody();


    }

    @DeleteMapping("/client/customers/{id}")
    public void DeleteCustometr(@PathVariable Integer id){
        ServiceInstance serviceCustomer = loadBalancer.choose("customer-service");
        restTemplate.delete(serviceCustomer.getUri().toString() + "/customers/"+id);

    }


    @GetMapping("/client/produits")
    public List<Produit> GetAllProduits() {

        ServiceInstance serviceProduit = loadBalancer.choose("produit-service");
        ResponseEntity<List<Produit>> response;
        response = restTemplate.exchange(serviceProduit.getUri().toString() + "/produits", HttpMethod.GET,null,new ParameterizedTypeReference<List<Produit>>() {});
        List<Produit>  produits = response.getBody();

        return produits;


    }

    @GetMapping("/client/produits/{id}")
    public Produit GetProduit(@PathVariable Integer id) {

        ServiceInstance serviceProduit = loadBalancer.choose("produit-service");
        ResponseEntity<Produit> response;
        response = restTemplate.getForEntity(serviceProduit.getUri().toString()+"/produits/"+id, Produit.class);
        Produit  produits = response.getBody();

        return produits;


    }


    @PostMapping("/client/produits")
    public Produit AddProduit(@RequestBody Produit produit) {

        System.out.println(produit);

        ServiceInstance serviceProduit = loadBalancer.choose("produit-service");
        ResponseEntity<Produit> response;

        response  = restTemplate.postForEntity(serviceProduit.getUri().toString()+"/produits",produit,Produit.class);

        return response.getBody();


    }

    @PutMapping("/client/produits")
    public Produit updateProduit(@RequestBody Produit produit) {

        System.out.println(produit);
        ServiceInstance serviceProduit = loadBalancer.choose("produit-service");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Produit>  entityProduit = new HttpEntity<Produit>(produit, headers);

        ResponseEntity<Produit> responseProduit = restTemplate.exchange(serviceProduit.getUri().toString()+"/produits", HttpMethod.PUT, entityProduit, Produit.class,produit.getId());
        System.out.println("aajajajajaajaj");


        return responseProduit.getBody();


    }

    @DeleteMapping("/client/produits/{id}")
    public void Deleteproduit(@PathVariable Integer id){
        ServiceInstance serviceProduit = loadBalancer.choose("produit-service");
        restTemplate.delete(serviceProduit.getUri().toString() + "/produits/"+id);


    }

*/

}
