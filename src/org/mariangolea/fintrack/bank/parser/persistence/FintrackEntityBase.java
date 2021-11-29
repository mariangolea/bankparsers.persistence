package org.mariangolea.fintrack.bank.parser.persistence;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

public class FintrackEntityBase {
	protected static final int DEFAULT_STRING_LENGTH = 250;
	protected static final BigDecimal DEFAULT_AMOUNT = BigDecimal.ZERO;
	protected static final String LINE_DELIMITER = "\n";
	protected static final int SHORT_DESCRIPTION_SIZE = 20;
	protected static final int MAX_DESCRIPTION_LENGTH = 1000;

	protected String adjustString(String input, int maxLength) {
		if (input == null || input.length() <= maxLength) {
			return input;
		}

		return input.substring(0, maxLength);
	}

	protected String adjustString(String input) {
		return adjustString(input, DEFAULT_STRING_LENGTH);
	}

	protected BigDecimal adjustAmount(BigDecimal amount, BigDecimal defaultValue) {
		return amount == null ? defaultValue : amount.abs();
	}

	protected BigDecimal adjustAmount(BigDecimal amount) {
		return adjustAmount(amount, DEFAULT_AMOUNT);
	}

	protected Date adjustDate(Date date) {
		return date == null ? new Date() : date;
	}
	
	protected <E> E get(Optional<E> optional) {
		if (optional == null || !optional.isPresent()) {
			return null;
		}
		return optional.get();
	}
}
