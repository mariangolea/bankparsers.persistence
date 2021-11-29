package org.mariangolea.fintrack.bank.parser.persistence.category;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.mariangolea.fintrack.bank.parser.persistence.BaseDataJPATest;
import org.mariangolea.fintrack.bank.parser.persistence.company.CompanyIdentifierRepository;
import org.mariangolea.fintrack.category.CategoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = { CategoriesService.class, CategoriesRepository.class,
		CompanyIdentifierRepository.class })
public class CategoryServiceTest extends BaseDataJPATest {

	@Autowired
	private CategoriesService categoriesService;

	@Autowired
	private CategoriesRepository categoriesRepo;

	@Test
	public void testGetTopMostCategories() {
		Category parent1 = new Category("AlohaParent", null);
		categoriesRepo.save(parent1);
		
		Category parent2 = new Category("AlohaParent 2", null);
		categoriesRepo.save(parent2);

		Category kid = new Category("Aloha", parent1);
		categoriesRepo.save(kid);

		Collection<CategoryInterface> topMost = categoriesService.getTopMostCategories();
		assertNotNull(topMost);
		assertEquals(2, topMost.size());
	}

	@Test
	public void testRemoveCategory() {
		Category grandParent = new Category("Aloha GrandParent", null);
		categoriesRepo.save(grandParent);

		Category parent = new Category("Aloha Parent", grandParent);
		categoriesRepo.save(parent);

		Category child = new Category("Aloha", parent);
		categoriesRepo.save(child);

		boolean success = categoriesService.removeCategory("Aloha Parent");
		assertTrue(success);
		Category found = categoriesRepo.findByName("Aloha Parent");
		assertNull(found);
		found = categoriesRepo.findByName("Aloha");
		assertEquals(grandParent, found.getParent());

		success = categoriesService.removeCategory("Aloha GrandParent");
		assertTrue(success);
		found = categoriesRepo.findByName("Aloha GrandParent");
		assertNull(found);
		found = categoriesRepo.findByName("Aloha");
		assertEquals(null, found.getParent());
	}
}
