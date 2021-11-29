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
		Category toAdd = new Category();
		toAdd.setName("AlohaParent");
		categoriesRepo.save(toAdd);
		Category parent = toAdd;

		toAdd = new Category();
		toAdd.setName("AlohaParent 2");
		categoriesRepo.save(toAdd);

		toAdd = new Category();
		toAdd.setName("Aloha");
		toAdd.setParent(parent);
		categoriesRepo.save(toAdd);

		Collection<CategoryInterface> topMost = categoriesService.getTopMostCategories();
		assertNotNull(topMost);
		assertEquals(2, topMost.size());
	}

	@Test
	public void testRemoveCategory() {
		Category toAdd = new Category("Aloha GrandParent", null);
		categoriesRepo.save(toAdd);
		Category grandParent = toAdd;

		toAdd = new Category("Aloha Parent", grandParent);
		Category parent = toAdd;
		categoriesRepo.save(toAdd);

		toAdd = new Category("Aloha", parent);
		categoriesRepo.save(toAdd);
		Category child = toAdd;
		parent.addChildrenLocal(toAdd);
		grandParent.addChildrenLocal(parent);

		boolean success = categoriesService.removeCategory("Aloha Parent");
		assertTrue(success);
		toAdd = categoriesRepo.findByName("Aloha Parent");
		assertNull(toAdd);
		toAdd = categoriesRepo.findByName("Aloha");
		assertEquals(grandParent, toAdd.getParent());

		success = categoriesService.removeCategory("Aloha GrandParent");
		assertTrue(success);
		toAdd = categoriesRepo.findByName("Aloha GrandParent");
		assertNull(toAdd);
		toAdd = categoriesRepo.findByName("Aloha");
		assertEquals(null, toAdd.getParent());
	}
}
