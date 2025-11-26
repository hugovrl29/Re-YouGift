package com.yougiftremake.yougift.entity;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.*;

@Entity(name = "users")
@Table(
    name = "USERS",
    uniqueConstraints = {
        @UniqueConstraint(name = "unique_email_constraint", columnNames = {"username", "email"})
    }
)

public class User {

    @Id
    @SequenceGenerator(
        name = "user_sequence",
        sequenceName = "user_sequence",
        allocationSize = 1
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "user_sequence"
    )
    private Long id;

    @Column(
        name = "username",
        unique = true,
        nullable = false
    )
    private String username;

    @Column(
        name = "password",
        nullable = false
    )
    private String password;

    @Column(
        name = "email",
        unique = true,
        nullable = false
    )
    private String email;

    @Column(
        name = "first_name",
        nullable = false
    )
    private String firstName;

    @Column(
        name = "last_name",
        nullable = false
    )
    private String lastName;

    @Column(
        name = "is_banned",
        nullable = false
    )
    private Boolean isBanned;

    @Column(
        name = "date_of_birth",
        nullable = false
    )
    private LocalDate dateOfBirth;

    @Column(
        name = "profile_picture_url",
        nullable = true
    )
    private String profilePictureUrl;

    @Column(
        name = "bio",
        nullable = true,
        columnDefinition = "TEXT"
    )
    private String bio;

    @ManyToMany(
        fetch = FetchType.LAZY,
        cascade = CascadeType.ALL
    )
    @JoinTable(
        name = "user_friends",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "friend_id")
    )
    private List<User> friends;

    @OneToMany(
        mappedBy = "owner",
        fetch = FetchType.LAZY,
        cascade = CascadeType.ALL
    )
    private List<Wishlist> wishlists;

    @OneToMany(
        mappedBy = "owner",
        fetch = FetchType.LAZY,
        cascade = CascadeType.ALL
    )
    private List<Peanut> peanuts;

    // Constructors
    public User() {}

    public User(
            String username,
            String password,
            String email,
            String firstName,
            String lastName,
            Boolean isBanned,
            LocalDate dateOfBirth,
            String profilePictureUrl,
            String bio,
            List<User> friends,
            List<Wishlist> wishlists,
            List<Peanut> peanuts
        ) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.isBanned = isBanned;
        this.dateOfBirth = dateOfBirth;
        this.profilePictureUrl = profilePictureUrl;
        this.bio = bio;
        this.friends = friends;
        this.wishlists = wishlists;
        this.peanuts = peanuts;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public Boolean getIsBanned() {
        return isBanned;
    }
    public void setIsBanned(Boolean isBanned) {
        this.isBanned = isBanned;
    }
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }
    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }
    public void setProfilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }
    public String getBio() {
        return bio;
    }
    public void setBio(String bio) {
        this.bio = bio;
    }
    public List<User> getFriends() {
        return friends;
    }
    public void setFriends(List<User> friendsList) {
        this.friends = friendsList;
    }
    public List<Wishlist> getWishlists() {
        return wishlists;
    }
    public void setWishlists(List<Wishlist> wishlists) {
        this.wishlists = wishlists;
    }
    public List<Peanut> getPeanuts() {
        return peanuts;
    }
    public void setPeanuts(List<Peanut> peanuts) {
        this.peanuts = peanuts;
    }

    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", isBanned=" + isBanned +
                ", dateOfBirth=" + dateOfBirth +
                ", profilePictureUrl='" + profilePictureUrl + '\'' +
                ", bio='" + bio + '\'' +
                ", friends=" + friends +
                ", wishlists=" + wishlists +
                ", peanuts=" + peanuts +
                '}';
    }

}
