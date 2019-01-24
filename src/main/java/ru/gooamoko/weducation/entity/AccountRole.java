package ru.gooamoko.weducation.entity;


/**
 * Перечисление ролей пользователей информационной системы.
 *
 * @author Воронин Леонид
 */
public enum AccountRole {
    // Добавляйте роли в конец списка!
    ADMIN, DEPARTMENT, RECEPTION, DEPOT;

    private static final String[] descriptions = {
            "Администратор",
            "Отделение",
            "Приемная комиссия",
            "Учебная часть"
            // Добавляйте сюда описания для всех новых значений перечисления
    };

    public String getDescription() {
        try {
            return descriptions[this.ordinal()];
        } catch (IndexOutOfBoundsException e) {
            throw new RuntimeException("Количество значений перечисления не соответствует количеству описаний!");
        }
    }
}
