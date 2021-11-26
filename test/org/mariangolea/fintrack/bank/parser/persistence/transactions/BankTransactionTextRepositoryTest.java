package org.mariangolea.fintrack.bank.parser.persistence.transactions;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mariangolea.fintrack.bank.parser.persistence.BaseDataJPATest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = BankTransactionTextRepository.class)
public class BankTransactionTextRepositoryTest extends BaseDataJPATest{
	@Autowired
	private BankTransactionTextRepository repo;

	@Test
	public void testFindOne() {
		List<BankTransactionText> preexisting = repo.findAll();
		BankTransactionText toAdd = new BankTransactionText();
		toAdd.setOriginalContent("Aloha");
		repo.save(toAdd);

		preexisting = repo.findAll();
		assertEquals(1, preexisting.size());

		BankTransactionText search = new BankTransactionText();
		search.setOriginalContent("Aloha");
		BankTransactionText found = get(repo.findOne(Example.of(search)));
		assertNotNull(found);
		assertNotNull(found.getId());
		assertEquals("Aloha", found.getOriginalContent());
	}

	@Test
	public void testFindById() {
		BankTransactionText toAdd = new BankTransactionText();
		toAdd.setOriginalContent("Aloha");
		repo.save(toAdd);
		Long id = toAdd.getId();

		BankTransactionText search = new BankTransactionText();
		search.setOriginalContent("Aloha");
		BankTransactionText found = get(repo.findById(id));
		assertNotNull(found);
		assertEquals(id, found.getId());
		assertEquals("Aloha", found.getOriginalContent());
	}

	@Test
	public void testDelete() {
		BankTransactionText toAdd = new BankTransactionText();
		toAdd.setOriginalContent("Aloha");
		repo.save(toAdd);
		repo.delete(toAdd);
		BankTransactionText found = get(repo.findOne(Example.of(toAdd)));
		assertNull(found);
	}
	
}
