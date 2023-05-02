package de.transaction.demo.cashbook;

import org.springframework.data.jpa.repository.JpaRepository;

interface CashbookRepository extends JpaRepository<Cashbook, Integer>
{

}
