package de.transaction.demo.cashbook;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
class CashbookService
{
    private final CashbookRepository repository;

    CashbookService(CashbookRepository repository)
    {
        this.repository = repository;
    }

    public List<CashbookRecord> list()
    {
        return repository.findAll().stream().map(Cashbook::toRecord).toList();
    }

    public CashbookRecord get(int id)
    {
        return repository.getReferenceById(id).toRecord();
    }

    public Integer add(CashbookRecord data)
    {
        return repository.save(new Cashbook(data)).getId();
    }

    public void update(int id, CashbookRecord data)
    {
        repository.getReferenceById(id).update(data);
    }

    public void delete(int id)
    {
        repository.deleteById(id);
    }

}
