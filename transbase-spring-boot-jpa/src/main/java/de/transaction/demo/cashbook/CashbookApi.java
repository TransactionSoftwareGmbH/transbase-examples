package de.transaction.demo.cashbook;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/cashbooks")
class CashbookApi {

    @Autowired
    private CashbookService service;

    @GetMapping
    List<CashbookRecord> getCashbooks() {
        return service.list();
    }

    @PostMapping
    void create(@RequestBody CashbookRecord data) {
        service.add(data);
    }

    @PutMapping("/{id}")
    void update(@PathVariable("id") int id, @RequestBody CashbookRecord data) {
        service.update(id, data);
    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable("id") int id) {
        service.delete(id);
    }
}
