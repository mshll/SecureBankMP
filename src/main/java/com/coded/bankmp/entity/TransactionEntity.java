package com.coded.bankmp.entity;

import com.coded.bankmp.util.TransactionType;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class TransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bank_account_id")
    private BankAccountEntity bankAccount;

    private Double amount;
    private TransactionType transactionType;
    private LocalDateTime createTime;

    public TransactionEntity() {
    }

    public TransactionEntity(BankAccountEntity bankAccount, Double amount, TransactionType transactionType, LocalDateTime createTime) {
        this.bankAccount = bankAccount;
        this.amount = amount;
        this.transactionType = transactionType;
        this.createTime = createTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BankAccountEntity getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(BankAccountEntity bankAccount) {
        this.bankAccount = bankAccount;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
}
