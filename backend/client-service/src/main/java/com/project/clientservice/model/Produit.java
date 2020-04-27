package com.project.clientservice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Objects;

public class Produit {

    private Integer id;

    private String name;

    private String price;

    private int nbExemplaire;


    private Category category;



    public Produit() {}

    public Produit(Integer id, String name, String price, int nbExemplaire, Category category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.nbExemplaire = nbExemplaire;
        this.category = category;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getNbExemplaire() {
        return nbExemplaire;
    }

    public void setNbExemplaire(int nbExemplaire) {
        this.nbExemplaire = nbExemplaire;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }


    @Override
    public String toString() {
        return "Produit{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price='" + price + '\'' +
                ", nbExemplaire=" + nbExemplaire +
                ", category=" + category +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Produit produit = (Produit) o;
        return nbExemplaire == produit.nbExemplaire &&
                Objects.equals(id, produit.id) &&
                Objects.equals(name, produit.name) &&
                Objects.equals(price, produit.price) &&
                Objects.equals(category, produit.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, nbExemplaire, category);
    }
}
