package pl.gabinetynagodziny.officesforrent.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
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

   // @Email(message = "It's not an email.")
   // @NotEmpty(message = "User's email cannot be empty.")
    private String email;

    private String token;

    private Boolean Enabled;
    private Boolean Expired;
    private Boolean Locked;

    private LocalDateTime createdDate;
    private LocalDateTime lastUpdatedDate;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="user_roles", joinColumns = @JoinColumn (name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    List<Role> roles;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="userId")
    private Unit unit;

    @OneToMany(fetch=FetchType.LAZY,
            mappedBy = "user",
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
            orphanRemoval = true)
    private List<Reservation> reservations;

    /*public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.roles = new HashSet<>();
        this.Enabled = false;
        this.Expired = false;
        this.Locked = false;
    }*/

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
        return roles.stream()
                .map(r -> new SimpleGrantedAuthority(r.getName()))
                .collect(Collectors.toList());
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
}
