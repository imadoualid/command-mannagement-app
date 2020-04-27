package com.project.commandeservice.service;

import com.project.commandeservice.model.Commande;
import com.project.commandeservice.model.CommandeEnum;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CommandeService {



    public Commande saveCommande(Commande com);

    public Commande updateCommande(Commande comm);

    public void deleteCommande(Integer commandeId);

    public List<Commande> findAllCommande();

    public Optional<Commande> findCommandeById(Integer id);

    public List<Commande> findCommandeForCustomer(Integer CustumerId);

    public List<Commande> findCommandeForProduit(Integer produitId);


    public List<Commande> findCommandeByStatus(CommandeEnum stat);


    public boolean checkIfCommandeExist(Integer CustomerId, Integer productId, CommandeEnum statut, LocalDate date );













}
