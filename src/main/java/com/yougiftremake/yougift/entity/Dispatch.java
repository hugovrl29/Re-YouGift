package com.yougiftremake.yougift.entity;

import jakarta.persistence.*;

@Entity(name = "dispatchs")
@Table(name = "DISPATCHS")
public class Dispatch {

    @Id
    @SequenceGenerator(
        name = "dispatch_sequence",
        sequenceName = "dispatch_sequence",
        allocationSize = 1
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "dispatch_sequence"
    )
    private Long id;

    @ManyToOne(
        fetch = FetchType.LAZY
    )
    @JoinColumn(
        name = "user_giver_id",
        nullable = false
    )
    private User user;

    @ManyToOne(
        fetch = FetchType.LAZY
    )
    @JoinColumn(
        name = "wishlist_assigned_id",
        nullable = false
    )
    private Wishlist wishlist;

    @ManyToOne(
        fetch = FetchType.LAZY
    )
    @JoinColumn(
        name = "peanut_id",
        nullable = false
    )
    private Peanut peanut;

    // Constructors

    public Dispatch(){}

    public Dispatch(User user, Wishlist wishlist, Peanut peanut) {
        this.user = user;
        this.wishlist = wishlist;
        this.peanut = peanut;
    }

    // Getters and setters
    public Long getId(){
        return id;
    }
    public void setId(Long id){
        this.id = id;
    }
    public User getUser(){
        return user;
    }
    public void setUser(User user){
        this.user = user;
    }
    public Wishlist getWishlist(){
        return wishlist;
    }
    public void setWishlist(Wishlist wishlist){
        this.wishlist = wishlist;
    }
    public Peanut getPeanut(){
        return peanut;
    }
    public void setPeanut(Peanut peanut){
        this.peanut = peanut;
    }
    public String toString() {
        return "Dispatch{" +
                "i=" + id +
                ", user=" + user +
                ", wishlist=" + wishlist +
                ", peanut=" + peanut +
                '}';
    }
}
