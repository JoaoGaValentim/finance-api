package com.github.joaogavalentim.financeapi.models.entities;

import com.github.joaogavalentim.financeapi.models.entities.enums.TypeFinance;

import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Size;

@Entity(name = "finances")
public class Finance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Size(min = 10, max = 200)
    private String description;

    @Enumerated
    private TypeFinance type;

    private Double value;

    public Finance() {
    }

    public Finance(String description, TypeFinance type, Double value) {
        this.description = description;
        this.type = type;
        this.value = value;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setType(TypeFinance type) {
        this.type = type;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public TypeFinance getType() {
        return type;
    }

    public Double getValue() {
        return value;
    }
}
