package org.mariangolea.fintrack.bank.parser.persistence.repository.category;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mariangolea.fintrack.bank.parser.persistence.repository.BaseDataJPATest;
import org.mariangolea.fintrack.bank.parser.persistence.repository.categories.CategoriesRepository;
import org.mariangolea.fintrack.bank.parser.persistence.repository.categories.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = CategoriesRepository.class)
public class CategoryRepositoryTest extends BaseDataJPATest {

	@Autowired
	private CategoriesRepository categoriesRepository;

	@Test
	public void testFindOne() {
		List<Category> preexisting = categoriesRepository.findAll();
		Category toAdd = new Category();
		toAdd.setName("Aloha");
		categoriesRepository.save(toAdd);

		preexisting = categoriesRepository.findAll();
		assertEquals(1, preexisting.size());

		Category search = new Category();
		search.setName("Aloha");
		Category found = get(categoriesRepository.findOne(Example.of(search)));
		assertNotNull(found);
		assertNotNull(found.getId());
		assertEquals("Aloha", found.getName());
	}

	@Test
	public void testFindById() {
		Category toAdd = new Category();
		toAdd.setName("Aloha");
		categoriesRepository.save(toAdd);
		Long id = toAdd.getId();

		Category search = new Category();
		search.setName("Aloha");
		Category found = get(categoriesRepository.findById(id));
		assertNotNull(found);
		assertEquals(id, found.getId());
		assertEquals("Aloha", found.getName());
	}

	@Test
	public void testDelete() {
		Category toAdd = new Category();
		toAdd.setName("Aloha");
		categoriesRepository.save(toAdd);
		categoriesRepository.delete(toAdd);
		Category found = get(categoriesRepository.findOne(Example.of(toAdd)));
		assertNull(found);
	}

	@Test
	public void testFindByParent() {
		Collection<Category> search = categoriesRepository.findByParent(null);
		assertTrue(search.isEmpty());
		Category toAdd = new Category();
		toAdd.setName("Aloha");
		categoriesRepository.save(toAdd);
		Long id = toAdd.getId();

		toAdd = new Category();
		toAdd.setName("AlohaChild");
		toAdd.setParent(id);
		categoriesRepository.save(toAdd);

		search = categoriesRepository.findByParent(id);
		assertEquals(1, search.size());
		Category first = search.iterator().next();
		assertEquals("AlohaChild", first.getName());
	}
	
	@Test
	public void testFindByName() {
		Category search = categoriesRepository.findByName(null);
		assertNull(search);
		Category toAdd = new Category();
		toAdd.setName("Aloha");
		categoriesRepository.save(toAdd);

		search = categoriesRepository.findByName("Aloha");
		assertNotNull(search);
		assertEquals("Aloha", search.getName());
	}
}
