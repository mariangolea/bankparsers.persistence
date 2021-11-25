package org.mariangolea.fintrack.bank.parser.persistence.repository.companies;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mariangolea.fintrack.bank.parser.persistence.repository.BaseDataJPATest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = CompanyNameRepository.class)
public class CompanyNameRepositoryTest extends BaseDataJPATest {
	@Autowired
	private CompanyNameRepository repo;

	@Test
	public void testFindOne() {
		List<CompanyName> preexisting = repo.findAll();
		CompanyName toAdd = new CompanyName();
		toAdd.setName("Aloha");
		repo.save(toAdd);

		preexisting = repo.findAll();
		assertEquals(1, preexisting.size());

		CompanyName search = new CompanyName();
		search.setName("Aloha");
		CompanyName found = get(repo.findOne(Example.of(search)));
		assertNotNull(found);
		assertNotNull(found.getId());
		assertEquals("Aloha", found.getName());
	}

	@Test
	public void testFindById() {
		CompanyName toAdd = new CompanyName();
		toAdd.setName("Aloha");
		repo.save(toAdd);
		Long id = toAdd.getId();

		CompanyName search = new CompanyName();
		search.setName("Aloha");
		CompanyName found = get(repo.findById(id));
		assertNotNull(found);
		assertEquals(id, found.getId());
		assertEquals("Aloha", found.getName());
	}

	@Test
	public void testDelete() {
		CompanyName toAdd = new CompanyName();
		toAdd.setName("Aloha");
		repo.save(toAdd);
		repo.delete(toAdd);
		CompanyName found = get(repo.findOne(Example.of(toAdd)));
		assertNull(found);
	}
}
