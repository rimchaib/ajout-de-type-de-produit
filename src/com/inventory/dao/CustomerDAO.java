/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.inventory.dao;

import com.inventory.database.ConnectionFactory;
import com.inventory.dto.CustomerDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ADMIN
 */
public class CustomerDAO {
    Connection con = null;
    PreparedStatement pstmt = null;
    Statement stmt = null;
    ResultSet rs = null;
    
    public CustomerDAO(){
        try {
            con = new ConnectionFactory().getConnection();
            stmt = con.createStatement();
        } catch (Exception e) {
                e.printStackTrace();
        }
    }

    /***
     * Refactoring name: RENAME VARIABLE
     * Changed variable name from query => findCustomersByNameEmailAndPhone
     */
     public void addCustomerDAO(CustomerDTO customerdto) {
        try{
                String findCustomersByNameEmailPhoneCINAndDepartement = "SELECT * FROM customers WHERE Nom='"+customerdto.getNom()
                        +"' AND Email='"+customerdto.getEmail()+"'AND phone='"+customerdto.getPhone()
                        +"'  AND CIN='"+customerdto.getCIN()+"' AND Departement='"+customerdto.getDepartement()+"'";
                rs=stmt.executeQuery(findCustomersByNameEmailPhoneCINAndDepartement);
                if(rs.next()){
                    JOptionPane.showMessageDialog(null,"Le même professeur a déjà été ajouté!");
                }else{
                    addFunction(customerdto);
                }
        }catch(Exception e){
                e.printStackTrace();
        }           
    }//end of method addCustomerDTO

    /***
     * Refactoring name: RENAME VARIABLE
     * Changed variable name from
     * query1 => getAllCustomers
     * query2 => getAllCustomersInDescOrder
     * q => insertCustomers
     */
     public void addFunction(CustomerDTO customerdto){
         try {
                        String codeprof = null;
                        String oldcodeprof = null;
                        String getAllCustomers="SELECT * FROM customers";
                        rs=stmt.executeQuery(getAllCustomers);
                        if(!rs.next()){
                            codeprof="cus"+"1"; 
                        }
                        else{
                            String getAllCustomersInDescOrder="SELECT * FROM customers ORDER by cid DESC";
                            rs=stmt.executeQuery(getAllCustomersInDescOrder);
                            if(rs.next()){
                                oldcodeprof=rs.getString("codeprof");
                                Integer scode=Integer.parseInt(oldcodeprof.substring(3));
                                scode++;    
                                codeprof="cus"+scode;
                            }
                        }
                            String insertCustomers = "INSERT INTO customers VALUES(null,?,?,?,?,?,?)";
                            pstmt = (PreparedStatement) con.prepareStatement(insertCustomers);
                            pstmt.setString(1, codeprof);
                            pstmt.setString(2, customerdto.getNom());
                            pstmt.setString(3, customerdto.getEmail());
                            pstmt.setString(4, customerdto.getPhone());
                            pstmt.setString(5, customerdto.getCIN());
                            pstmt.setString(6, customerdto.getDepartement());
                            pstmt.executeUpdate();
                            JOptionPane.showMessageDialog(null, "Inséré avec succès");
                        }catch (Exception e) {
                            e.printStackTrace();
                        }
     }

    /***
     * Refactoring name: RENAME VARIABLE
     * Changed variable name from query => updateCustomerDetails
     */
    public void editCustomerDAO(CustomerDTO customerdto){
          try {
                        String updateCustomerDetails = "UPDATE customers SET Nom=?,Email=?,phone=?,CIN=?,Departement=? WHERE codeprof=?";
                        pstmt = (PreparedStatement) con.prepareStatement(updateCustomerDetails);
                        
                        pstmt.setString(1, customerdto.getNom());
                        pstmt.setString(2, customerdto.getEmail());
                        pstmt.setString(3, customerdto.getPhone());
                        pstmt.setString(4, customerdto.getCIN());
                        pstmt.setString(5, customerdto.getDepartement());
                        pstmt.setString(6 ,customerdto.getcodeprof());
                        
                      
                        pstmt.executeUpdate();
                        JOptionPane.showMessageDialog(null, "Mis à jour avec succés!"); 
                    
            } catch (Exception e) {
                e.printStackTrace();
            } 
    }
    

    /***
     * Refactoring name: RENAME VARIABLE
     * Changed variable name from query => deleteCustomerByCode
     */
    public void deleteCustomerDAO(String value){
        try{
            System.out.println(value);
            String deleteCustomerByCode="delete from customers where codeprof=?";
            pstmt=con.prepareStatement(deleteCustomerByCode);
            pstmt.setString(1,value);
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "vous etes sur de supprimer..");
        }catch(SQLException  e){
            e.printStackTrace();
        }
    }


    /***
     * Refactoring name: RENAME VARIABLE
     * Changed variable name from query => getCustomersDetails
     */
    public ResultSet getQueryResult() {
        try {
            String getCustomersDetails = "SELECT codeprof AS codeprof, Nom AS Name, Email AS Email, phone AS Phone ,CIN AS CIN,Departement AS Departement FROM customers";
            rs = stmt.executeQuery(getCustomersDetails);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }//end of method getQueryResult

    /***
     * Refactoring name: RENAME VARIABLE
     * Changed variable name from query => getCustomersWithCredit
     */
    public ResultSet getCreditCustomersQueryResult() {
        try {
            String getCustomersWithCredit = "SELECT * FROM customers WHERE credit>0";
            rs = stmt.executeQuery(getCustomersWithCredit);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    /***
     * Refactoring name: RENAME VARIABLE
     * Changed variable name from query => getCustomersWithDebit
     */
    public ResultSet getDebitCustomersQueryResult() {
        try {
            String getCustomersWithDebit = "SELECT * FROM customers WHERE credit=0";
            rs = stmt.executeQuery(getCustomersWithDebit);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    /***
     * Refactoring name: RENAME VARIABLE
     * Changed variable name from query => getCustomers
     */
    public ResultSet getSearchCustomersQueryResult(String searchTxt) {
        try {
            String getCustomers = "SELECT * FROM customers WHERE Nom LIKE '%"+searchTxt+"%' OR Email LIKE '%"+searchTxt+"%'OR CIN LIKE '%"+searchTxt+"%'OR Departement LIKE '%"+searchTxt+"%' OR codeprof LIKE '%"+searchTxt+"%' OR phone LIKE '%"+searchTxt+"%'";
            rs = stmt.executeQuery(getCustomers);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    /***
     * Refactoring name: RENAME VARIABLE
     * Changed variable name from query => getCustomersByCode
     */
    public ResultSet getCustomersName(String codeprof){
        try{
            String getCustomersByCode="SELECT * FROM customers WHERE codeprof='"+codeprof+"'";
            rs=stmt.executeQuery(getCustomersByCode);
        }catch(Exception e){
            e.printStackTrace();
        }
        return rs;
    }

    /***
     * Refactoring name: RENAME VARIABLE
     * Changed variable name from query => getCustomersBycodeproduit
     */
    public ResultSet getProductsName(String codeproduit){
        try{
            String getCustomersBycodeproduit="SELECT Produit, currentstocks.quantite FROM products INNER JOIN currentstocks ON products.codeproduit=currentstocks.codeproduit WHERE currentstocks.codeproduit='"+codeproduit+"'";
            rs=stmt.executeQuery(getCustomersBycodeproduit);
        }catch(Exception e){
            e.printStackTrace();
        }
        return rs;
    }

    //start of method DefaultTableModel
    public DefaultTableModel buildTableModel(ResultSet rs) throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData(); //resultset ko metadata
        Vector<String> columnNames = new Vector<String>();
        int columnCount = metaData.getColumnCount();

        for (int column = 1; column <= columnCount; column++) {
            columnNames.add(metaData.getColumnName(column));
        }

        Vector<Vector<Object>> data = new Vector<Vector<Object>>();
        while (rs.next()) {
            Vector<Object> vector = new Vector<Object>();
            for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                vector.add(rs.getObject(columnIndex));
            }
            data.add(vector);
        }
        return new DefaultTableModel(data, columnNames);
    }//end of method DefaultTableModel
}
