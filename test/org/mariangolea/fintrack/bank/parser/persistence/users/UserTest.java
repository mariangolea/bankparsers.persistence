package org.mariangolea.fintrack.bank.parser.persistence.users;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.google.common.base.Objects;

public class UserTest {
	
	@Test
	public void testConstructorAndAccessMethods() {
		User test = null;
		try {
			test = new User();
			test = new User(null, null, null);
			test = new User("", null, null);
			test = new User("", "", null);
		} catch (Exception e) {
			assertTrue(false);
		}

		test = new User("name", "pwd", null);
		
		assertEquals("name", test.getName());
		assertEquals("pwd", test.getPassword());
		assertEquals(null, test.getPreferences());

		test.setName("Alohaaa");
		test.setPassword("P");
		UserPreferences prefs = new UserPreferences();
		test.setPreferences(prefs);
		assertEquals("Alohaaa", test.getName());
		assertEquals("P", test.getPassword());
		assertEquals(prefs, test.getPreferences());

		try {
			test.setName(null);
			assertEquals("", test.getName());
			test.setPassword(null);
			assertEquals("", test.getPassword());
		} catch (Exception e) {
			assertTrue(false);
		}
	}

	@Test
	public void testHashCodeAndEquals() {
		User test = new User("Aloha", "P", null);
		int hash = test.hashCode();

		User test1 = new User("Alohaa", "P", null);
		int hash1 = test1.hashCode();

		assertFalse(hash == hash1);
		assertFalse(Objects.equal(test, test1));
		assertTrue(test.equals(test));
		assertFalse(test.equals(null));
		assertFalse(test.equals("Aloha"));

		test1.setName("Aloha");
		test1.setPassword("P");
		assertTrue(Objects.equal(test, test1));
	}

	@Test
	public void testToString() {
		UserPreferences prefs = new UserPreferences();
		prefs.setPageSize(10);
		User test = new User("Aloha", "p", null);
		test.setPreferences(prefs);
		String stringForm = test.toString();
		
		assertTrue(stringForm.contains("Aloha"));
		assertTrue(stringForm.contains("p"));
		assertTrue(stringForm.contains("10"));
	}
}
