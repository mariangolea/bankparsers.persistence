package org.mariangolea.fintrack.bank.parser.persistence.transactions;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.mariangolea.fintrack.bank.parser.persistence.BaseDataJPATest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = BankTransactionRepository.class)
public class BankTransactionRepositoryTest extends BaseDataJPATest{
	@Autowired
	private BankTransactionRepository repo;

	@Test
	public void testFindOne() {
		List<BankTransaction> preexisting = repo.findAll();
		BankTransaction toAdd = new BankTransaction(new BankTransactionText("Mu"));
		repo.save(toAdd);

		preexisting = repo.findAll();
		assertEquals(1, preexisting.size());

		BankTransaction search = new BankTransaction(toAdd.getOriginalContent());
		BankTransaction found = get(repo.findOne(Example.of(search, ExampleMatcher.matchingAny())));
		assertNotNull(found);
		assertNotNull(found.getId());
		assertEquals(new BankTransactionText("Mu"), found.getOriginalContent());
	}

	@Test
	public void testFindById() {
		BankTransaction toAdd = new BankTransaction(new BankTransactionText("Aloha"));
		toAdd.setDescription("Aloha");
		repo.save(toAdd);
		Long id = toAdd.getId();

		BankTransaction search = new BankTransaction(new BankTransactionText("Aloha"));
		search.setDescription("Aloha");
		BankTransaction found = get(repo.findById(id));
		assertNotNull(found);
		assertEquals(id, found.getId());
		assertEquals("Aloha", found.getDescription());
	}

	@Test
	public void testDelete() {
		BankTransaction toAdd = new BankTransaction(new BankTransactionText("Aloha"));
		toAdd.setDescription("Aloha");
		repo.save(toAdd);
		repo.delete(toAdd);
		BankTransaction found = get(repo.findOne(Example.of(toAdd)));
		assertNull(found);
	}
	
	@Test
	public void testfindAllByCompletedDateBetween() throws InterruptedException {
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
		
		List<BankTransaction> transactions = repo.findAllByCompletedBetween(startDate, endDate, PageRequest.of(0, 10));
		assertNotNull(transactions);
		assertEquals(2, transactions.size());

	}
	
}
