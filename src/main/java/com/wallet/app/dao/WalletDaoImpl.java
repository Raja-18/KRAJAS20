package com.wallet.app.dao;

import com.wallet.app.dto.Wallet;
import com.wallet.app.exception.WalletException;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class WalletDaoImpl implements WalletDao{
    private Connection connection = MySqlUtility.getConnectionToMySQL();

    @Override
    public Wallet addWallet(Wallet newWallet) throws WalletException {
        String sql = "INSERT INTO wallet_datas (id,userName,userBalance,userPassword) VALUES(?,?,?,?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,newWallet.getId());
            preparedStatement.setString(2, newWallet.getName());
            preparedStatement.setDouble(3,newWallet.getBalance());
            preparedStatement.setString(4,newWallet.getPassword());
            Integer queryResult = preparedStatement.executeUpdate();
            if(queryResult != 1) System.out.println("add wallet failed!");
        }
        catch (SQLException e) {
            throw new WalletException(e.getMessage());
        }
        return newWallet;
    }

    @Override
    public Wallet getWalletById(Integer walletId) throws WalletException {
        String sql = "SELECT * FROM wallet_datas WHERE id = ?";

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,walletId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                Wallet fetchedWallet = new Wallet(resultSet.getInt("id"),resultSet.getString("userName"),
                        resultSet.getDouble("userBalance"),resultSet.getString("userPassword"));
                return (fetchedWallet);
            }
            else{
                throw new WalletException("No wallet found!");
            }

        }
        catch (Exception e){
            throw new WalletException(e.getMessage());
        }
    }

    @Override
    public Wallet updateWallet(Wallet updateWallet) throws WalletException {
        String sql = "UPDATE wallet_datas SET userName = ?, userBalance = ?, userPassword = ? WHERE id = ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(4,updateWallet.getId());
            preparedStatement.setString(1,updateWallet.getName());
            preparedStatement.setDouble(2,updateWallet.getBalance());
            preparedStatement.setString(3, updateWallet.getPassword());
            Integer queryResult = preparedStatement.executeUpdate();
            if(queryResult!=1) System.out.println("update wallet failed!");
        }
        catch (Exception e){
            throw new WalletException("Can't update wallet");
        }
        return updateWallet;
    }

    @Override
    public Wallet deleteWalletById(Integer walletId) throws WalletException {
        String sql = "DELETE FROM wallet_datas WHERE id = ?";
        Wallet toDelete = this.getWalletById(walletId);
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,walletId);
            Integer queryResult = preparedStatement.executeUpdate();
            if(queryResult!=1) System.out.println("Delete wallet failed");
        }
        catch (Exception e){
            throw new WalletException("Wallet not found to delete!");
        }
        return toDelete;
    }

    @Override
    public  Map<Integer, Wallet> getWallets() {
        String sql = "SELECT * FROM wallet_datas";
        Map<Integer,Wallet> allWallets = new HashMap<>();
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Wallet tempWallet = new Wallet(rs.getInt("id"),rs.getString("userName"),
                        rs.getDouble("userBalance"),rs.getString("userPassword"));
                allWallets.put(tempWallet.getId(),tempWallet);
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        return allWallets;
    }

    public Integer lastIdGetter(){
        String sql = "SELECT id FROM wallet_datas ORDER BY id DESC LIMIT 1";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()) return rs.getInt("id");
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}
