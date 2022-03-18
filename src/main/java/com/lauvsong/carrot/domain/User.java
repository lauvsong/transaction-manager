package com.lauvsong.carrot.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Table(name = "users")
public class User {

    @Id
    @Column(name = "user_id")
    private Long id;

    @OneToMany(mappedBy = "user")
    private List<Transaction> transactions = new ArrayList<>();

    //==생성 메서드==//
    public static User createUser(Long id) {
        User user = new User();
        user.setId(id);

        return user;
    }
}
