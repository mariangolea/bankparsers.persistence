package org.mariangolea.fintrack.bank.parser.persistence.company;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.mariangolea.fintrack.bank.parser.persistence.FintrackEntityBase;
import org.mariangolea.fintrack.company.CompanyIdentifierInterface;

@Entity
@Table(name = "companyidentifiers")
public class CompanyIdentifier extends FintrackEntityBase implements Serializable, CompanyIdentifierInterface {
	private static final long serialVersionUID = 6107837566518229694L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "identifier", unique = true, nullable = false)
	private String text;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "company_name")
	private CompanyName companyName;

	public CompanyIdentifier() {
	}

	public CompanyIdentifier(String text) {
		this.text = adjustString(text);
	}

	CompanyIdentifier(CompanyIdentifierInterface identifier) {
		this.text = identifier == null ? "" : identifier.getText();
		this.text = adjustString(text);
	}
	
	CompanyIdentifier(String text, CompanyName company) {
		this.text = adjustString(text);
		this.companyName = company;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String getText() {
		return text;
	}

	public CompanyName getCompanyName() {
		return companyName;
	}

	public void setCompanyName(CompanyName companyName) {
		this.companyName = companyName;
	}

	@Override
	public void setText(String text) {
		this.text = adjustString(text);
	}

	@Override
	public int hashCode() {
		return Objects.hash(text);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CompanyIdentifier other = (CompanyIdentifier) obj;
		return Objects.equals(text, other.text);
	}

	@Override
	public String toString() {
		return "CompanyIdentifier{" + "id=" + id + ", text=" + text + '}';
	}
}
