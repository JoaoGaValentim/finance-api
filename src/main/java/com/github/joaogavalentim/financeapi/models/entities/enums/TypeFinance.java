package com.github.joaogavalentim.financeapi.models.entities.enums;

public enum TypeFinance {
    INPUT("Entrada"),
    OUTPUT("Saída");

    private String type;

    private TypeFinance(String type) {
        this.type = type;
    }

    public String getTypeText() {
        return type;
    }
}
