package com.project.produit.produitservice.controller;

import com.project.produit.produitservice.model.Commande;
import com.project.produit.produitservice.model.Produit;
import com.project.produit.produitservice.service.ProduitServiceImpl;
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
public class ProduitController {

    @Autowired
    private ProduitServiceImpl produitService;
    @Autowired
    private LoadBalancerClient loadBalancer;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/produits")
    public List<Produit> GetAllProduct(){

        return this.produitService.findAllProduct();

    }

    @GetMapping("/produits/{ProduitId}")
    public ResponseEntity<Produit> addProduct(@PathVariable("ProduitId") Integer produitId) {
        Optional<Produit> produit = this.produitService.findProduct(produitId);
        if(produit.isPresent()){
            return new ResponseEntity<Produit>(produit.get(), HttpStatus.OK);

        }
        else{
            return new ResponseEntity<Produit>(HttpStatus.NO_CONTENT);

        }

    }

    @PostMapping("/produits")
    public ResponseEntity<Produit> addProduit(@RequestBody Produit produitNew) {

        if(this.produitService.checkIfNomProduitExiste(produitNew.getName()))
            return new ResponseEntity<Produit>(HttpStatus.CONFLICT);

        Produit produit = this.produitService.saveProduit(produitNew);
        if (produit != null && produit.getId()!= null){

            return new ResponseEntity<Produit>(produit, HttpStatus.OK);

        }

        return new ResponseEntity<Produit>(HttpStatus.NOT_MODIFIED);

    }



    @PutMapping("/produits")
    public ResponseEntity<Produit> updateProduit(@RequestBody Produit produitNew) {

        if (!this.produitService.checkIfIdExists(produitNew.getId())) {
            return new ResponseEntity<Produit>(HttpStatus.NOT_FOUND);
        }



        Produit produit = this.produitService.updateProduct(produitNew);
        return new ResponseEntity<Produit>(produit,HttpStatus.OK);

    }

    @DeleteMapping("/produits/{produitId}")
    public ResponseEntity<String> deleteProduct(@PathVariable("produitId") Integer produitId){
        /*LA SUPP d un produit supprimera les commande en  relation avec ce dernier*/
        ServiceInstance serviceCommande = loadBalancer.choose("commande-service");
        ResponseEntity<List<Commande>> response;
        response = restTemplate.exchange(serviceCommande.getUri().toString() + "/commandes/produits/" + produitId  , HttpMethod.GET,null,new ParameterizedTypeReference<List<Commande>>() {});

        List<Commande>  commandes = response.getBody();
        for(Commande com : commandes){
            restTemplate.delete(serviceCommande.getUri().toString() + "/commandes/"+com.getCommandeId());
        }

        this.produitService.deleteProduit(produitId);

        //http://localhost:9004/commandes-service/commandes/produits/
        return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
    }


}














