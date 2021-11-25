package org.mariangolea.fintrack.bank.parser.persistence.repository.companies;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.google.common.base.Objects;

public class CompanyNameTest {
	@Test
	public void testConstructorAndAccessMethods() {
		CompanyName temp = null;
		try {
			temp = new CompanyName();
		} catch (Exception e) {
			assertTrue(false);
		}

		try {
			temp = new CompanyName(null, null, null);
			assertTrue(false);
		} catch (Exception e) {
			assertTrue(true);
		}

		List<CompanyIdentifier> list = null;
		try {
			temp = new CompanyName(null, "Aloha", null);
			list = new ArrayList<>();
			list.add(new CompanyIdentifier("Ab", temp));
			temp = new CompanyName(null, "Aloha",list);
		} catch (Exception e) {
			assertTrue(false);
		}

		assertEquals("Aloha", temp.getName());
		assertEquals("Ab", temp.getIdentifiers().iterator().next().getName());

		temp.setName("Alohaaa");
		temp.setId(3L);
		list.clear();
		list.add(new CompanyIdentifier("Abb", temp));
		temp.setIdentifiers(list);
		assertEquals("Alohaaa", temp.getName());
		assertEquals(3L, temp.getId());
		assertEquals("Abb", temp.getIdentifiers().iterator().next().getName());

		try {
			temp.setName(null);
			assertTrue(false);
		} catch (Exception e) {
			assertTrue(true);
		}
	}

	@Test
	public void testHashCodeAndEquals() {
		List<CompanyIdentifier> list = null;
		list = new ArrayList<>();
		list.add(new CompanyIdentifier("Ab", null));
		CompanyName cat = new CompanyName(null, "Aloha",list);
		int hash = cat.hashCode();

		CompanyName cat1 = new CompanyName(null, "Aloha", null);
		int hash1 = cat1.hashCode();

		assertFalse(hash == hash1);
		assertFalse(Objects.equal(cat, cat1));
		assertTrue(cat.equals(cat));
		assertFalse(cat.equals(null));
		assertFalse(cat.equals("Aloha"));

		cat1.setName("Aloha");
		cat1.setId(2L);
		assertFalse(Objects.equal(cat, cat1));

		cat1.setId(2L);
		cat.setId(2L);
		list = new ArrayList<>();
		list.add(new CompanyIdentifier("Aba", null));
		cat1.setIdentifiers(list);
		assertFalse(Objects.equal(cat, cat1));
	}

	@Test
	public void testToString() {
		List<CompanyIdentifier> list = null;
		list = new ArrayList<>();
		list.add(new CompanyIdentifier("Ab", null));
		CompanyName cat = new CompanyName(null, "Aloha",list);
		String stringForm = cat.toString();

		String idString = cat.getId() == null ? "null" : cat.getId().toString();
		assertTrue(stringForm.contains(idString));
		assertTrue(stringForm.contains(cat.getName()));
		assertTrue(stringForm.contains("Aloha"));
	}
}
