package org.mariangolea.fintrack.bank.parser.persistence.category;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
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
		Category grandParent = new Category("Aloha GrandParent", null);
		categoriesRepo.save(grandParent);

		Category parent = new Category("Aloha Parent", grandParent);
		categoriesRepo.save(parent);

		Category child = new Category("Aloha", parent);
		categoriesRepo.save(child);
		parent.addChildrenLocal(child);
// aici e dubios, ca ai adaugat la parent pe child, dar il adaugi pe child si la grandparent.
// nu cred ca merge, ai un singur parent_id, ce valoare sa ia, al parent sau al grand parent?
//		grandParent.addChildrenLocal(parent);

		assertTrue(categoriesService.removeCategory("Aloha Parent"));

		Category foundParent = categoriesRepo.findByName("Aloha Parent");
		assertNull(foundParent);

		Category foundChild = categoriesRepo.findByName("Aloha");
		assertEquals(grandParent, foundChild.getParent());

		assertTrue(categoriesService.removeCategory("Aloha GrandParent"));

		Category foundGrandParent = categoriesRepo.findByName("Aloha GrandParent");
		assertNull(foundGrandParent);

		foundChild = categoriesRepo.findByName("Aloha");
		assertNull(foundChild.getParent());
	}
}
