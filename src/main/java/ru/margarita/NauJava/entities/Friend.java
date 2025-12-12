package ru.margarita.NauJava.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "tbl_friends")
public class Friend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private User friendUser;

    @Column
    private FriendStatus status;

    public Friend() {
    }

    public Friend(User user, User friendUser, FriendStatus status) {
        this.user = user;
        this.friendUser = friendUser;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getFriendUser() {
        return friendUser;
    }

    public void setFriendUser(User friendUser) {
        this.friendUser = friendUser;
    }

    public FriendStatus getStatus() {
        return status;
    }

    public void setStatus(FriendStatus status) {
        this.status = status;
    }
}
