package org.mariangolea.fintrack.bank.parser.persistence.category;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Objects;

import org.junit.jupiter.api.Test;

public class CategoryTest {

	@Test
	public void testConstructorAndAccessMethods() {
		Category cat = null;
		Category parent = createFullCategory();
		try {
			cat = new Category();
		} catch (Exception e) {
			assertTrue(false);
		}

		try {
			cat = new Category("Aloha", null);
			cat = new Category("Aloha", parent);
		} catch (Exception e) {
			assertTrue(false);
		}
		
		assertEquals("Aloha", cat.getName());
		assertEquals(parent, cat.getParent());

		cat.setName("Alohaaa");
		cat.setId(3L);
		assertEquals("Alohaaa", cat.getName());
		assertEquals(parent, cat.getParent());

		try {
			cat.setName(null);
			assertTrue(false);
		} catch (Exception e) {
			assertTrue(true);
		}
	}

	@Test
	public void testHashCodeAndEquals() {
		Category cat = new Category("Aloha", null);
		int hash = cat.hashCode();

		Category cat1 = new Category("Alohaa", null);
		int hash1 = cat1.hashCode();

		assertFalse(hash == hash1);
		assertFalse(Objects.equals(cat, cat1));
		assertTrue(cat.equals(cat));
		assertFalse(cat.equals(null));
		assertFalse(cat.equals("Aloha"));
		
		cat1.setName("Aloha");
		cat1.setParent(new Category("una", null));
		assertFalse(Objects.equals(cat, cat1));
		
		cat1.setId(2L);
		cat.setId(2L);
		assertFalse(Objects.equals(cat, cat1));
	}
	
	@Test
	public void testToString() {
		Category cat = new Category("Aloha", new Category("meh", null));
		String stringForm = cat.toString();
		
		String idString = cat.getId() == null ? "null" : cat.getId().toString();
		assertTrue(stringForm.contains(idString));
		assertTrue(stringForm.contains(cat.getParent().toString()));
		assertTrue(stringForm.contains(cat.getName()));
	}
	
	
	private Category createFullCategory() {
		Category parent = new Category("one", null);
		parent.addChildrenLocal(new Category("meh", parent), new Category("beh", parent));

		
		return parent;
	}
}
