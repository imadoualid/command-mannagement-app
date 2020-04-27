package com.project.clientservice.model;

public class CommandeFullInfo {

   private Commande commande;
   private Customer customer;


    private Produit produit;


    public CommandeFullInfo(Commande commande, Customer customer, Produit produit) {
        this.commande = commande;
        this.customer = customer;
        this.produit = produit;
    }

    public CommandeFullInfo() {
    }


    public Commande getCommande() {
        return commande;
    }

    public void setCommande(Commande commande) {
        this.commande = commande;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }


    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    @Override
    public String toString() {
        return "CommandeFullInfo{" +
                "commande=" + commande +
                ", customer=" + customer +
                ", produit=" + produit +
                '}';
    }
}
