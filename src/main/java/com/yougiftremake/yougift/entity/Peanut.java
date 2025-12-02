package com.yougiftremake.yougift.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;

@Entity(name = "peanuts")
@Table(name = "PEANUTS")
public class Peanut {

    @Id
    @SequenceGenerator(
        name = "peanut_sequence",
        sequenceName = "peanut_sequence",
        allocationSize = 1
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "peanut_sequence"
    )
    private Long id;

    @Column(
        name = "is_distributed",
        nullable = false
    )
    private Boolean isDistributed;

    @ManyToOne(
        fetch = FetchType.LAZY,
        cascade = CascadeType.ALL
    )
    private User owner;

    @OneToMany(
        mappedBy = "peanut",
        fetch = FetchType.LAZY,
        cascade = CascadeType.ALL
    )
    private Set<Wishlist> wishlists;

    @ManyToMany(
        fetch = FetchType.LAZY,
        cascade = CascadeType.ALL
    )
    Set<User> users;

    @OneToMany(
        mappedBy = "peanut",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private Set<Dispatch> dispatchs = new HashSet<>();

    // Constructors
    public Peanut() {
    }

    public Peanut(Boolean isDistributed, User owner, Set<Wishlist> wishlists, Set<User> users, Set<Dispatch> dispatchs) {
        this.isDistributed = isDistributed;
        this.owner = owner;
        this.wishlists = wishlists;
        this.users = users;
        this.dispatchs = dispatchs;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Boolean getIsDistributed() {
        return isDistributed;
    }
    public void setIsDistributed(Boolean isDistributed) {
        this.isDistributed = isDistributed;
    }
    public User getOwner() {
        return owner;
    }
    public void setOwner(User owner) {
        this.owner = owner;
    }
    public Set<Wishlist> getWishlists() {
        return wishlists;
    }
    public void setWishlists(Set<Wishlist> wishlists) {
        this.wishlists = wishlists;
    }
    public Set<User> getUsers() {
        return users;
    }
    public void setUsers(Set<User> users) {
        this.users = users;
    }
    public Set<Dispatch> getDispatchs() {
        return dispatchs;
    }
    public void setDispatchs(Set<Dispatch> dispatchs) {
        this.dispatchs = dispatchs;
    }

    public String toString() {
        return "Peanut{" +
                "id=" + id +
                ", isDistributed=" + isDistributed +
                ", owner=" + owner +
                ", wishlists=" + wishlists +
                ", users=" + users +
                ", dispatchs=" + dispatchs +
                '}';
    }


}
