package com.wallet.app.dao;

import com.wallet.app.dto.Wallet;
import com.wallet.app.exception.WalletException;

import java.util.Map;

public interface WalletDao {

    Wallet addWallet(Wallet newWallet) throws WalletException;

    Wallet getWalletById(Integer walletId) throws WalletException;

    Wallet updateWallet(Wallet updateWallet) throws WalletException;

    //return boolean or which wallet(id or object) is deleted
    Wallet deleteWalletById(Integer walletId) throws WalletException;

    Map<Integer, Wallet> getWallets();

}
