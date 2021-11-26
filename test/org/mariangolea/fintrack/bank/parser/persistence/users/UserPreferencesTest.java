package org.mariangolea.fintrack.bank.parser.persistence.users;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.google.common.base.Objects;

public class UserPreferencesTest {
	
	@Test
	public void testConstructorAndAccessMethods() {
		UserPreferences test = null;
		try {
			test = new UserPreferences();
			test = new UserPreferences(null, null, null);
			test = new UserPreferences(1, null, null);
			test = new UserPreferences(1, "", null);
		} catch (Exception e) {
			assertTrue(false);
		}

		test = new UserPreferences(2, "pwd", 3);

		assertEquals("pwd", test.getInputFolder());
		assertEquals(3, test.getPageSize());
		assertEquals(2, test.getTimeFrameInterval());

		test.setInputFolder("Alohaaa");
		test.setPageSize(5);
		test.setTimeFrameInterval(3);
		
		assertEquals("Alohaaa", test.getInputFolder());
		assertEquals(5, test.getPageSize());
		assertEquals(3, test.getTimeFrameInterval());

		try {
			test.setInputFolder(null);
			assertEquals("", test.getInputFolder());
			test.setPageSize(null);
			assertEquals(50, test.getPageSize());
			test.setTimeFrameInterval(null);
			assertEquals(1, test.getTimeFrameInterval());
		} catch (Exception e) {
			assertTrue(false);
		}
	}

	@Test
	public void testHashCodeAndEquals() {
		UserPreferences test = new UserPreferences(1, "P", 2);
		int hash = test.hashCode();

		UserPreferences test1 = new UserPreferences(1, "P", 3);
		int hash1 = test1.hashCode();

		assertFalse(hash == hash1);
		assertFalse(Objects.equal(test, test1));
		assertTrue(test.equals(test));
		assertFalse(test.equals(null));
		assertFalse(test.equals("Aloha"));

		test1.setPageSize(2);
		assertTrue(Objects.equal(test, test1));
	}

	@Test
	public void testToString() {
		UserPreferences test = new UserPreferences(1, "p", 2);
		String stringForm = test.toString();

		assertTrue(stringForm.contains("1"));
		assertTrue(stringForm.contains("p"));
		assertTrue(stringForm.contains("2"));
	}
}
