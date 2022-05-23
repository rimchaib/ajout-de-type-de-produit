/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.inventory.dao;

import com.inventory.database.ConnectionFactory;
import com.inventory.dto.ProductDTO;
import com.inventory.ui.CurrentStocks;
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
public class ProductDAO {
    Connection con = null;
    PreparedStatement pstmt = null;
    Statement stmt = null;
    ResultSet rs1=null;
    Statement stmt1=null;
    ResultSet rs = null;

    /***
     * Refactoring name: EXTRACT CLASS
     * Extract class refactoring is implemented to remove multiple responsibilities
     * checkStock() was present in this class which is moved to a new class Stocks.java
     * Here object of new class is created and method checkStock() of Stocks.java class is called with this object.
     * This improves cohesiveness.
     */
    Stocks stocks = null;


    public ProductDAO() {
        try {
            con = new ConnectionFactory().getConnection();
            stmt = con.createStatement();
            stmt1=con.createStatement();
            stocks = new Stocks();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public ResultSet getSuppliersInfo(){
        try{
            String query="SELECT * FROM suppliers";
            rs=stmt.executeQuery(query);
        }catch(Exception e){
            e.printStackTrace();
        }
        return rs;
    }
    
    public ResultSet getCustomersInfo(){
        try{
            String query="SELECT * FROM customers";
            rs=stmt.executeQuery(query);
        }catch(Exception e){
            e.printStackTrace();
        }
        return rs;
    }
    
    public ResultSet getProductInfo(){
        try{
            String query="SELECT * FROM currentstocks";
            rs=stmt.executeQuery(query);
        }catch(Exception e){
            e.printStackTrace();
        }
        return rs;
    }
    
    public ResultSet getProductsName(){
        try{
            String query="SELECT * FROM products";
            rs=stmt.executeQuery(query);
        }catch(Exception e){
            e.printStackTrace();
        }
        return rs;
    }
    
    public Double getProductCostPrice(String codeproduitTxt){
        Double costPrice = null;
        try{
            String query="SELECT costprice FROM products WHERE codeproduit='"+codeproduitTxt+"'";
            rs=stmt.executeQuery(query);
            if(rs.next()){
                costPrice=rs.getDouble("costprice");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return costPrice;
    }
    
    public Double getProductSellingPrice(String codeproduitTxt){
        Double sellingPrice = null;
        try{
            String query="SELECT sellingprice FROM products WHERE codeproduit='"+codeproduitTxt+"'";
            rs=stmt.executeQuery(query);
            if(rs.next()){
                sellingPrice=rs.getDouble("sellingprice");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return sellingPrice;
    }
    
    
    
    String supplierCode;
    public String getSupplierCode(String suppliersName){
        try{
            String query="SELECT suppliercode FROM suppliers WHERE Marque='"+suppliersName+"'";
            rs=stmt.executeQuery(query);
            while(rs.next()){
                supplierCode=rs.getString("suppliercode");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return supplierCode;
    }
    //getcodeproduit
    
    String codeproduit;
    public String getcodeproduit(String productsName){
        try{
            String query="SELECT codeproduit FROM products WHERE Produit='"+productsName+"'";
            rs=stmt.executeQuery(query);
            while(rs.next()){
                codeproduit=rs.getString("codeproduit");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return codeproduit;
    }
    
    String codeprof;
    public String getcodeprof(String customersName){
        try{
            String query="SELECT codeprof FROM customers WHERE Nom='"+customersName+"'";
            rs=stmt.executeQuery(query);
            while(rs.next()){
                codeprof=rs.getString("codeprof");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return codeprof;
    }
    
    public void addProductDAO(ProductDTO productdto) {
         try{
                String query = "SELECT * FROM products WHERE Produit='"+productdto.getProduit()+"' AND costprice='"+productdto.getCostPrice()+"' AND sellingprice='"+productdto.getSellingPrice()+"' AND Reference='"+productdto.getReference()+"' AND Typedeproduit='"+productdto.getTypedeproduit()+"'";
                rs=stmt.executeQuery(query);
                if(rs.next()){
                    JOptionPane.showMessageDialog(null,"Le même produit a déjà été ajouté!");
                }else{
                    addFunction(productdto);
                }
        }catch(Exception e){
                e.printStackTrace();
        }
            
    }//end of method addUserDTO
    
    public void addFunction(ProductDTO productdto){
        try {
            String codeproduit = null;
            String oldcodeproduit = null;
            String query1="SELECT * FROM products";
            rs=stmt.executeQuery(query1);
            if(!rs.next()){
                    codeproduit="prod"+"1"; 
                    }
                    else{
                        String query2="SELECT * FROM products ORDER by pid DESC";
                        rs=stmt.executeQuery(query2);
                        if(rs.next()){
                            oldcodeproduit=rs.getString("codeproduit");
                            Integer pcode=Integer.parseInt(oldcodeproduit.substring(4));
                            pcode++;    
                            codeproduit="prod"+pcode;
                        }
                    }
                    String q = "INSERT INTO products VALUES(null,?,?,?,?,?,?)";
                    pstmt = (PreparedStatement) con.prepareStatement(q);
                    pstmt.setString(1, codeproduit);
                    pstmt.setString(2, productdto.getProduit());
                    pstmt.setDouble(3, productdto.getCostPrice());
                    pstmt.setDouble(4, productdto.getSellingPrice());
                    pstmt.setString(5, productdto.getReference());
                    pstmt.setString(6, productdto.getTypedeproduit());

                    pstmt.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Inséré avec succès ! Vous pouvez maintenant acheter le produit.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
    }

//    addPurchaseDAO
     public void addPurchaseDAO(ProductDTO productdto){

        try {
                    String q = "INSERT INTO purchaseinfo VALUES(null,?,?,?,?,?)";
                    pstmt = (PreparedStatement) con.prepareStatement(q);
                    pstmt.setString(1, productdto.getSupplierCode());
                    pstmt.setString(2, productdto.getcodeproduit());
                    pstmt.setString(3, productdto.getDate());
                    pstmt.setInt(4, productdto.getquantite());
                    pstmt.setDouble(5, productdto.getTotalCost());
                    pstmt.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Inséré avec succès");
                } catch (Exception e) {
                    e.printStackTrace();
                }
        
            String codeproduit=productdto.getcodeproduit();
            if(stocks.checkStock(codeproduit, stmt)==true){
                try {
                    String q = "UPDATE currentstocks SET quantite=quantite+? WHERE codeproduit=?";
                    pstmt = (PreparedStatement) con.prepareStatement(q);
                    pstmt.setDouble(1, productdto.getquantite());
                    pstmt.setString(2, productdto.getcodeproduit());

                    pstmt.executeUpdate();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else if(stocks.checkStock(codeproduit, stmt)==false){
                try{
                    String q = "INSERT INTO currentstocks VALUES(?,?)";
                    pstmt = (PreparedStatement) con.prepareStatement(q);

                    pstmt.setString(1, productdto.getcodeproduit());
                    pstmt.setInt(2, productdto.getquantite());
                    pstmt.executeUpdate();
                }catch(Exception e){
                     e.printStackTrace();   
                }
            }

         /***
          * Refactoring name: MOVE METHOD
          * Move method refactoring is implemented to improve cohesion and reduce coupling
          * deleteStock() was present in this class which is moved to other class Stocks.java
          */
            stocks.deleteStock(stmt);
     }
    
    public void editProductDAO(ProductDTO productdto) {
        try {
                String query = "UPDATE products SET Produit=?,costprice=?,sellingprice=?,Reference=?,Typedeproduit=? WHERE codeproduit=?";
                pstmt = (PreparedStatement) con.prepareStatement(query);
                pstmt.setString(1, productdto.getProduit());
                pstmt.setDouble(2, productdto.getCostPrice());
                pstmt.setDouble(3, productdto.getSellingPrice());
                pstmt.setString(4, productdto.getReference());
                pstmt.setString(5, productdto.getTypedeproduit());
                pstmt.setString(6, productdto.getcodeproduit());
                pstmt.executeUpdate();
                JOptionPane.showMessageDialog(null, "Mis à jour avec succés");
            } catch (Exception e) {
                e.printStackTrace();
            }  
       
    }//end of method editUserDTO
    
   
 
    
    public void editStock(String val,int q){
        try{
            String query="SELECT * FROM currentstocks WHERE codeproduit = '"+val+"'";
            rs=stmt.executeQuery(query);
            if(rs.next()){
                String qry = "UPDATE currentstocks SET quantite=quantite-? WHERE codeproduit=?";
                pstmt = (PreparedStatement) con.prepareStatement(qry);
                pstmt.setDouble(1, q);
                pstmt.setString(2, val);
                pstmt.executeUpdate();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void editSoldStock(String val,int q){
        try{
            String query="SELECT * FROM currentstocks WHERE codeproduit = '"+val+"'";
            rs=stmt.executeQuery(query);
            if(rs.next()){
                String qry = "UPDATE currentstocks SET quantite=quantite+? WHERE codeproduit=?";
                pstmt = (PreparedStatement) con.prepareStatement(qry);
                pstmt.setDouble(1, q);
                pstmt.setString(2, val);
                pstmt.executeUpdate();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void deleteProductDAO(String value){
        try{
            String query="delete from products where codeproduit=?";
            pstmt=con.prepareStatement(query);
            pstmt.setString(1,value);
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "vous etes sur de supprimer..");
        }catch(SQLException  e){
            e.printStackTrace();
        }
        stocks.deleteStock(stmt);
    }
    
    
    public void deletePurchaseDAO(String value){
        try{
            String query="delete from purchaseinfo where purchaseid=?";
            pstmt=con.prepareStatement(query);
            pstmt.setString(1,value);
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "vous etes sur de supprimer..");
        }catch(SQLException  e){
            e.printStackTrace();
        }
        stocks.deleteStock(stmt);
    }
    
    public void deleteSalesDAO(String value){
        try{
            String query="delete from salesreport where salesid=?";
            pstmt=con.prepareStatement(query);
            pstmt.setString(1,value);
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "vous etes sur de supprimer..");
        }catch(SQLException  e){
            e.printStackTrace();
        }
        stocks.deleteStock(stmt);
    }
    
    public void sellProductDAO(ProductDTO productDTO,String username){
        int quantite=0;
        String sellDate=productDTO.getSellDate();
        String codeproduit=productDTO.getcodeproduit();
        String customersCode=productDTO.getcodeprof();
        Double sellingPrice=productDTO.getSellingPrice();
        Double totalRevenue=productDTO.getTotalRevenue();
        int qty=productDTO.getquantite();
        try{
            String query="SELECT * FROM currentstocks WHERE codeproduit='"+productDTO.getcodeproduit()+"'";
            rs=stmt.executeQuery(query);
            while(rs.next()){
                codeproduit=rs.getString("codeproduit");
                quantite=rs.getInt("quantite");
            }
            if(productDTO.getquantite()>quantite){
                JOptionPane.showMessageDialog(null,"quantité Insuffisante");
            }else if(productDTO.getquantite()<=0){
                JOptionPane.showMessageDialog(null,"Quantité invalide");
            }else{
                try{
                    String q="UPDATE currentstocks SET quantite=quantite-'"+productDTO.getquantite()+"' WHERE codeproduit='"+productDTO.getcodeproduit()+"'";
                    String qry="INSERT INTO salesreport(date,codeproduit,codeprof,quantite,revenue,soldby) VALUES('"+sellDate+"','"+codeproduit+"','"+customersCode+"','"+qty+"','"+totalRevenue+"','"+username+"')";
                    stmt.executeUpdate(q);
                    stmt.executeUpdate(qry);
                    JOptionPane.showMessageDialog(null,"VENDU AVEC SUCCÈS");
                 }catch(Exception e){
                    e.printStackTrace();
                 }
             }
         }catch(Exception e){
                e.printStackTrace();
         } 
    }

    public ResultSet getQueryResult() {
        try {
            String query = "SELECT codeproduit,Produit,costprice,sellingprice,Reference,Typedeproduit FROM products ORDER BY pid";
            rs = stmt.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }//end of method getQueryResult
   
    public ResultSet getPurchaseResult() {
        try {
            String query = "SELECT purchaseid,purchaseinfo.codeproduit,Produit,quantite,totalcost FROM purchaseinfo INNER JOIN products ON products.codeproduit=purchaseinfo.codeproduit ORDER BY purchaseid";
            rs = stmt.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }//end of method getQueryResult
    
    public ResultSet getQueryResultOfCurrentStocks() {
        try {
            String query = "SELECT currentstocks.codeproduit,products.Produit,currentstocks.quantite,products.costprice,products.sellingprice FROM currentstocks INNER JOIN products ON currentstocks.codeproduit=products.codeproduit";
            rs = stmt.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }//end of method getQueryResult
    
    public ResultSet getSalesReportQueryResult() {
        try {
            String query = "SELECT salesid,salesreport.codeproduit,Produit,salesreport.quantite,revenue,soldby FROM salesreport INNER JOIN products ON salesreport.codeproduit=products.codeproduit";
            rs = stmt.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }//end of method getQueryResult
    
     public ResultSet getSearchProductsQueryResult(String searchTxt) {
        try {
            String query = "SELECT pid,codeproduit,Produit,costprice,sellingprice,Reference,Typedeproduit FROM products WHERE Produit LIKE '%"+searchTxt+"%' OR Reference LIKE '%"+searchTxt+"%' OR Typedeproduit LIKE '%"+searchTxt+"%' OR codeproduit LIKE '%"+searchTxt+"%'";
            rs = stmt.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }
     
     public ResultSet getSearchPurchaseQueryResult(String searchTxt) {
        try {
            String query = "SELECT purchaseid,purchaseinfo.codeproduit,Produit,quantite,totalcost FROM purchaseinfo INNER JOIN products ON products.codeproduit=purchaseinfo.codeproduit WHERE purchaseinfo.codeproduit LIKE '%"+searchTxt+"%' OR Produit LIKE '%"+searchTxt+"%' ORDER BY purchaseid";
            rs = stmt.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }
     
   public ResultSet getSearchSalesQueryResult(String searchTxt) {
        try {
            String query = "SELECT salesid,salesreport.codeproduit,Produit,quantite,revenue,soldby FROM salesreport INNER JOIN products ON products.codeproduit=salesreport.codeproduit INNER JOIN customers ON customers.codeprof=salesreport.codeprof WHERE salesreport.codeproduit LIKE '%"+searchTxt+"%' OR Produit LIKE '%"+searchTxt+"%' OR soldby LIKE '%"+searchTxt+"%' OR Nom LIKE '%"+searchTxt+"%' ORDER BY salesid";
            rs = stmt.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }
     
    public ResultSet getProduit(String pcode){
        try {
            String query = "SELECT Produit FROM products WHERE codeproduit='"+pcode+"'";
            rs = stmt.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }
    
    public String getProductsSupplier(int id){
        String sup=null;
        try {
            String query = "SELECT Marque FROM suppliers INNER JOIN purchaseinfo ON suppliers.suppliercode=purchaseinfo.suppliercode WHERE purchaseid='"+id+"'";
            rs = stmt.executeQuery(query);
            if(rs.next()){
                sup=rs.getString("Marque");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sup;
    }
    
    public String getProductsCustomer(int id){
        String cus=null;
        try {
            String query = "SELECT Nom FROM customers INNER JOIN salesreport ON customers.codeprof=salesreport.codeprof WHERE salesid='"+id+"'";
            rs = stmt.executeQuery(query);
            if(rs.next()){
                cus=rs.getString("Nom");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cus;
    }
     
    
    public String getPurchasedDate(int pur){
        String p=null;
        try {
            String query = "SELECT date FROM purchaseinfo WHERE purchaseid='"+pur+"'";
            rs = stmt.executeQuery(query);
            if(rs.next()){
                p=rs.getString("date");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return p;
    }
    
    public String getSoldDate(int salesid){
        String p=null;
        try {
            String query = "SELECT date FROM salesreport WHERE salesid='"+salesid+"'";
            rs = stmt.executeQuery(query);
            if(rs.next()){
                p=rs.getString("date");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return p;
    }

    /***
     * Refactoring name: EXTRACT METHOD
     * To remove multiple responsibilities: Extracted code block from buildTableModel() and,
     * Created two new methods getColumnNames() and tableModel() and,
     * Passed appropriate variables and return appropriate values
     */
    public DefaultTableModel buildTableModel(ResultSet rs) throws SQLException {
        Vector<String> columnNames = getColumnNames(rs);
        Vector<Vector<Object>> data = tableModel(rs, columnNames);

        return new DefaultTableModel(data, columnNames);
    }//end of method DefaultTableModel


    public Vector<String> getColumnNames(ResultSet rs) throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData(); //resultset ko metadata
        Vector<String> columnNames = new Vector<String>();
        int columnCount = metaData.getColumnCount();

        for (int column = 1; column <= columnCount; column++) {
            columnNames.add(metaData.getColumnName(column));
        }

        return columnNames;
    }

    public Vector<Vector<Object>> tableModel(ResultSet rs, Vector<String> columnNames) throws SQLException {
        int columnCount = columnNames.size();
        Vector<Vector<Object>> data = new Vector<Vector<Object>>();

        while (rs.next()) {
            Vector<Object> vector = new Vector<Object>();
            for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                vector.add(rs.getObject(columnIndex));
            }
            data.add(vector);
        }
        return data;
    }
}
