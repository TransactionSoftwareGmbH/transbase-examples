package de.transaction.demo.cashbook;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
class Cashbook {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "nr")
    private Integer id;
    private Instant timestamp;
    private double amount;
    private String comment;

    protected Cashbook() {

    }

    Cashbook(CashbookRecord data) {
        update(data);
    }

    CashbookRecord toRecord() {
        return new CashbookRecord(id, timestamp, amount, comment);
    }

    void update(CashbookRecord data) {
        timestamp = data.timestamp();
        amount = data.amount();
        comment = data.comment();
    }
}
