package org.mariangolea.fintrack.bank.parser.persistence.repository.transactions;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
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

/**
 * Container of parsed and raw csv data for any transaction.
 * <br> Instances of this class will always contain data read directly from CSV
 * files, making no further changes on them.
 */
@Entity
@Table(name = "transactions")
public class BankTransaction implements Serializable, Comparable<BankTransaction>{

	private static final long serialVersionUID = 3176537482173332346L;

	private static final String LINE_DELIMITER = "\n";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "start_date")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date startDate;

    @Column(name = "completed_date")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date completedDate;

    @Column(name = "credit_amount")
    private BigDecimal creditAmount;

    @Column(name = "debit_amount")
    private BigDecimal debitAmount;

    @Column(name = "description")
    private String description;
    
    @JoinColumn(name="original_content_id")
    @OneToOne(cascade = CascadeType.ALL)
    private BankTransactionText originalContent;
    
    @Transient
    private int contentLines = 0;
    
    public BankTransaction() {
    }

    public BankTransaction(
            final Date startDate,
            final Date completedDate,
            BigDecimal creditAmount,
            BigDecimal debitAmount,
            final String description,
            final BankTransactionText originalContent) {
        super();
        Objects.requireNonNull(startDate);
        Objects.requireNonNull(completedDate);
        Objects.requireNonNull(description);
        Objects.requireNonNull(originalContent);

        this.startDate = startDate;
        this.completedDate = completedDate;
        this.creditAmount = creditAmount == null ? BigDecimal.ZERO : creditAmount.abs();
        this.debitAmount = debitAmount == null ? BigDecimal.ZERO : debitAmount.abs();
        this.description = description;
        this.originalContent = originalContent;
    }

    public BankTransaction(
            final Date startDate,
            final Date completedDate,
            BigDecimal creditAmount,
            BigDecimal debitAmount,
            final String description,
            final List<String> csvContent) {
        super();
        Objects.requireNonNull(startDate);
        Objects.requireNonNull(completedDate);
        Objects.requireNonNull(description);
        Objects.requireNonNull(csvContent);

        this.startDate = startDate;
        this.completedDate = completedDate;
        this.creditAmount = creditAmount == null ? BigDecimal.ZERO : creditAmount.abs();
        this.debitAmount = debitAmount == null ? BigDecimal.ZERO : debitAmount.abs();
        this.description = description;
        StringBuilder content = new StringBuilder();
        for (String line : csvContent){
            content.append(line).append(LINE_DELIMITER);
            contentLines++;
        }
        BankTransactionText text = new BankTransactionText();
        text.setOriginalContent(content.substring(0,content.length()-LINE_DELIMITER.length()));
        setOriginalContent(text);
    }

    public BankTransactionText getOriginalContent() {
        return originalContent;
    }

    public int getCSVContentLines() {
        return contentLines;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getCompletedDate() {
        return completedDate;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BankTransaction that = (BankTransaction) o;
        return Objects.equals(that.creditAmount, creditAmount)
                && Objects.equals(that.debitAmount, debitAmount)
                && Objects.equals(startDate, that.startDate)
                && Objects.equals(completedDate, that.completedDate)
                && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                startDate,
                completedDate,
                creditAmount,
                debitAmount,
                description);
    }

    @Override
    public int compareTo(final BankTransaction o) {
        int result = completedDate.compareTo(o.completedDate);
        if (result == 0) {
            result = description.compareTo(o.description);
        }
        return result;
    }

    @Override
    public String toString() {
        BigDecimal amount = creditAmount == BigDecimal.ZERO ? debitAmount : creditAmount;
        String descSubString = description.length() > 20 ? description.trim().substring(0, 20) : description;
        return descSubString + "\n" + amount.toString();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setCompletedDate(Date completedDate) {
        this.completedDate = completedDate;
    }

    public void setCreditAmount(BigDecimal creditAmount) {
        this.creditAmount = creditAmount;
    }

    public void setDebitAmount(BigDecimal debitAmount) {
        this.debitAmount = debitAmount;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setOriginalContent(BankTransactionText originalContent) {
        this.originalContent = originalContent;
    }

    public void setContentLines(int contentLines) {
        this.contentLines = contentLines;
    }
    
    
}
