package ru.gooamoko.weducation.entity;

import org.junit.Test;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

import static org.junit.Assert.*;

/**
 * Тестовый класс для проверки учетной записи.
 *
 * @author Voronin Leonid
 * @since 30.01.19
 **/
public class AccountTest {

  @Test
  public void testGetAuthoritiesReturnsAdminRole() {
    Account testAccount = new Account();
    testAccount.setRoles(AccountRole.ADMIN.name());
    Collection<? extends GrantedAuthority> authorities = testAccount.getAuthorities();
    assertNotNull(authorities);
    assertEquals(1, authorities.size());
    GrantedAuthority authority = authorities.iterator().next();
    assertEquals(AccountRole.ADMIN, AccountRole.valueOf(authority.getAuthority()));
  }
}