package com.project.commandeservice.service;

import com.netflix.discovery.converters.Auto;
import com.project.commandeservice.model.Commande;
import com.project.commandeservice.model.CommandeEnum;
import com.project.commandeservice.repository.CommandeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
@Service
public class CommandeServiceImpl implements  CommandeService{

    @Autowired
    private CommandeRepository commandeRepository;


    @Override
    public Commande saveCommande(Commande com) {
        return this.commandeRepository.save(com);
    }

    @Override
    public Commande updateCommande(Commande comm) {
        return this.commandeRepository.save(comm);
    }

    @Override
    public void deleteCommande(Integer commandeId) {
        this.commandeRepository.deleteById(commandeId);
    }

    @Override
    public List<Commande> findAllCommande() {
        return this.commandeRepository.findAll();
    }

    @Override
    public Optional<Commande> findCommandeById(Integer id) {
        return this.commandeRepository.findById(id);
    }

    @Override
    public List<Commande> findCommandeForCustomer(Integer custumerId) {
        return this.commandeRepository.findCommandeByCustomerId(custumerId);
    }

    @Override
    public List<Commande> findCommandeForProduit(Integer produitId) {
        return this.commandeRepository.findCommandeByProduitId(produitId);
    }

    @Override
    public List<Commande> findCommandeByStatus(CommandeEnum stat) {
        return this.commandeRepository.findCommandeByStatut(stat);
    }

    @Override
    public boolean checkIfCommandeExist(Integer customerId, Integer productId,CommandeEnum statut ,LocalDate date) {
        Commande com= this.commandeRepository.findCommandeByCustomerIdAndProduitIdAndStatutAndDateCommande(customerId,productId,statut,date);
        if (com == null)
            return false;
        return true;
    }

}
