package com.project.produit.produitservice.repostirory;

import com.project.produit.produitservice.model.Produit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProduitRepository extends JpaRepository<Produit,Integer> {


    @Query(   "SELECT b "
            + "FROM Produit b "
            + "INNER JOIN b.category cat "
            + "WHERE cat.code = :code"
    )
    public List<Produit> 	findByCategory(@Param("code") String codeCategory);

    public Boolean existsByName(String name);

}
