package com.github.joaogavalentim.financeapi.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.github.joaogavalentim.financeapi.dto.ResponseStructure;
import com.github.joaogavalentim.financeapi.models.entities.Finance;
import com.github.joaogavalentim.financeapi.services.FinanceService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
public class FinanceController {
    @Autowired
    FinanceService service;

    @GetMapping("/finance")
    public ResponseStructure<Iterable<Finance>> getAll() {
        return service.getAll();
    }

    @GetMapping("/finance/page/{page}")
    public ResponseStructure<Iterable<Finance>> getAllInPage(@PathVariable int page) {
        return service.getAll(page);
    }

    @GetMapping("/finance/{id}")
    public ResponseStructure<Finance> getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping("/finance")
    public ResponseStructure<Finance> save(@RequestBody @Valid Finance finance) {
        return service.save(finance);
    }

    @PutMapping("/finance/{id}")
    public ResponseStructure<Finance> update(@PathVariable Long id, @RequestBody @Valid Finance finance) {
        return service.update(id, finance);
    }

    @DeleteMapping("/finance/{id}")
    public ResponseStructure<Finance> delete(@PathVariable Long id) {
        return service.delete(id);
    }
}