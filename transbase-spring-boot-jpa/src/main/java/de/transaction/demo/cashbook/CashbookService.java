package de.transaction.demo.cashbook;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

class CashbookService {

    @Autowired
    private CashbookRepository repository;

    List<CashbookRecord> list() {
        return repository.findAll().stream().map(Cashbook::toRecord).toList();
    }

    void add(CashbookRecord data) {
        repository.save(new Cashbook(data));
    }

    void update(int id, CashbookRecord data) {
        repository.getReferenceById(id).update(data);
    }

    void delete(int id) {
        repository.deleteById(id);
    }
}
