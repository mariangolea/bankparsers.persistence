package org.mariangolea.fintrack.bank.parser.persistence.repository.transactions;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "transactiontexts")
public class BankTransactionText implements Serializable{

	private static final long serialVersionUID = -717598296361745618L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "original_content")
    private String originalContent;
    
    public BankTransactionText() {
    }

    public BankTransactionText(String originalContent) {
        this.originalContent = originalContent;
    }

    public String getOriginalContent() {
        return originalContent;
    }

    public void setOriginalContent(String originalContent) {
        this.originalContent = originalContent;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final BankTransactionText other = (BankTransactionText) obj;
        if (!Objects.equals(this.originalContent, other.originalContent)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "BankTransactionText{" + "id=" + id + ", originalContent=" + originalContent + '}';
    }

    
}
