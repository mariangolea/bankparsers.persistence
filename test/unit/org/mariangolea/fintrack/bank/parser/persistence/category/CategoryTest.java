package org.mariangolea.fintrack.bank.parser.persistence.repository.category;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.mariangolea.fintrack.bank.parser.persistence.repository.categories.Category;

import com.google.common.base.Objects;

public class CategoryTest {

	@Test
	public void testConstructorAndAccessMethods() {
		Category cat = null;
		try {
			cat = new Category();
		} catch (Exception e) {
			assertTrue(false);
		}

		try {
			cat = new Category(null, null);
			assertTrue(false);
		} catch (Exception e) {
			assertTrue(true);
		}

		try {
			cat = new Category("Aloha", null);
			cat = new Category("Aloha", 1L);
		} catch (Exception e) {
			assertTrue(false);
		}

		assertEquals("Aloha", cat.getName());
		assertEquals(1L, cat.getParent());
		assertEquals(null, cat.getId());

		cat.setName("Alohaaa");
		cat.setId(3L);
		cat.setParent(2L);
		assertEquals("Alohaaa", cat.getName());
		assertEquals(2L, cat.getParent());
		assertEquals(3L, cat.getId());

		try {
			cat.setName(null);
			assertTrue(false);
		} catch (Exception e) {
			assertTrue(true);
		}
	}

	@Test
	public void testHashCodeAndEquals() {
		Category cat = new Category("Aloha", 1L);
		int hash = cat.hashCode();

		Category cat1 = new Category("Alohaa", 1L);
		int hash1 = cat1.hashCode();

		assertFalse(hash == hash1);
		assertFalse(Objects.equal(cat, cat1));
		assertTrue(cat.equals(cat));
		assertFalse(cat.equals(null));
		assertFalse(cat.equals("Aloha"));
		
		cat1.setName("Aloha");
		cat1.setParent(2L);
		assertFalse(Objects.equal(cat, cat1));
		
		cat1.setId(2L);
		cat.setId(2L);
		assertFalse(Objects.equal(cat, cat1));
	}
	
	@Test
	public void testToString() {
		Category cat = new Category("Aloha", 1L);
		String stringForm = cat.toString();
		
		String idString = cat.getId() == null ? "null" : cat.getId().toString();
		assertTrue(stringForm.contains(idString));
		assertTrue(stringForm.contains(cat.getParent().toString()));
		assertTrue(stringForm.contains(cat.getName()));
	}
}
