/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.inventory.dto;

import com.inventory.dao.ProductDAO;
import java.sql.ResultSet;

/**
 *
 * @author ADMIN
 */
public class ProductDTO {
    private int productId;
    private String codeproduit;
    private String date;
    private String sellDate;
    private String supplierCode;
    private String Produit;
    private int quantite;
    private double costPrice;
    private double sellingPrice;
    private String Reference;
    private int userId;
    private String customersName;
    private String codeprof;
    private Double totalCost;
    private Double totalRevenue;

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getcodeproduit() {
        return codeproduit;
    }

    public void setcodeproduit(String codeproduit) {
        this.codeproduit = codeproduit;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSellDate() {
        return sellDate;
    }

    public void setSellDate(String sellDate) {
        this.sellDate = sellDate;
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }
    
    public String getcodeprof() {
        return codeprof;
    }

    public void setcodeprof(String codeprof) {
        this.codeprof = codeprof;
    }

    public String getProduit() {
        return Produit;
    }

    public void setProduit(String Produit) {
        this.Produit = Produit;
    }

    public int getquantite() {
        return quantite;
    }

    public void setquantite(int quantite) {
        this.quantite = quantite;
    }

    public double getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(double costPrice) {
        this.costPrice = costPrice;
    }
    
    public double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public ResultSet getSuppliersName() {
        ResultSet rs=new ProductDAO().getSuppliersInfo();
        return rs;
    }  
    
    public ResultSet getCustomersName() {
        ResultSet rs=new ProductDAO().getCustomersInfo();
        return rs;
    }  
    
    public ResultSet getProductsName() {
        ResultSet rs=new ProductDAO().getProductInfo();
        return rs;
    } 

    public String getReference() {
        return Reference;
    }

    public void setReference(String Reference) {
        this.Reference = Reference;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }

    public Double getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(Double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }
    
}
