package ru.gooamoko.weducation.utils;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Comment here
 *
 * @author Voronin Leonid
 * @since 30.01.19
 **/
public class UtilsTest {

  @Test
  public void testIsBlank() {
    assertTrue(Utils.isBlank(null));
    assertTrue(Utils.isBlank(""));
    assertTrue(Utils.isBlank(" "));
    assertTrue(Utils.isBlank("    "));
  }

  @Test
  public void testIsNotBlank() {
    assertFalse(Utils.isBlank("1"));
    assertFalse(Utils.isBlank("."));
  }
}