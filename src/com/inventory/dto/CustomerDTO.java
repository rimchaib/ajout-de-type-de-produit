



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.inventory.dto;

/**
 *
 * @author ADMIN
 */
public class CustomerDTO {
    private int customersId;
    private String codeprof;
    private String Nom;
    private String Email;
    private String phone;
    private double debit;
    private double credit;
    private double balance;
    private String CIN;
    private String Departement;
    

    public int getCustomersId() {
        return customersId;
    }

    public void setCustomersId(int customersId) {
        this.customersId = customersId;
    }

    public String getcodeprof() {
        return codeprof;
    }

    public void setcodeprof(String codeprof) {
        this.codeprof = codeprof;
    }

    public String getNom() {
        return Nom;
    }

    public void setNom(String Nom) {
        this.Nom = Nom;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }
    public String getCIN() {
        return CIN ; 
    }

    public void setCIN(String CIN) {
        this.CIN= CIN;
    }
    public String getDepartement() {
        return Departement ; 
    }

    public void setDepartement (String Departement) {
        this.Departement =Departement;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public double getDebit() {
        return debit;
    }

    public void setDebit(double debit) {
        this.debit = debit;
    }

    public double getCredit() {
        return credit;
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }    

    public String getphone() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
