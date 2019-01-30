package ru.gooamoko.weducation.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import static ru.gooamoko.weducation.utils.Utils.getHash;
import static ru.gooamoko.weducation.utils.Utils.isNotBlank;


@Entity
@Table(name = "accounts")
public class Account implements UserDetails, Serializable {

  @Id
  @Column(name = "aco_pcode")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(name = "aco_fullname", nullable = false)
  private String fullName;

  @Column(name = "aco_login", nullable = false, length = 50)
  private String login;

  @Column(name = "aco_password", nullable = false, length = 50)
  private String passwordHash;

  @Column(name = "aco_roles", nullable = false)
  private String roles;

  @Column(name = "aco_code")
  private int code;

  @Transient
  private String password;

  @Transient
  private String confirm;

  public void updatePassword() {
    if ((password != null) && (!password.isEmpty())) {
      if (password.contentEquals(confirm)) {
        passwordHash = getHash(password);
      } else {
        throw new RuntimeException("Password and confirmation doesn't match!");
      }
    } else {
      throw new RuntimeException("Empty and null passwords not accepted!");
    }
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    List<GrantedAuthority> authorities = new LinkedList<>();
    if (isNotBlank(roles)) {
      for (String role : roles.split(",")) {
        if (isNotBlank(role)) {
          authorities.add(new SimpleGrantedAuthority(role));
        }
      }
    }
    return authorities;
  }

  @Override
  public String getUsername() {
    return login;
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

  public int getId() {
    return id;
  }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public String getLogin() {
    return login;
  }

  public void setLogin(String login) {
    this.login = login;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getConfirm() {
    return confirm;
  }

  public void setConfirm(String confirm) {
    this.confirm = confirm;
  }

  public String getRoles() {
    return roles;
  }

  public void setRoles(String roles) {
    this.roles = roles;
  }

  public String getPasswordHash() {
    return passwordHash;
  }
}
