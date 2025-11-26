package com.yougiftremake.yougift.entity;

import java.util.List;

import jakarta.persistence.*;

@Entity(name = "wishlists")
@Table(
    name = "WISHLISTS",
    uniqueConstraints = {
        @UniqueConstraint(name = "unique_wishlist_name_constraint", columnNames = {"name", "owner_id"} )
    }
)
public class Wishlist {
    @Id
    @SequenceGenerator(
        name = "wishlist_sequence",
        sequenceName = "wishlist_sequence",
        allocationSize = 1
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "wishlist_sequence"
    )
    private Long id;

    @Column(
        name = "name",
        nullable = false,
        unique = true
    )
    private String name;

    @Column(
        name = "description",
        nullable = true,
        columnDefinition = "TEXT"
    )
    private String description;

    @ManyToMany(
        fetch = FetchType.LAZY,
        cascade = CascadeType.ALL
    )
    @JoinTable(
        name = "wishlist_items",
        joinColumns = @JoinColumn(
            name = "wishlist_id",
            referencedColumnName = "id"
        ),
        inverseJoinColumns = @JoinColumn(
            name = "wishlist_item_id",
            referencedColumnName = "id"
        )
    )
    private List<WishlistItem> items;

    @ManyToOne(
        fetch = FetchType.LAZY,
        cascade = CascadeType.ALL
    )
    @JoinColumn(name = "peanut_id")
    private Peanut peanut;

    @ManyToOne(
        fetch = FetchType.LAZY,
        cascade = CascadeType.ALL
    )
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    // Constructors
    public Wishlist() {
    }

    public Wishlist(Long id, String name, String description, List<WishlistItem> items, Peanut peanut, User owner) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.items = items;
        this.peanut = peanut;
        this.owner = owner;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public List<WishlistItem> getItems() {
        return items;
    }
    public void setItems(List<WishlistItem> items) {
        this.items = items;
    }
    public Peanut getPeanut() {
        return peanut;
    }
    public void setPeanut(Peanut peanut) {
        this.peanut = peanut;
    }
    public User getOwner() {
        return owner;
    }
    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String toString () {
        return "Wishlist{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", items=" + items +
                ", peanut=" + peanut +
                ", owner=" + owner +
                '}';
    }

}
