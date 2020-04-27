package com.project.clientservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;


public class Category {


    private String code;

    private String label;


    @JsonIgnore
    private List<Produit> produits;

    public Category(){
    }

    public Category(String code, String label) {
        this.code = code;
        this.label = label;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<Produit> getProduits() {
        return produits;
    }

    public void setProduits(List<Produit> produits) {
        this.produits = produits;
    }

    @Override
    public String toString() {
        return "Category{" +
                "code='" + code + '\'' +
                ", label='" + label + '\'' +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(code, category.code) &&
                Objects.equals(label, category.label) &&
                Objects.equals(produits, category.produits);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, label, produits);
    }
}
