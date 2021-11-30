package org.mariangolea.fintrack.bank.parser.persistence.company;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Objects;

import org.junit.jupiter.api.Test;
import org.mariangolea.fintrack.company.CompanyIdentifierInterface;

public class CompanyIdentifierTest {
	@Test
	public void testConstructorAndAccessMethods() {
		CompanyIdentifier temp = null;
		try {
			temp = new CompanyIdentifier();
			temp = new CompanyIdentifier((String)null);
			temp = new CompanyIdentifier((CompanyIdentifierInterface)null);
			temp = new CompanyIdentifier("Aloha", new CompanyName("Al"));
		} catch (Exception e) {
			assertTrue(false);
		}

		assertEquals("Aloha", temp.getText());
		assertEquals("Al", temp.getCompanyName().getName());

		temp.setText("Alohaaa");
		temp.setId(3L);
		temp.setCompanyName(new CompanyName("Alo"));
		assertEquals("Alohaaa", temp.getText());
		assertEquals(3L, temp.getId());
		assertEquals("Alo", temp.getCompanyName().getName());

		try {
			temp.setText(null);
		} catch (Exception e) {
			assertTrue(false);
		}
	}

	@Test
	public void testHashCodeAndEquals() {
		CompanyIdentifier cat = new CompanyIdentifier("Aloha", new CompanyName("Al"));
		int hash = cat.hashCode();

		CompanyIdentifier cat1 = new CompanyIdentifier("Alohaa", new CompanyName("Al"));
		int hash1 = cat1.hashCode();

		assertFalse(hash == hash1);
		assertFalse(Objects.equals(cat, cat1));
		assertTrue(cat.equals(cat));
		assertFalse(cat.equals(null));
		assertFalse(cat.equals("Aloha"));

		cat1.setText("Aloha");
		assertTrue(Objects.equals(cat, cat1));
	}

	@Test
	public void testToString() {
		CompanyIdentifier cat = new CompanyIdentifier("Aloha", new CompanyName("Ab"));
		String stringForm = cat.toString();

		String idString = cat.getId() == null ? "null" : cat.getId().toString();
		assertTrue(stringForm.contains(idString));
		assertTrue(stringForm.contains(cat.getText()));
		assertTrue(!stringForm.contains("Ab"));
	}
}
