package com.momchil.TU4ALL.dbo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;

@Entity
@Table(name = "FRIENDS")
@Getter
@Setter
@RequiredArgsConstructor
public class FriendDBO {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ID")
    private long id;

    @Column(name = "ALIAS")
    private String alias;

    @Column(name = "status")
    private long status;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "USER_ID")
    @JsonIgnore
    private UserDBO user;

}
