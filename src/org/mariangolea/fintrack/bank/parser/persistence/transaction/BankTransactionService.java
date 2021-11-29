package org.mariangolea.fintrack.bank.parser.persistence.transaction;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.mariangolea.fintrack.bank.transaction.BankTransactionInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class BankTransactionService {

	@Autowired
	BankTransactionRepository repo;

	public List<BankTransactionInterface> getTransactions(Date startDate, Date endDate, int page, int pageSize) {
		List<BankTransactionInterface> transactions = new ArrayList<>();
		List<BankTransaction> dbTransactions = repo.findAllByCompletedBetween(startDate, endDate,
				PageRequest.of(page, pageSize));
		transactions.addAll(dbTransactions);
		return transactions;
	}

	public void saveAll(Collection<BankTransactionInterface> transactions) {
		List<BankTransaction> toSave = new ArrayList<>();
		for (BankTransactionInterface transaction : transactions) {
			toSave.add(new BankTransaction(transaction));
		}
		
		repo.saveAll(toSave);
	}
}
