/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Adm
 */

import java.sql.*;
import java.sql.PreparedStatement;
import java.sql.Connection;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ProdutosDAO {
    
    Connection        conn;
    PreparedStatement prep;
    ResultSet         resultset;
    ArrayList<ProdutosDTO> listagem = new ArrayList<>();
    
    public void cadastrarProduto (ProdutosDTO produto){
       String sql = "INSERT INTO produtos (nome, valor, status) VALUES (?, ?, ?)";
        
        try {
            conn = new conectaDAO().connectDB();
            
            if (conn != null) {
                prep = conn.prepareStatement(sql);
                prep.setString(1, produto.getNome());
                prep.setInt(2, produto.getValor());
                prep.setString(3, produto.getStatus());
                
                int rowsAffected = prep.executeUpdate();
                
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(null, "Produto cadastrado com sucesso!");
                } 
                else {
                    JOptionPane.showMessageDialog(null, "Erro ao cadastrar o produto.");
                }
            }
        } 
        catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar produto: " + e.getMessage());
        } 
        finally {
            try {
                if (prep != null) prep.close();
                if (conn != null) conn.close();
            } 
            catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Erro ao fechar a conexão: " + e.getMessage());
            }
        }
    }
    
    public ArrayList<ProdutosDTO> listarProdutos(){   
        return listagem;
    }       
}