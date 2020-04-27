package com.project.produit.produitservice.service;

import com.project.produit.produitservice.model.Produit;
import com.project.produit.produitservice.repostirory.ProduitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class ProduitServiceImpl implements ProduitService {
    @Autowired
    private ProduitRepository produitRepository;


    @Override
    public List<Produit> findAllProduct() {
        return this.produitRepository.findAll();
    }

    @Override
    public Optional<Produit> findProduct(Integer ProduitId) {
        return this.produitRepository.findById(ProduitId);
    }

    @Override
    public Produit saveProduit(Produit produit) {

        return this.produitRepository.save(produit);
    }

    @Override
    public Produit updateProduct(Produit produit) {
        return this.produitRepository.save(produit);
    }

    @Override
    public void deleteProduit(Integer produitId) {
        this.produitRepository.deleteById(produitId);
    }

    @Override
    public List<Produit> getProductsByCategory(String codeCategory) {
        return this.produitRepository.findByCategory(codeCategory);
    }

    @Override
    public boolean checkIfIdExists(Integer ProduitId) {
        return this.produitRepository.existsById(ProduitId);
    }

    @Override
    public boolean checkIfNomProduitExiste(String nom) {
        return this.produitRepository.existsByName(nom);
    }
}
