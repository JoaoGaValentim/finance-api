package com.github.joaogavalentim.financeapi.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.github.joaogavalentim.financeapi.dto.ResponseStructure;
import com.github.joaogavalentim.financeapi.dto.ResumeResponse;
import com.github.joaogavalentim.financeapi.models.entities.Finance;
import com.github.joaogavalentim.financeapi.models.entities.enums.TypeFinance;
import com.github.joaogavalentim.financeapi.models.repositories.FinanceRepository;

@Service
public class FinanceService {
    @Autowired
    private FinanceRepository financeRepository;

    public ResponseStructure<Finance> save(Finance finance) {
        ResponseStructure<Finance> response = new ResponseStructure<>();

        if (finance.getType() == null
                || finance.getValue() == 0.0) {
            response.setData(null);
            response.setMessage("Failure to create finance.");
            response.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return response;
        }

        financeRepository.save(finance);
        response.setData(finance);
        response.setMessage("Finance created");
        response.setStatusCode(HttpStatus.CREATED.value());
        return response;
    }

    public ResponseStructure<Iterable<Finance>> getAll() {
        List<Finance> finances = StreamSupport
                .stream(financeRepository.findAll().spliterator(), false)
                .toList();

        ResponseStructure<Iterable<Finance>> response = new ResponseStructure<>();

        if (!finances.isEmpty()) {
            response.setData(finances);
            response.setMessage("found");
            response.setStatusCode(HttpStatus.OK.value());
            return response;
        }

        response.setData(null);
        response.setMessage("Finances not found");
        response.setStatusCode(HttpStatus.NOT_FOUND.value());
        return response;
    }

    public ResponseStructure<ResumeResponse> getResume() {
        double input = financeRepository.getFinanceValueOf(null);
        double output = financeRepository.getFinanceValueOf(TypeFinance.OUTPUT);

        double actualValue = input > output ? input - output : output - input;
        ResponseStructure<ResumeResponse> responseStructure = new ResponseStructure<>();
        ResumeResponse resume = new ResumeResponse(input, actualValue, output);

        responseStructure.setData(resume);
        responseStructure.setMessage("Success");
        responseStructure.setStatusCode(HttpStatus.OK.value());

        return responseStructure;
    }

    public ResponseStructure<Iterable<Finance>> getAll(int page) {
        List<Finance> finances = StreamSupport
                .stream(financeRepository.findAll(PageRequest.of(page - 1, 10)).spliterator(), false)
                .toList();

        ResponseStructure<Iterable<Finance>> response = new ResponseStructure<>();

        if (!finances.isEmpty()) {
            response.setData(finances);
            response.setMessage("found");
            response.setStatusCode(HttpStatus.OK.value());
            return response;
        }

        response.setData(null);
        response.setMessage("Finances not found");
        response.setStatusCode(HttpStatus.NOT_FOUND.value());
        return response;
    }

    public ResponseStructure<Finance> getById(Long id) {
        ResponseStructure<Finance> response = new ResponseStructure<>();

        financeRepository.findById(id).ifPresentOrElse(
                f -> {
                    response.setData(f);
                    response.setMessage("found");
                    response.setStatusCode(HttpStatus.OK.value());
                },
                () -> {
                    response.setData(null);
                    response.setMessage("Finance not found");
                    response.setStatusCode(HttpStatus.NOT_FOUND.value());
                });

        return response;
    }

    public ResponseStructure<Finance> update(Long id, Finance finance) {
        ResponseStructure<Finance> response = new ResponseStructure<>();

        if (finance.getType() == null
                || finance.getValue() == 0.0) {
            response.setData(null);
            response.setMessage("Failure to update finance.");
            response.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return response;
        }

        financeRepository.updateFinanceDetails(id, finance.getDescription(), finance.getType(), finance.getValue());
        response.setData(finance);
        response.setMessage("updated finance.");
        response.setStatusCode(HttpStatus.OK.value());
        return response;
    }

    public ResponseStructure<Finance> delete(Long id) {
        ResponseStructure<Finance> response = new ResponseStructure<>();

        Optional<Finance> financeOptional = financeRepository.findById(id);

        if (financeOptional.isEmpty()) {
            response.setData(null);
            response.setMessage("Finance not found");
            response.setStatusCode(HttpStatus.NOT_FOUND.value());
            return response;
        }

        Finance entity = financeOptional.get();
        financeRepository.delete(entity);

        response.setData(entity);
        response.setMessage("Finance deleted");
        response.setStatusCode(HttpStatus.OK.value());
        return response;
    }
}
