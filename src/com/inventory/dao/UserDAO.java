/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inventory.dao;

import com.inventory.database.ConnectionFactory;
import com.inventory.dto.UserDTO;
import com.inventory.ui.Users;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/***
 * Refactoring name: PULL UP METHOD
 * To remove duplication of code for the method buildTableModel() in both classes UserDAO.java and SupplierDAO.java,
 * Pull up method refactoring is performed and method is pulled from both classes and is kept in the new class BuildTableModel.java class
 * The class BuildTableModel.java is then extended to two classes UserDAO.java and SupplierDAO.java.
 */
public class UserDAO extends BuildTableModel{

    Connection con = null;
    PreparedStatement pstmt = null;
    Statement stmt = null;
    ResultSet rs = null;

    public UserDAO() {
        try {
            con = new ConnectionFactory().getConnection();
            stmt = con.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addUserDAO(UserDTO userdto, String user) {
        try{
            String query = "SELECT * FROM users WHERE Nom='"+userdto.getNom()+"' AND location='"+userdto.getLocation()+"' AND phone='"+userdto.getPhone()+"' AND category='"+userdto.getCategory()+"'";
            rs=stmt.executeQuery(query);
            if(rs.next()){
                JOptionPane.showMessageDialog(null,"Le même utilisateur a déjà été ajouté!");
            }else{
                addFunction(userdto, user);
            }
        }catch(Exception e){
                e.printStackTrace();
        }   
    }//end of method addUserDTO
    
    public void addFunction(UserDTO userdto, String user){
        try{
            String username = null;
            String password = null;
            String oldUsername = null;
            String encPass=null;
            String query1="SELECT * FROM users";
            rs=stmt.executeQuery(query1);
            if(!rs.next()){
                username="user"+"1";
                password="user"+"1";
            }
            else{
                String query2="SELECT * FROM users ORDER by id DESC";
                rs=stmt.executeQuery(query2);
                if(rs.next()){
                    oldUsername=rs.getString("username");
                    Integer ucode=Integer.parseInt(oldUsername.substring(4));
                    ucode++;    
                    username="user"+ucode;
                    password="user"+ucode;
                }
                encPass=new Users().encryptPassword(password);
            }

            String query = "INSERT INTO users (Nom,location, phone, username, password, category) VALUES(?,?,?,?,?,?)";
            pstmt = (PreparedStatement) con.prepareStatement(query);
            pstmt.setString(1, userdto.getNom());
            pstmt.setString(2, userdto.getLocation());
            pstmt.setString(3, userdto.getPhone());
            pstmt.setString(4, username);
            pstmt.setString(5, encPass);
            pstmt.setString(6, userdto.getCategory());
            pstmt.executeUpdate();
            if("ADMINISTRATOR".equals(user))
                JOptionPane.showMessageDialog(null, "Nouveau ADMINISTRATEUR AJOUTÉ");
            else
                JOptionPane.showMessageDialog(null, "Nouveau UTILISATEUR NORMAL AJOUTÉ");
        }catch(Exception e){
            e.printStackTrace();
        }
                    
    }
   
    public void editUserDAO(UserDTO userdto) {
            try {
                String query = "UPDATE users SET Nom=?,location=?,phone=?,category=? WHERE username=?";
                pstmt = (PreparedStatement) con.prepareStatement(query);
                pstmt.setString(1, userdto.getNom());
                pstmt.setString(2, userdto.getLocation());
                pstmt.setString(3, userdto.getPhone());
                pstmt.setString(4, userdto.getCategory());
                pstmt.setString(5, userdto.getUsername());
                pstmt.executeUpdate();
                JOptionPane.showMessageDialog(null, "Updated");
            } catch (Exception e) {
                e.printStackTrace();
            }
    }//end of method editUserDTO
    
    public void editFunction(UserDTO userdto, String imgLink, File file){
        
        try{
            if(imgLink.equals("")) {
                    
            } else {
                    String query = "UPDATE users SET Nom=?,location=?,phone=?,username=?,password=?,category=?,image=? WHERE id=?";
                    FileInputStream fis=new FileInputStream(file);
                    pstmt = (PreparedStatement) con.prepareStatement(query);
                    pstmt.setString(1, userdto.getNom());
                    pstmt.setString(2, userdto.getLocation());
                    pstmt.setString(3, userdto.getPhone());
                    pstmt.setString(4, userdto.getUsername());
                    pstmt.setString(5, userdto.getPassword());
                    pstmt.setString(6, userdto.getCategory());
                    pstmt.setBinaryStream(7, fis,(int)file.length());
                    pstmt.setInt(8, userdto.getId());
                    pstmt.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Mis à jour");
            }
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void deleteUserDAO(String value){
        try{
            String query="delete from users where username=?";
            pstmt=con.prepareStatement(query);
            pstmt.setString(1,value);
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "supprimer");
        }catch(SQLException  e){
        }
        new Users().loadDatas();
    }

    public ResultSet getQueryResult1() {
        try {
            String query = "SELECT Nom,location,phone,username,category FROM users";
            rs = stmt.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public ResultSet getUser(String user){
        try {
            String query = "SELECT * FROM users WHERE username='"+user+"'";
            rs = stmt.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }
    
    public ResultSet getPassword(String user, String pass){
        try {
            String query = "SELECT password FROM users WHERE username='"+user+"' AND password='"+pass+"'";
            rs = stmt.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }
    
    public void changePassword(String user, String pass){
        try{
            String query="UPDATE users SET password=? WHERE username=?";
            pstmt=con.prepareStatement(query);
            String encPass=new Users().encryptPassword(pass);
            pstmt.setString(1, encPass);
            pstmt.setString(2, user);
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Mis à jour!");
        }catch(Exception e){
            e.printStackTrace();
        }
    }


}
