package org.mariangolea.fintrack.bank.parser.persistence.company;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mariangolea.fintrack.bank.parser.persistence.BaseDataJPATest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = CompanyIdentifierRepository.class)
public class CompanyIdentifierRepositoryTest extends BaseDataJPATest {

	@Autowired
	private CompanyIdentifierRepository repo;

	@Test
	public void testFindOne() {
		List<CompanyIdentifier> preexisting = repo.findAll();
		CompanyIdentifier toAdd = new CompanyIdentifier();
		toAdd.setText("Aloha");
		repo.save(toAdd);

		preexisting = repo.findAll();
		assertEquals(1, preexisting.size());

		CompanyIdentifier search = new CompanyIdentifier();
		search.setText("Aloha");
		CompanyIdentifier found = get(repo.findOne(Example.of(search)));
		assertNotNull(found);
		assertNotNull(found.getId());
		assertEquals("Aloha", found.getText());
	}

	@Test
	public void testFindById() {
		CompanyIdentifier toAdd = new CompanyIdentifier();
		toAdd.setText("Aloha");
		repo.save(toAdd);
		Long id = toAdd.getId();

		CompanyIdentifier search = new CompanyIdentifier();
		search.setText("Aloha");
		CompanyIdentifier found = get(repo.findById(id));
		assertNotNull(found);
		assertEquals(id, found.getId());
		assertEquals("Aloha", found.getText());
	}

	@Test
	public void testDelete() {
		CompanyIdentifier toAdd = new CompanyIdentifier();
		toAdd.setText("Aloha");
		repo.save(toAdd);
		repo.delete(toAdd);
		CompanyIdentifier found = get(repo.findOne(Example.of(toAdd)));
		assertNull(found);
	}
	
//	@Test
//	public void testFindByTextContains() {
//		CompanyIdentifier toAdd = new CompanyIdentifier("Aloha");
//		repo.save(toAdd);
//		toAdd = new CompanyIdentifier("AlohaBis");
//		repo.save(toAdd);
//
//		Collection<CompanyIdentifier> found = repo.findAllByTextContained("ff AlohaBis Marc");
//		assertNotNull(found);
//		assertEquals(2, found.size());
//	}
}
