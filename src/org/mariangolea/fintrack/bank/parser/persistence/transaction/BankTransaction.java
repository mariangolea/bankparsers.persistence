package org.mariangolea.fintrack.bank.parser.persistence.transaction;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;

import org.mariangolea.fintrack.bank.parser.persistence.FintrackEntityBase;
import org.mariangolea.fintrack.bank.transaction.BankTransactionInterface;
import org.mariangolea.fintrack.bank.transaction.BankTransactionTextInterface;

/**
 * Container of parsed and raw csv data for any transaction. <br>
 * Instances of this class will always contain data read directly from CSV
 * files, making no further changes on them.
 */
@Entity
@Table(name = "transactions")
public class BankTransaction extends FintrackEntityBase implements Serializable, BankTransactionInterface {

	private static final long serialVersionUID = 3176537482173332346L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "started_date", nullable = false)
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	private Date started;

	@Column(name = "completed_date", nullable = false)
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	private Date completed;

	@Column(name = "credit_amount", nullable = false)
	private BigDecimal creditAmount;

	@Column(name = "debit_amount", nullable = false)
	private BigDecimal debitAmount;

	@Column(name = "description", length = MAX_DESCRIPTION_LENGTH)
	private String description;

	@JoinColumn(name = "original_content_id", nullable = false)
	@OneToOne(cascade = CascadeType.ALL)
	private BankTransactionText originalContent;

	@Transient
	private int contentLines = 0;

	public BankTransaction() {
		started = adjustDate(null);
		completed = adjustDate(null);
		creditAmount = adjustAmount(null);
		debitAmount = adjustAmount(null);
		originalContent = new BankTransactionText();
	}

	BankTransaction(BankTransactionInterface transaction) {
		this();
		if (transaction != null) {
			started = adjustDate(transaction.getStartDate());
			completed = adjustDate(transaction.getCompletedDate());
			creditAmount = adjustAmount(transaction.getCreditAmount());
			debitAmount = adjustAmount(transaction.getDebitAmount());
			originalContent = new BankTransactionText(transaction.getOriginalContent());
		}
	}

	public BankTransaction(final BankTransactionText originalContent) {
		this();
		this.originalContent = Objects.requireNonNull(originalContent);
	}

	public BankTransaction(final Collection<String> fileContent) {
		this();
		BankTransactionText text = new BankTransactionText();
		this.originalContent = text;
		if (fileContent != null && !fileContent.isEmpty()) {
			StringBuilder content = new StringBuilder();
			for (String line : fileContent) {
				content.append(line).append(LINE_DELIMITER);
				contentLines++;
			}
			text.setOriginalContent(content.substring(0, content.length() - LINE_DELIMITER.length()));
		}
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		BankTransaction that = (BankTransaction) o;
		return Objects.equals(that.creditAmount, creditAmount) && Objects.equals(that.debitAmount, debitAmount)
				&& Objects.equals(started, that.started) && Objects.equals(completed, that.completed)
				&& Objects.equals(description, that.description);
	}

	@Override
	public int hashCode() {
		return Objects.hash(started, completed, creditAmount, debitAmount, description);
	}

	/**
	 * Support only for ordering purposes: by date, and then by description.
	 */
	@Override
	public int compareTo(final BankTransactionInterface o) {
		int result = completed.compareTo(o.getCompletedDate());
		if (result == 0) {
			if (description == null) {
				result = o.getDescription() == null ? 0 : -1;
			} else {
				result = o.getDescription() == null ? 1 : description.compareTo(o.getDescription());
			}
		}

		return result;
	}

	@Override
	public String toString() {
		BigDecimal amount = creditAmount == DEFAULT_AMOUNT ? debitAmount : creditAmount;
		String descSubString = "";
		if (description != null) {
			descSubString = description.length() > SHORT_DESCRIPTION_SIZE
					? description.trim().substring(0, SHORT_DESCRIPTION_SIZE)
					: description;
		}
		return descSubString + LINE_DELIMITER + amount.toString();
	}

	public BankTransactionText getOriginalContent() {
		return originalContent;
	}

	public int getContentLines() {
		return contentLines;
	}

	public Date getStartDate() {
		return started;
	}

	public Date getCompletedDate() {
		return completed;
	}

	public BigDecimal getCreditAmount() {
		return creditAmount;
	}

	public BigDecimal getDebitAmount() {
		return debitAmount;
	}

	public String getDescription() {
		return description;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setStartDate(Date startDate) {
		this.started = adjustDate(startDate);
	}

	public void setCompletedDate(Date completedDate) {
		this.completed = adjustDate(completedDate);
	}

	public void setCreditAmount(BigDecimal creditAmount) {
		this.creditAmount = adjustAmount(creditAmount);
	}

	public void setDebitAmount(BigDecimal debitAmount) {
		this.debitAmount = adjustAmount(debitAmount);
	}

	public void setDescription(String description) {
		this.description = adjustString(description, MAX_DESCRIPTION_LENGTH);
	}

	public void setOriginalContent(BankTransactionText originalContent) {
		this.originalContent = Objects.requireNonNull(originalContent);
	}

	@Override
	public void setOriginalContent(BankTransactionTextInterface originalContent) {
		this.originalContent = new BankTransactionText(Objects.requireNonNull(originalContent).getOriginalContent());
	}

	public void setContentLines(int contentLines) {
		this.contentLines = contentLines;
	}
}
