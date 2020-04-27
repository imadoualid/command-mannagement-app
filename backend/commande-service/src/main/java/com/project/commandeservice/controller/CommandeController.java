package com.project.commandeservice.controller;

import com.project.commandeservice.model.Commande;
import com.project.commandeservice.service.CommandeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@RestController
public class CommandeController {

    @Autowired
    CommandeService commandeService;


    @GetMapping("/commandes")
    public List<Commande> findAllCommande() {
        List<Commande> commandes = commandeService.findAllCommande();
        return commandes;

    }

    @GetMapping("/commandes/{id}")
    public ResponseEntity<Commande> findCommande(@PathVariable Integer id) {
        Optional<Commande> commande = commandeService.findCommandeById(id);


        if(commande.isPresent()){
            return new ResponseEntity<Commande>(commande.get(), HttpStatus.OK);

        }
        else{
            return new ResponseEntity<Commande>(HttpStatus.NO_CONTENT);

        }
    }


    @GetMapping("/commandes/customer/{customerId}")
    public List<Commande> searchAllcommandeCustomer(@PathVariable Integer customerId) {
        List<Commande> commandes = commandeService.findCommandeForCustomer(customerId);
        return commandes;

    }

    @GetMapping("/commandes/produits/{produitid}")
    public List<Commande> searchAllCommandeForProduit(@PathVariable Integer produitid) {
        List<Commande> commandes = commandeService.findCommandeForProduit(produitid);
        return commandes;

    }


    @PostMapping("/commandes")
  public ResponseEntity<Commande> addCommande(@RequestBody Commande commandeNew) {
        boolean isCommandeExists = commandeService.checkIfCommandeExist(commandeNew.getCustomerId(),commandeNew.getProduitId(),commandeNew.getStatut(),commandeNew.getDateCommande());
        if (isCommandeExists) {
            return new ResponseEntity<Commande>(HttpStatus.CONFLICT);
        }
        Commande commande = this.commandeService.saveCommande(commandeNew);
        if (commande != null && commande.getCommandeId()!= null){

            return new ResponseEntity<Commande>(commande, HttpStatus.OK);

        }

        return new ResponseEntity<Commande>(HttpStatus.NOT_MODIFIED);

    }


    @PutMapping("/commandes")
    public ResponseEntity<Commande> updateCommande(@RequestBody Commande commandeNew) {
        Optional<Commande> commande = commandeService.findCommandeById(commandeNew.getCommandeId());
        if (!commande.isPresent()) {
            return new ResponseEntity<Commande>(HttpStatus.NOT_FOUND);
        }
        Commande commande2 = this.commandeService.saveCommande(commandeNew);
        if (commande2 != null ){

            return new ResponseEntity<Commande>(commande2, HttpStatus.OK);

        }

        return new ResponseEntity<Commande>(HttpStatus.NOT_MODIFIED);

    }

    @DeleteMapping("/commandes/{commandeId}")
    public ResponseEntity<String> deleteCustomer(@PathVariable Integer commandeId) {
        Optional<Commande> commande = commandeService.findCommandeById(commandeId);
        if (!commande.isPresent() )
            return new ResponseEntity<String>(HttpStatus.NOT_FOUND);

        this.commandeService.deleteCommande(commandeId);
        return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
    }


}