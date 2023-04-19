package de.transaction.demo.cashbook;

import java.time.Instant;

public record CashbookRecord(Integer id, Instant timestamp, double amount, String comment) {

}
