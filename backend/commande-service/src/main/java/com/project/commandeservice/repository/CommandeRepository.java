package com.project.commandeservice.repository;

import com.project.commandeservice.model.Commande;
import com.project.commandeservice.model.CommandeEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CommandeRepository  extends JpaRepository<Commande,Integer> {

    public List<Commande> findCommandeByStatut(CommandeEnum statut);
    public List<Commande> findCommandeByCustomerId(Integer customerid);
    public List<Commande> findCommandeByProduitId(Integer produitId);




    public Commande findCommandeByCustomerIdAndProduitIdAndStatutAndDateCommande(Integer customerId, Integer produitId, CommandeEnum stat,LocalDate date);

    @Query(   "SELECT lo "
            + "FROM Commande lo "
            + "WHERE lo.customerId = :code1 "
            + "   AND lo.produitId = :code2 "
            + "   AND lo.statut = :code3 ")
    public Commande getCommandeByCriteria(@Param("code1") Integer customerId,@Param("code2") Integer produitId,@Param("code3") CommandeEnum stat);







}
