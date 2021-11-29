package org.mariangolea.fintrack.bank.parser.persistence.transaction;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.mariangolea.fintrack.bank.transaction.BankTransactionTextInterface;

@Entity
@Table(name = "transactiontexts")
public class BankTransactionText implements Serializable, BankTransactionTextInterface{

	private static final long serialVersionUID = -717598296361745618L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "original_content")
    private String originalContent;
    
    public BankTransactionText() {
    	this((String)null);
    }

    public BankTransactionText(String originalContent) {
        this.originalContent = originalContent;
    }
    
    BankTransactionText(BankTransactionTextInterface text){
    	this.originalContent = text == null ? null : text.getOriginalContent();
    }

    public String getOriginalContent() {
        return originalContent;
    }

    public void setOriginalContent(String originalContent) {
        this.originalContent = originalContent;
    }

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(originalContent);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BankTransactionText other = (BankTransactionText) obj;
		return Objects.equals(originalContent, other.originalContent);
	}

    @Override
    public String toString() {
        return "BankTransactionText{" + "id=" + id + ", originalContent=" + originalContent + '}';
    }
    
}
