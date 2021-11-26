package org.mariangolea.fintrack.bank.parser.persistence.users;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mariangolea.fintrack.bank.parser.persistence.BaseDataJPATest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = UserRepository.class)
public class UserRepositoryTest extends BaseDataJPATest {
	@Autowired
	private UserRepository repo;

	@Test
	public void testFindOne() {
		List<User> preexisting = repo.findAll();
		assertNotNull(preexisting);
		assertEquals(0, preexisting.size());
		
		User toAdd = new User();
		toAdd.setName("Aloha");
		repo.save(toAdd);

		preexisting = repo.findAll();
		assertEquals(1, preexisting.size());

		User search = new User();
		search.setName("Aloha");
		User found = get(repo.findOne(Example.of(search)));
		assertNotNull(found);
		assertNotNull(found.getId());
		assertEquals("Aloha", found.getName());
	}

	@Test
	public void testFindById() {
		User toAdd = new User();
		toAdd.setName("Aloha");
		repo.save(toAdd);
		Long id = toAdd.getId();

		User search = new User();
		search.setName("Aloha");
		User found = get(repo.findById(id));
		assertNotNull(found);
		assertEquals(id, found.getId());
		assertEquals("Aloha", found.getName());
	}

	@Test
	public void testDelete() {
		User toAdd = new User();
		toAdd.setName("Aloha");
		repo.save(toAdd);
		repo.delete(toAdd);
		User found = get(repo.findOne(Example.of(toAdd)));
		assertNull(found);
	}
}
