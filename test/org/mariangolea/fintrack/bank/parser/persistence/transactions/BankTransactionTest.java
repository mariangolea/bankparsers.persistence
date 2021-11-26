package org.mariangolea.fintrack.bank.parser.persistence.transactions;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;

public class BankTransactionTest {
	@Test
	void testEmptyConstructor() {
		BankTransaction empty = new BankTransaction();
		testEmptyConstructorFields(empty, 0);

		assertNull(empty.getOriginalContent());
	}

	@Test
	void testSetters() {
		BankTransaction empty = new BankTransaction();
		Date date = new Date();
		empty.setCompletedDate(date);
		assertEquals(date, empty.getCompletedDate());

		date = new Date();
		empty.setStartDate(date);
		assertEquals(date, empty.getStartDate());

		BigDecimal amount = BigDecimal.ONE;
		empty.setCreditAmount(amount);
		assertEquals(amount, empty.getCreditAmount());

		empty.setDebitAmount(amount);
		assertEquals(amount, empty.getDebitAmount());

		empty.setDescription("Aloha");
		assertEquals("Aloha", empty.getDescription());

		empty.setContentLines(3);
		assertEquals(3, empty.getContentLines());

		Long test = 12L;
		empty.setId(test);
		assertEquals(test, empty.getId());

		BankTransactionText text = new BankTransactionText();
		empty.setOriginalContent(text);
		assertEquals(text, empty.getOriginalContent());
	}

	@Test
	void testConstructorOriginalContent() {
		BankTransactionText text = new BankTransactionText("Aloha");
		BankTransaction empty = new BankTransaction(text);
		testEmptyConstructorFields(empty, 0);

		assertEquals(text, empty.getOriginalContent());
	}

	@Test
	void testConstructorContentLines() {
		Collection<String> fileContent = new ArrayList<>();
		fileContent.add("One");
		fileContent.add("Two");
		String assumedFileContentTransformed = "One" + BankTransaction.LINE_DELIMITER + "Two";

		BankTransaction empty = new BankTransaction(fileContent);
		testEmptyConstructorFields(empty, 2);

		assertEquals(new BankTransactionText(assumedFileContentTransformed), empty.getOriginalContent());
	}

	@Test
	void testEquals() {
		Date date = new Date();
		BankTransaction first = new BankTransaction();

		assertEquals(first, first);
		assertNotEquals(first, null);
		assertNotEquals(first, new BankTransactionText());

		first.setStartDate(date);
		first.setCompletedDate(date);
		BankTransaction second = new BankTransaction();
		second.setStartDate(date);
		second.setCompletedDate(date);
		assertTrue(first.equals(second));

		first.setCompletedDate(second.getCompletedDate());
		first.setDescription("");
		assertNotEquals(first, second);
	}

	@Test
	void testHash() {
		Date date = new Date();
		BankTransaction first = new BankTransaction();
		first.setStartDate(date);
		first.setCompletedDate(date);
		
		BankTransaction second = new BankTransaction();
		second.setStartDate(date);
		second.setCompletedDate(date);
		assertEquals(first.hashCode(), second.hashCode());
	}

	@Test
	void testCompare() throws InterruptedException {
		BankTransaction first = new BankTransaction();
		BankTransaction second = new BankTransaction();

		assertEquals(0, first.compareTo(second));

		TimeUnit.MILLISECONDS.sleep(10);
		second.setCompletedDate(Date.from(Instant.now()));
		assertTrue(first.compareTo(second) < 0);

		TimeUnit.MILLISECONDS.sleep(10);
		first.setCompletedDate(Date.from(Instant.now()));
		assertTrue(first.compareTo(second) > 0);
		second.setCompletedDate(first.getCompletedDate());

		second.setDescription("");
		assertTrue(first.compareTo(second) < 0);

		first.setDescription("");
		assertEquals(0, first.compareTo(second));

		second.setDescription(null);
		assertTrue(first.compareTo(second) > 0);

		second.setDescription("a");
		assertTrue(first.compareTo(second) < 0);

		first.setDescription("c");
		assertTrue(first.compareTo(second) > 0);
	}

	@Test
	public void testToString() {
		BankTransaction test = new BankTransaction();
		String expected = "" + BankTransaction.LINE_DELIMITER + BankTransaction.DEFAULT_AMOUNT.toString();
		assertEquals(expected, test.toString());

		test.setDebitAmount(BigDecimal.ONE);
		expected = "" + BankTransaction.LINE_DELIMITER + BigDecimal.ONE.toString();
		assertEquals(expected, test.toString());

		test.setCreditAmount(BigDecimal.TEN);
		expected = "" + BankTransaction.LINE_DELIMITER + BigDecimal.TEN.toString();
		assertEquals(expected, test.toString());

		test.setDescription("ShortOf20Characters");
		expected = "ShortOf20Characters" + BankTransaction.LINE_DELIMITER + BigDecimal.TEN.toString();
		assertEquals(expected, test.toString());

		test.setDescription("20CharactersPlusOne 1");
		expected = "20CharactersPlusOne " + BankTransaction.LINE_DELIMITER + BigDecimal.TEN.toString();
		assertEquals(expected, test.toString());
	}

	private void testEmptyConstructorFields(BankTransaction transaction, int lines) {
		assertNotNull(transaction.getStartDate());
		assertNotNull(transaction.getCompletedDate());
		assertEquals(BankTransaction.DEFAULT_AMOUNT, transaction.getCreditAmount());
		assertEquals(BankTransaction.DEFAULT_AMOUNT, transaction.getDebitAmount());
		assertNull(transaction.getDescription());
		assertEquals(lines, transaction.getContentLines());
		assertNull(transaction.getId());
	}
}
