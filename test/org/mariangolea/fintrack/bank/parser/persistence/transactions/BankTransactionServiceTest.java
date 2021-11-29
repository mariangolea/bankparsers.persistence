package org.mariangolea.fintrack.bank.parser.persistence.transactions;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.mariangolea.fintrack.bank.parser.persistence.BaseDataJPATest;
import org.mariangolea.fintrack.bank.parser.persistence.transaction.BankTransaction;
import org.mariangolea.fintrack.bank.parser.persistence.transaction.BankTransactionRepository;
import org.mariangolea.fintrack.bank.parser.persistence.transaction.BankTransactionService;
import org.mariangolea.fintrack.bank.parser.persistence.transaction.BankTransactionText;
import org.mariangolea.fintrack.bank.transaction.BankTransactionInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = {BankTransactionService.class, BankTransactionRepository.class})
public class BankTransactionServiceTest extends BaseDataJPATest{

	@Autowired
	private BankTransactionService service;
	
	@Autowired
	private BankTransactionRepository repo;
	
	@Test
	public void testGetAllCompanyIdentifierStrings() throws InterruptedException {
		
		List<BankTransactionInterface> preexisting = service.getTransactions(new Date(1L), new Date(), 1, 20);
		assertNotNull(preexisting);
		assertTrue(preexisting.isEmpty());

		BankTransaction irrellevantOne = new BankTransaction(new BankTransactionText("Aloha"));
		repo.save(irrellevantOne);
		
		TimeUnit.MILLISECONDS.sleep(10);
		Date startDate = new Date();
		TimeUnit.MILLISECONDS.sleep(10);

		BankTransaction one = new BankTransaction(new BankTransactionText("One"));
		repo.save(one);
		BankTransaction two = new BankTransaction(new BankTransactionText("Two"));
		repo.save(two);
		
		TimeUnit.MILLISECONDS.sleep(10);
		Date endDate = new Date();
		TimeUnit.MILLISECONDS.sleep(10);
		
		BankTransaction irrellevantTwo = new BankTransaction(new BankTransactionText("Three"));
		repo.save(irrellevantTwo);
		
		List<BankTransactionInterface> transactions = service.getTransactions(startDate, endDate, 0, 10);
		assertNotNull(transactions);
		assertEquals(2, transactions.size());
	}
}
