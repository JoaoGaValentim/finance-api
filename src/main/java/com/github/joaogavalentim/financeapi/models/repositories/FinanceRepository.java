package com.github.joaogavalentim.financeapi.models.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.github.joaogavalentim.financeapi.models.entities.Finance;
import com.github.joaogavalentim.financeapi.models.entities.enums.TypeFinance;

import java.util.List;

@Repository
public interface FinanceRepository extends JpaRepository<Finance, Long> {
    List<Finance> findByValue(Double value);

    @Modifying
    @Transactional
    @Query("UPDATE finances f SET f.description = :description, f.type = :type, f.value = :value WHERE f.id = :id")
    void updateFinanceDetails(@Param("id") Long id,
            @Param("description") String description,
            @Param("type") TypeFinance type,
            @Param("value") Double value);

    @Query("SELECT SUM(f.value) FROM finances f WHERE f.type = :type")
    double getFinanceValueByType(@Param("type") TypeFinance typeFinance);

    default double getFinanceValueOf(@Param("type") TypeFinance typeFinance) {
        TypeFinance type = typeFinance == null ? TypeFinance.INPUT : TypeFinance.OUTPUT;
        return getFinanceValueByType(type);
    }
}
