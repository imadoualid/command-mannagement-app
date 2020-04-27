package com.project.produit.produitservice.service;

import com.project.produit.produitservice.model.Produit;

import java.util.List;
import java.util.Optional;


public interface ProduitService {

    public List<Produit> findAllProduct();

    public Optional<Produit> findProduct(Integer ProduitId);

    public Produit saveProduit(Produit produit);

    public Produit updateProduct(Produit produit);

    public void deleteProduit(Integer ProduitId);


    public List<Produit> getProductsByCategory(String codeCategory);


    public boolean checkIfIdExists(Integer id);
    public boolean checkIfNomProduitExiste(String nom);



}
