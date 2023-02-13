package com.nhan.entity;

import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "users")
public class User implements UserDetails {
	
	private static final long serialVersionUID = 2295166099637216117L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private int userId;
	
	@Column(name = "name", columnDefinition = "varchar(50)")
	private String name;
	
	@Column(name = "email", unique = true, length = 255)
	private String email;
	
	@Column(name = "password")
	private String password;
	
	@Column(name = "status", columnDefinition = "varchar(50) default 'INACTIVE'", nullable = false)
	private String status;
	
	@Column(name = "role", columnDefinition = "varchar(20) default 'CUSTOMER'", nullable = false)
    private String role;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		String role = this.getRole();
		return List.of(new SimpleGrantedAuthority(role == null ? "CUSTOMER" : role));
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	
}
