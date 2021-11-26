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

@ContextConfiguration(classes = UserPreferencesRepository.class)
public class UserPrefencesRepositoryTest extends BaseDataJPATest{
	@Autowired
	private UserPreferencesRepository repo;

	@Test
	public void testFindOne() {
		List<UserPreferences> preexisting = repo.findAll();
		assertNotNull(preexisting);
		assertEquals(0, preexisting.size());
		
		UserPreferences toAdd = new UserPreferences();
		toAdd.setInputFolder("Aloha");
		repo.save(toAdd);

		preexisting = repo.findAll();
		assertEquals(1, preexisting.size());

		UserPreferences search = new UserPreferences();
		search.setInputFolder("Aloha");
		UserPreferences found = get(repo.findOne(Example.of(search)));
		assertNotNull(found);
		assertNotNull(found.getId());
		assertEquals("Aloha", found.getInputFolder());
	}

	@Test
	public void testFindById() {
		UserPreferences toAdd = new UserPreferences();
		toAdd.setInputFolder("Aloha");
		repo.save(toAdd);
		Long id = toAdd.getId();

		UserPreferences search = new UserPreferences();
		search.setInputFolder("Aloha");
		UserPreferences found = get(repo.findById(id));
		assertNotNull(found);
		assertEquals(id, found.getId());
		assertEquals("Aloha", found.getInputFolder());
	}

	@Test
	public void testDelete() {
		UserPreferences toAdd = new UserPreferences();
		toAdd.setInputFolder("Aloha");
		repo.save(toAdd);
		repo.delete(toAdd);
		UserPreferences found = get(repo.findOne(Example.of(toAdd)));
		assertNull(found);
	}
}
