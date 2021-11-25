package org.mariangolea.fintrack.bank.parser.persistence.repository.category;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.mariangolea.fintrack.bank.parser.persistence.repository.BaseDataJPATest;
import org.mariangolea.fintrack.bank.parser.persistence.repository.categories.CategoriesRepository;
import org.mariangolea.fintrack.bank.parser.persistence.repository.categories.CategoriesService;
import org.mariangolea.fintrack.bank.parser.persistence.repository.categories.Category;
import org.mariangolea.fintrack.bank.parser.persistence.repository.companies.CompanyIdentifierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(
		classes = {
				CategoriesService.class, 
				CategoriesRepository.class,
				CompanyIdentifierRepository.class
				})
public class CategoryServiceTest extends BaseDataJPATest{

	@Autowired
	private CategoriesService categoriesService;
	
	@Autowired
	private CategoriesRepository categoriesRepo;
	
	@Test
	public void testGetParent() {
		Category preexisting = categoriesService.getParent("Aloha");
		assertNull(preexisting);
		
		Category toAdd = new Category();
		toAdd.setName("AlohaParent");
		categoriesRepo.save(toAdd);
		Long parentId = toAdd.getId();
		
		toAdd = new Category();
		toAdd.setName("Aloha");
		toAdd.setParent(parentId);
		categoriesRepo.save(toAdd);
		
		preexisting = categoriesService.getParent("Aloha");
		assertNotNull(preexisting);
		assertEquals("AlohaParent", preexisting.getName());
	}
	
	@Test
	public void testGetTopMostCategories() {
		Category toAdd = new Category();
		toAdd.setName("AlohaParent");
		categoriesRepo.save(toAdd);
		Long parentId = toAdd.getId();
		
		toAdd = new Category();
		toAdd.setName("AlohaParent 2");
		categoriesRepo.save(toAdd);
		
		toAdd = new Category();
		toAdd.setName("Aloha");
		toAdd.setParent(parentId);
		categoriesRepo.save(toAdd);
		
		Collection<Category> topMost = categoriesService.getTopMostCategories();
		assertNotNull(topMost);
		assertEquals(2, topMost.size());
	}
	
	@Test
	public void testGetUserDefinedCategoryNames() {
		Collection<Category> names = categoriesService.getCategoryNames();
		assertEquals(0, names.size());
		
		
		Category toAdd = new Category();
		toAdd.setName("AlohaParent");
		categoriesRepo.save(toAdd);
		Long parentId = toAdd.getId();
		
		toAdd = new Category();
		toAdd.setName("AlohaParent 2");
		categoriesRepo.save(toAdd);
		
		toAdd = new Category();
		toAdd.setName("Aloha");
		toAdd.setParent(parentId);
		categoriesRepo.save(toAdd);
		
		names = categoriesService.getCategoryNames();
		assertNotNull(names);
		assertEquals(3, names.size());
	}
	
	@Test
	public void testGetSubCategories() {
		Collection<Category> names = categoriesService.getCategoryNames();
		assertEquals(0, names.size());
		
		
		Category toAdd = new Category();
		toAdd.setName("Aloha GrandParent");
		categoriesRepo.save(toAdd);
		Long parentId = toAdd.getId();
		
		toAdd = new Category();
		toAdd.setName("Aloha Parent");
		toAdd.setParent(parentId);
		categoriesRepo.save(toAdd);
		parentId = toAdd.getId();
		
		toAdd = new Category();
		toAdd.setName("Aloha");
		toAdd.setParent(parentId);
		categoriesRepo.save(toAdd);
		
		names = categoriesService.getSubCategories("Aloha GrandParent");
		assertNotNull(names);
		assertEquals(1, names.size());
		
		names = categoriesService.getSubCategories("Aloha Parent");
		assertNotNull(names);
		assertEquals(1, names.size());
		
		names = categoriesService.getSubCategories("Aloha");
		assertNotNull(names);
		assertEquals(0, names.size());
	}
	
	@Test
	public void testRemoveCategory() {
		Category toAdd = new Category();
		toAdd.setName("Aloha GrandParent");
		categoriesRepo.save(toAdd);
		Long grandParentId = toAdd.getId();
		
		toAdd = new Category();
		toAdd.setName("Aloha Parent");
		toAdd.setParent(grandParentId);
		categoriesRepo.save(toAdd);
		Long parentId = toAdd.getId();
		
		toAdd = new Category();
		toAdd.setName("Aloha");
		toAdd.setParent(parentId);
		categoriesRepo.save(toAdd);
		
		boolean success = categoriesService.removeCategory("Aloha Parent");
		assertTrue(success);
		toAdd = categoriesRepo.findByName("Aloha Parent");
		assertNull(toAdd);
		toAdd = categoriesRepo.findByName("Aloha");
		assertEquals(grandParentId, toAdd.getParent());
		
		success = categoriesService.removeCategory("Aloha GrandParent");
		assertTrue(success);
		toAdd = categoriesRepo.findByName("Aloha GrandParent");
		assertNull(toAdd);
		toAdd = categoriesRepo.findByName("Aloha");
		assertEquals(null, toAdd.getParent());
	}
}
