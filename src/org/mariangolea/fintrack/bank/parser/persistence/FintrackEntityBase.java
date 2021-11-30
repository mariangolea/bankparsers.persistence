package org.mariangolea.fintrack.bank.parser.persistence;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class FintrackEntityBase {
	protected static final int DEFAULT_STRING_LENGTH = 250;
	protected static final String DEFAULT_STRING_VALUE = "";
	protected static final BigDecimal DEFAULT_AMOUNT = BigDecimal.ZERO;
	protected static final Integer DEFAULT_INTEGER = 0;
	protected static final String LINE_DELIMITER = "\n";
	protected static final int SHORT_DESCRIPTION_SIZE = 20;
	protected static final int MAX_DESCRIPTION_LENGTH = 1000;

	protected String adjustString(String input, int maxLength) {
		if (input == null || input.length() <= maxLength) {
			return input;
		}

		return input.substring(0, maxLength);
	}
	
	protected String adjustString(String input, int maxLength, String defaultValue) {
		if (input == null) {
			return defaultValue;
		}
		
		if (input.length() <= maxLength) {
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

	protected Integer adjustInteger(Integer value, Integer defaultValue) {
		return value == null ? defaultValue : value;
	}
	
	protected Integer adjustInteger(Integer value) {
		return adjustInteger(value, DEFAULT_INTEGER);
	}
	
	protected <E> E get(Optional<E> optional) {
		if (optional == null || !optional.isPresent()) {
			return null;
		}
		return optional.get();
	}
	
	protected <A, E extends A> Collection<A> toInterface(Collection<E> actual) {
		if (actual == null || actual.isEmpty()) {
			return Collections.emptyList();
		}

		List<A> identifiers = new ArrayList<>();
		for (E originalObject : actual) {
			identifiers.add(originalObject);
		}
		
		return identifiers;
	}
}
