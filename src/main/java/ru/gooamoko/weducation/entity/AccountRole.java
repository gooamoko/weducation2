package ru.gooamoko.weducation.entity;


/**
 * Перечисление ролей пользователей информационной системы.
 *
 * @author Воронин Леонид
 */
public enum AccountRole {
  ADMIN("Администратор"),
  DEPARTMENT("Отделение"),
  RECEPTION("Приемная комиссия"),
  DEPOT("Учебная часть");

  private String description;

  AccountRole(String description) {
    this.description = description;
  }

  public String getDescription() {
    return description;
  }
}
