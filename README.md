# YouGift — Backend API

YouGift is a Spring Boot backend designed for a gift-exchange platform. The system allows users to create an account, build wishlists, join groups (called "Peanuts"), and participate in automated Secret-Santa style assignment.

The project demonstrates backend design principles including REST architecture, persistence layers, data modeling, validation, and testable domain logic (ongoing).

---

## Features

- User registration and profile management  
- Friendship system (add, remove, list)
- Wishlists with reusable wishlist items
- Group system ("Peanuts") with:
  - Join/leave logic
  - Linking wishlists to participants (1 to 1)
  - Random assignment algorithm for gift matching (shifting)
- DTO-based request/response structure
- Layered architecture (Controller → Service → Repository → Entity)

---

## Tech Stack

- Java 25  
- Spring Boot 4  
- Spring MVC  
- Spring Data JPA  
- PostgreSQL  
- Maven  
- JUnit, Mockito, MockMvc for testing  (TODO)

---

## Directory Structure
src/
 └─ main/
     └─ java/com/yougiftremake/yougift
         ├─ YouGiftApplication.java
         ├─ controller
         │    ├─ authentication
         |    |   └─ AuthController.java
         │    ├─ user
         |    |   └─ UserController.java
         │    ├─ profile
         |    |   └─ ProfileController.java
         │    ├─ wishlist
         |    |   └─ WishlistController.java
         │    ├─ wishlistitem
         |    |   └─ WishlistItemController.java
         │    └─ peanut
         |        └─ PeanutController.java
         ├─ dto
         │    ├─ user
         │    │    ├─ UserCreateRequest.java
         │    │    ├─ UserUpdateRequest.java
         │    │    └─ UserResponse.java
         │    ├─ wishlist
         │    │    ├─ WishlistCreateRequest.java
         │    │    ├─ WishlistUpdateRequest.java
         │    │    └─ WishlistResponse.java
         │    ├─ wishlistitem
         │    │    ├─ WishlistItemCreateRequest.java
         │    │    ├─ WishlistItemUpdateRequest.java
         │    │    └─ WishlistItemResponse.java
         │    ├─ peanut
         │    │    ├─ PeanutCreateRequest.java
         │    │    ├─ PeanutUpdateRequest.java
         │    │    └─ PeanutResponse.java
         |    └─ authentication
         │         ├─ LoginRequest.java
         │         └─ LoginResponse.java
         ├─ entity
         │    ├─ User.java
         │    ├─ Wishlist.java
         │    ├─ WishlistItem.java
         │    └─ Peanut.java
         ├─ repository
         │    ├─ user
         |    |   └─ UserRepository.java
         │    ├─ wishlist
         |    |   └─ WishlistRepository.java
         │    ├─ wishlistitem
         |    |   └─ WishlistItemRepository.java
         │    └─ peanut
         |        └─ PeanutRepository.java
         ├─ service
         │    ├─ authentication
         |    |   └─ AuthService.java
         │    ├─ user
         |    |   └─ UserService.java
         │    ├─ wishlist
         |    |   └─ WishlistService.java
         │    ├─ wishlistitem
         |    |   └─ WishlistItemService.java
         │    └─ peanut
         |        └─ PeanutService.java
         (todo)
         └─ exception
              └─ Exceptions...


Tests follow the same structure under `src/test/java/com/yougiftremake/yougift`. (todo)

---

## Running the Project

1. Clone the repository  
2. Configure your database credentials in `application.properties`  
3. Run: `mvn spring-boot:run`
The backend runs at: http://localhost:8080
