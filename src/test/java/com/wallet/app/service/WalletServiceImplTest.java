package com.wallet.app.service;

import com.wallet.app.controller.IdGenerator;
import com.wallet.app.dto.Wallet;
import com.wallet.app.exception.WalletException;
import org.junit.jupiter.api.*;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class WalletServiceImplTest {
    public static IdGenerator idGenerator = new IdGenerator();
    public static WalletService testWalletService = new WalletServiceImpl();

    public Wallet testWallet1 = new Wallet(111, "TestUser1",4000.0,"testpass1");
    public Wallet testWallet2 = new Wallet(112, "TestUser2",2500.0,"testpass2");

    @Test
    @Order(1)
    public void testRegisterWallet(){
        try{
            assertEquals(testWallet1,testWalletService.registerWallet(testWallet1));                  // register new
            assertThrows(WalletException.class,()->testWalletService.registerWallet(testWallet1));   // register same(again)
        } catch (WalletException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Order(2)
    public void testLogin(){
        try {
            assertTrue(testWalletService.login(testWallet1.getId(),testWallet1.getPassword()));
            assertFalse(testWalletService.login(testWallet1.getId(), "wrongPassword"));
            assertThrows(WalletException.class,()->testWalletService.login(testWallet2.getId(), testWallet2.getPassword()));    //testWallet2 is still not registered
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @Order(3)
    public void testAddFundsToWallet(){
        testWallet1 = testWalletService.getAllWallets().get(testWallet1.getId());
        Double testAmount = testWallet1.getBalance();       // amount before adding funds to the account
        try {
            assertEquals(testAmount+100.0,testWalletService.addFundsToWallet(testWallet1.getId(),100.0));
        } catch (WalletException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Order(4)
    public void testShowWalletBalance() {
        testWallet1 = testWalletService.getAllWallets().get(testWallet1.getId());
        try {
            assertEquals(testWallet1.getBalance(),testWalletService.showWalletBalance(testWallet1.getId()));
            assertThrows(WalletException.class,()-> testWalletService.showWalletBalance(200));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    @Order(5)
    public void testFundTransfer(){
        try {
            testWalletService.registerWallet(testWallet2);     // registering testWallet2
            testWallet1 = testWalletService.getAllWallets().get(testWallet1.getId());
            testWallet2 = testWalletService.getAllWallets().get(testWallet2.getId());
            Double testWalletAmount1 = testWallet1.getBalance();
            Double testWalletAmount2 = testWallet2.getBalance();

            assertEquals(testWalletAmount1-500.0,testWalletService.fundTransfer(testWallet1.getId(),testWallet2.getId(),500.0));
            assertEquals(testWalletAmount2+500.0,testWalletService.showWalletBalance(testWallet2.getId()));
        } catch (WalletException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Order(6)
    public void testUnregisterWallet(){
        try {
            assertThrows(WalletException.class, ()->testWalletService.unRegisterWallet(testWallet1.getId(), "worngTestPassword"));
            assertEquals(testWallet1.getId(),testWalletService.unRegisterWallet(testWallet1.getId(), testWallet1.getPassword()).getId());
        } catch (WalletException e) {
            e.printStackTrace();
        }
    }

    @Test
    @Order(7)
    public void testWithdrawFunds(){
        testWallet2 = testWalletService.getAllWallets().get(testWallet2.getId());
        Double testWalletBalance = testWallet2.getBalance();
        try {
            assertThrows(WalletException.class,()->testWalletService.withdrawFunds(testWallet2.getId(), 100000.0));
            assertEquals(testWalletBalance-500.0,testWalletService.withdrawFunds(testWallet2.getId(), 500.0));
        } catch (WalletException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Order(8)
    public void testGetAllWallets(){

        Wallet testWallet3 = new Wallet(113, "testUser3",3000.0,"testPass3");

        try {
            testWalletService.registerWallet(testWallet3);
            Map<Integer,Wallet> testWalletRepo = testWalletService.getAllWallets();
            assertEquals(testWallet3,testWalletRepo.get(testWallet3.getId()));

            testWalletService.unRegisterWallet(testWallet3.getId(), testWallet3.getPassword());
            testWalletRepo = testWalletService.getAllWallets();
            assertFalse(testWalletRepo.containsKey(testWallet3.getId()));
        } catch (WalletException e) {
            e.printStackTrace();
        }

    }

}