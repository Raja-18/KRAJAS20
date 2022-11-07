package com.wallet.app.dto;

import java.util.Objects;

public class Wallet {
    private Integer id;
    private String name;
    private Double balance;
    private String password;

    public Wallet(Integer id, String name, Double balance, String password) {
        this.id = id;
        this.name = name;
        this.balance = balance;
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Wallet wallet = (Wallet) o;
        return Objects.equals(id, wallet.id) && Objects.equals(name, wallet.name) && Objects.equals(balance, wallet.balance) && Objects.equals(password, wallet.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, balance, password);
    }

    @Override
    public String toString() {
        return "Wallet{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", balance=" + balance +
                ", password='" + password + '\'' +
                '}';
    }
}
