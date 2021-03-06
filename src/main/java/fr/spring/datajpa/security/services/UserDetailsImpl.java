package fr.spring.datajpa.security.services;

import java.util.Collection;
import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import fr.spring.datajpa.enums.Role;
import fr.spring.datajpa.model.AbstractUser;

public class UserDetailsImpl implements UserDetails {
	private static final long serialVersionUID = 1L;

	private Long id;

	private String username;

	private String name;
	private String firstName;
	private String tel;
	private String imgUrl;

	private String email;
	
	@JsonIgnore
	private String password;

	private Role role;

	public UserDetailsImpl(String email, String password, Role role, String name, String firstName, String tel, String imgUrl) {
		this.email = email;
		this.password = password;
		this.role = role;
		this.name = name;
		this.firstName = firstName;
		this.tel = tel;
		this.imgUrl = imgUrl;
		this.username = email;
	}

	public static UserDetailsImpl build(AbstractUser user) {

		return new UserDetailsImpl(
				user.getMail(),
				user.getPassword(),
				user.getRole(),
				user.getName(),
				user.getFirstName(),
				user.getTel(),
				user.getImgUrl()
				);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	public Long getId() {
		return id;
	}

	public String getEmail() {
		return email;
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
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		UserDetailsImpl user = (UserDetailsImpl) o;
		return Objects.equals(id, user.id);
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getName() {
		return name;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getTel() {
		return tel;
	}

	public String getImgUrl() {
		return imgUrl;
	}
	
}