package pl.gabinetynagodziny.officesforrent.entity;

import lombok.Builder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class User implements UserDetails {
    //UserDetails jednej z glownych elementow spring security
    //otwarte jest na dodawanie swoich pol itd

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

   // @NotEmpty(message = "User's username cannot be empty.")
    private String username;

    //@NotEmpty(message = "User's password cannot be empty.")
    //@Size(min = 8, max = 50, message = "User's password have to be mininum 8 chars long")
    private String password;
    private String role;

   // @Email(message = "It's not an email.")
   // @NotEmpty(message = "User's email cannot be empty.")
    private String email;

    private String token;

    private Boolean Enabled;
    private Boolean Expired;
    private Boolean Locked;

    private LocalDateTime createdDate;
    private LocalDateTime lastUpdatedDate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="userId")
    private Unit unit;

    @OneToMany(fetch=FetchType.LAZY,
            mappedBy = "user",
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
            orphanRemoval = true)
    private List<Reservation> reservations;

    //dla kazdej encji ma byc bezparametrowy konstruktor
    public User(){ }

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = "USER";
        this.Enabled = false;
        this.Expired = false;
        this.Locked = false;
    }

    @PrePersist
    public void prePersist() {
        createdDate = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        lastUpdatedDate = LocalDateTime.now();
    }

    //metody z UserDetails
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
        //security spring chce liste
        //return roles.stream()
                //.map(r -> new SimpleGrantedAuthority(r))
                //.collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        if(Enabled){
            return true;
        }
        return false;
    }

    //gettery i settery


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Boolean getEnabled() {
        return Enabled;
    }

    public void setEnabled(Boolean enabled) {
        Enabled = enabled;
    }

    public Boolean getExpired() {
        return Expired;
    }

    public void setExpired(Boolean expired) {
        Expired = expired;
    }

    public Boolean getLocked() {
        return Locked;
    }

    public void setLocked(Boolean locked) {
        Locked = locked;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(LocalDateTime lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }
}
