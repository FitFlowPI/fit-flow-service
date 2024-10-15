package com.fitflow.fitflow_service.user;

import com.fitflow.fitflow_service.user.enums.Gender;
import com.fitflow.fitflow_service.user.enums.UserType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users")
public class User implements UserDetails {

    @Id
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Boolean active_plan = false;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserType user_type;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal weight;

    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal height;

    public User (
        String name,
        String email,
        String password,
        Gender gender,
        BigDecimal weight,
        BigDecimal height
    ) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.weight = weight;
        this.height = height;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();

        authorities.add(new SimpleGrantedAuthority("ROLE_STUDENT"));

        if (this.user_type == UserType.PERSONAL_TRAINER) {
            authorities.add(new SimpleGrantedAuthority("ROLE_PERSONAL_TRAINER"));
        }

        if (this.user_type == UserType.AUTO_TRAINER) {
            authorities.add(new SimpleGrantedAuthority("ROLE_AUTO_TRAINER"));
        }

        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return active_plan;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
