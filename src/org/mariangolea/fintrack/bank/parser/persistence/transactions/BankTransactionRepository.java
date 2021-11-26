package org.mariangolea.fintrack.bank.parser.persistence.transactions;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankTransactionRepository extends JpaRepository<BankTransaction, Long> {

	List<BankTransaction> findAllByCompletedBetween(Date completedStart, Date completedEnd, Pageable page);
}
