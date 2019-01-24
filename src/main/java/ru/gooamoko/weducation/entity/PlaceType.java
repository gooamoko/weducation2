package ru.gooamoko.weducation.entity;

public enum PlaceType {

    CIT("город", "г."), POS("поселок", "п."), SEL("село", "с."), PGT("поселок городского типа", "пгт."), DER("деревня", "д.");

    private String fullName;
    private String shortName;

    PlaceType(String fullName, String shortName) {
        this.fullName = fullName;
        this.shortName = shortName;
    }

    public static PlaceType forValue(final int value) {
        PlaceType result = null;
        for (PlaceType pt : PlaceType.values()) {
            if (pt.ordinal() == value) {
                result = pt;
                break;
            }
        }
        if (result != null) {
            return result;
        }
        throw new RuntimeException("Указанное значение не соответствует ни одному из вариантов перечисления!");
    }

    public String getFullName() {
        return fullName;
    }

    public String getShortName() {
        return shortName;
    }
}
