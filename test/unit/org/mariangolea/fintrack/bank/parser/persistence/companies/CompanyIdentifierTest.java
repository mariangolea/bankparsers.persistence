package org.mariangolea.fintrack.bank.parser.persistence.repository.companies;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.google.common.base.Objects;

public class CompanyIdentifierTest {
	@Test
	public void testConstructorAndAccessMethods() {
		CompanyIdentifier temp = null;
		try {
			temp = new CompanyIdentifier();
		} catch (Exception e) {
			assertTrue(false);
		}

		try {
			temp = new CompanyIdentifier(null, null);
			assertTrue(false);
		} catch (Exception e) {
			assertTrue(true);
		}

		try {
			temp = new CompanyIdentifier("Aloha", null);
			temp = new CompanyIdentifier("Aloha", new CompanyName(null, "Al", null));
		} catch (Exception e) {
			assertTrue(false);
		}

		assertEquals("Aloha", temp.getName());
		assertEquals("Al", temp.getCompanyName().getName());

		temp.setName("Alohaaa");
		temp.setId(3L);
		temp.setCompanyName(new CompanyName(null, "Alo", null));
		assertEquals("Alohaaa", temp.getName());
		assertEquals(3L, temp.getId());
		assertEquals("Alo", temp.getCompanyName().getName());

		try {
			temp.setName(null);
			assertTrue(false);
		} catch (Exception e) {
			assertTrue(true);
		}
	}

	@Test
	public void testHashCodeAndEquals() {
		CompanyIdentifier cat = new CompanyIdentifier("Aloha", new CompanyName(null, "Al", null));
		int hash = cat.hashCode();

		CompanyIdentifier cat1 = new CompanyIdentifier("Alohaa", new CompanyName(null, "Al", null));
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
		cat1.setCompanyName(new CompanyName(null, "Ab", null));
		assertFalse(Objects.equal(cat, cat1));
	}

	@Test
	public void testToString() {
		CompanyIdentifier cat = new CompanyIdentifier("Aloha", new CompanyName(null, "Ab", null));
		String stringForm = cat.toString();

		String idString = cat.getId() == null ? "null" : cat.getId().toString();
		assertTrue(stringForm.contains(idString));
		assertTrue(stringForm.contains(cat.getName()));
		assertTrue(stringForm.contains("Ab"));
	}
}
