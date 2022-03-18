package com.lauvsong.carrot.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Table(name = "banks")
public class Bank {

    @Id
    @Column(name = "bank_code", length = 4)
    private String code;

    @NotNull
    private String name;

    @OneToMany(mappedBy = "bank")
    private List<Transaction> transactions = new ArrayList<>();

    //==생성 메서드==//
    public static Bank createBank(String code, String name) {
        Bank bank = new Bank();
        bank.setCode(code);
        bank.setName(name);

        return bank;
    }
}
