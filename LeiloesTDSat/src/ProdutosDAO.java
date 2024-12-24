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
      String sql = "SELECT * FROM produtos";  // SQL para buscar todos os produtos
        ArrayList<ProdutosDTO> listagem = new ArrayList<>();  // Lista para armazenar os produtos
        
        try (Connection conn = new conectaDAO().connectDB(); 
             PreparedStatement prep = conn.prepareStatement(sql); 
             ResultSet resultSet = prep.executeQuery()) {
            
            // Verifica se a conexão foi bem-sucedida
            if (conn != null) {
                // Processa os resultados da consulta
                while (resultSet.next()) {
                    // Cria um objeto ProdutoDTO para armazenar os dados do produto
                    ProdutosDTO produto = new ProdutosDTO();
                    produto.setId(resultSet.getInt("id"));
                    produto.setNome(resultSet.getString("nome"));
                    produto.setValor(resultSet.getInt("valor"));
                    produto.setStatus(resultSet.getString("status"));
                    
                    // Adiciona o produto à lista
                    listagem.add(produto);
                }
            }
        } catch (SQLException e) {
            // Exibe uma mensagem de erro em caso de exceção
            JOptionPane.showMessageDialog(null, "Erro ao listar produtos: " + e.getMessage());
        }

        // Retorna a lista de produtos
        return listagem;
    }       
}