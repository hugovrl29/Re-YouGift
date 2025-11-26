package com.yougiftremake.yougift.entity;

import java.util.List;

import jakarta.persistence.*;

@Entity(name = "wishlist_items")
@Table(
    name = "WISHLIST_ITEMS",
    uniqueConstraints = {
        @UniqueConstraint(name = "unique_name_constraint", columnNames = {"name"})
    }
)
public class WishlistItem {

    @Id
    @SequenceGenerator(
        name = "wishlist_item_sequence",
        sequenceName = "wishlist_item_sequence",
        allocationSize = 1
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "wishlist_item_sequence"
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
        mappedBy = "items",
        fetch = FetchType.LAZY,
        cascade = CascadeType.ALL
    )
    private     List<Wishlist> wishlists;

    // Constructors
    public WishlistItem() {
    }

    public WishlistItem(Long id, String name, String description, List<Wishlist> wishlists) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.wishlists = wishlists;
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
    public List<Wishlist> getWishlists() {
        return wishlists;
    }
    public void setWishlists(List<Wishlist> wishlists) {
        this.wishlists = wishlists;
    }

    public String toString() {
        return "WishlistItem{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", wishlists=" + wishlists +
                '}';
    }

}
