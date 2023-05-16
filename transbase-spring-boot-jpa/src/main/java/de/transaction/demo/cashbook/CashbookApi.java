package de.transaction.demo.cashbook;

import java.util.List;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cashbook")
class CashbookApi
{

    private final CashbookService service;

    CashbookApi(CashbookService service)
    {
        this.service = service;
    }

    @GetMapping
    public List<CashbookRecord> list()
    {
        return service.list();
    }

    @GetMapping("/{id}")
    public CashbookRecord get(@PathVariable("id") int id)
    {
        return service.get(id);
    }

    @PostMapping
    public Integer create(@RequestBody CashbookRecord data)
    {
        return service.add(data);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable("id") int id, @RequestBody CashbookRecord data)
    {
        service.update(id, data);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") int id)
    {
        service.delete(id);
    }
}
