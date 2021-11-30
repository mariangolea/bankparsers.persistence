package org.mariangolea.fintrack.bank.parser.persistence.company;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.junit.jupiter.api.Test;

public class CompanyNameTest {
	@Test
	public void testConstructorAndAccessMethods() {
		CompanyName temp = null;
		try {
			temp = new CompanyName();
			temp = new CompanyName(null, null);
		} catch (Exception e) {
			assertTrue(false);
		}

		List<CompanyIdentifier> list = null;
		try {
			temp = new CompanyName("Aloha", null);
			list = new ArrayList<>();
			list.add(new CompanyIdentifier("Ab", temp));
			temp = new CompanyName("Aloha", list);
		} catch (Exception e) {
			assertTrue(false);
		}

		assertEquals("Aloha", temp.getName());
		assertEquals("Ab", temp.getIdentifiers().iterator().next().getText());

		temp.setName("Alohaaa");
		temp.setId(3L);
		list.clear();
		list.add(new CompanyIdentifier("Abb", temp));
		temp.setIdentifiersLocal(list);
		assertEquals("Alohaaa", temp.getName());
		assertEquals(3L, temp.getId());
		assertEquals("Abb", temp.getIdentifiers().iterator().next().getText());
	}

	@Test
	public void testHashCodeAndEquals() {
		List<CompanyIdentifier> list = null;
		list = new ArrayList<>();
		list.add(new CompanyIdentifier("Ab", null));
		CompanyName cat = new CompanyName("Aloha", list);
		int hash = cat.hashCode();

		CompanyName cat1 = new CompanyName("Aloha", null);
		int hash1 = cat1.hashCode();

		assertFalse(hash == hash1);
		assertFalse(Objects.equals(cat, cat1));
		assertTrue(cat.equals(cat));
		assertFalse(cat.equals(null));
		assertFalse(cat.equals("Aloha"));

		cat1.setName("Aloha");
		cat1.setId(2L);
		assertFalse(Objects.equals(cat, cat1));

		cat1.setId(2L);
		cat.setId(2L);
		list = new ArrayList<>();
		list.add(new CompanyIdentifier("Aba", null));
		cat1.setIdentifiersLocal(list);
		assertFalse(Objects.equals(cat, cat1));
	}

	@Test
	public void testToString() {
		List<CompanyIdentifier> list = null;
		list = new ArrayList<>();
		list.add(new CompanyIdentifier("Ab", null));
		CompanyName cat = new CompanyName("Aloha", list);
		String stringForm = cat.toString();

		String idString = cat.getId() == null ? "null" : cat.getId().toString();
		assertTrue(stringForm.contains(idString));
		assertTrue(stringForm.contains(cat.getName()));
		assertTrue(stringForm.contains("Aloha"));
	}
}
