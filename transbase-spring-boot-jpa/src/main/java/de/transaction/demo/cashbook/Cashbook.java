package de.transaction.demo.cashbook;

import java.time.Instant;

import jakarta.persistence.*;

@Entity
class Cashbook
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "nr")
    private Integer id;

    private Instant date;
    private double amount;
    private String comment;

    public Integer getId()
    {
        return id;
    }

    public Instant getDate()
    {
        return date;
    }

    public double getAmount()
    {
        return amount;
    }

    public String getComment()
    {
        return comment;
    }

    Cashbook()
    {

    }

    Cashbook(CashbookRecord data)
    {
        update(data);
    }

    CashbookRecord toRecord()
    {
        return new CashbookRecord(id, date, amount, comment);
    }

    void update(CashbookRecord data)
    {
        date = data.timestamp();
        amount = data.amount();
        comment = data.comment();
    }
}
