package com.project.produit.produitservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;


public class Commande {
    private Integer commandeId;

    private Integer produitId;

    private Integer customerId;

    private CommandeEnum statut;

    private Integer qte;

    @JsonFormat(pattern="dd-MM-yyyy")
    private LocalDate dateCommande;

    public Commande() {
    }


    public Commande(Integer commandeId, Integer produitId, Integer custumerId, CommandeEnum statut, Integer qte, LocalDate dateCommande) {
        this.commandeId = commandeId;
        this.produitId = produitId;
        this.customerId = custumerId;
        this.statut = statut;
        this.qte = qte;
        this.dateCommande = dateCommande;
    }

    public Integer getCommandeId() {
        return commandeId;
    }

    public void setCommandeId(Integer commandeId) {
        this.commandeId = commandeId;
    }

    public Integer getProduitId() {
        return produitId;
    }

    public void setProduitId(Integer produitId) {
        this.produitId = produitId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public CommandeEnum getStatut() {
        return statut;
    }

    public void setStatut(CommandeEnum statut) {
        this.statut = statut;
    }

    public Integer getQte() {
        return qte;
    }

    public void setQte(Integer qte) {
        this.qte = qte;
    }




    public LocalDate getDateCommande() {
        return dateCommande;
    }

    public void setDateCommande(LocalDate dateCommande) {
        this.dateCommande = dateCommande;
    }

    @Override
    public String toString() {
        return "Commande{" +
                "commandeId=" + commandeId +
                ", produitId=" + produitId +
                ", customerId=" + customerId +
                ", statut=" + statut +
                ", qte=" + qte +
                ", dateCommande=" + dateCommande +
                '}';
    }
}
