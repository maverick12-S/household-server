package com.example.household_server.application.dto;

public class BalanceDto {
    private int income;
    private int expense;
    private int balance;

    public BalanceDto(int income, int expense, int balance) {
        this.income = income;
        this.expense = expense;
        this.balance = balance;
    }

    public int getIncome() {
        return income;
    }

    public void setIncome(int income) {
        this.income = income;
    }

    public int getExpense() {
        return expense;
    }

    public void setExpense(int expense) {
        this.expense = expense;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

}
