package org.mariangolea.fintrack.bank.parser.persistence.company;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.mariangolea.fintrack.bank.parser.persistence.FintrackEntityBase;
import org.mariangolea.fintrack.company.CompanyIdentifierInterface;
import org.mariangolea.fintrack.company.CompanyInterface;

@Entity
@Table(name = "companynames")
public class CompanyName extends FintrackEntityBase implements Serializable, CompanyInterface {
	private static final long serialVersionUID = 8119331797537769458L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "display_name", unique = true, nullable = false)
	private String name;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "companyName", orphanRemoval = true, fetch = FetchType.LAZY)
	private Collection<CompanyIdentifier> identifiers;

	public CompanyName() {
	}

	public CompanyName(String name) {
		this.name = adjustString(name);
	}

	public CompanyName(String name, Collection<CompanyIdentifier> identifiers) {
		this.name = adjustString(name);
		this.identifiers = identifiers;
	}

	CompanyName(CompanyInterface company) {
		this.name = company.getName();
		addIdentifiers(company.getIdentifiers());
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = adjustString(name);
	}

	Collection<CompanyIdentifier> getIdentifiersLocal() {
		return identifiers;
	}

	void setIdentifiersLocal(Collection<CompanyIdentifier> identifiers) {
		this.identifiers = identifiers;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, identifiers, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CompanyName other = (CompanyName) obj;
		return Objects.equals(id, other.id) && Objects.equals(identifiers, other.identifiers)
				&& Objects.equals(name, other.name);
	}

	@Override
	public String toString() {
		String identifiersString = identifiers == null ? "null" : identifiers.toString();
		return "CompanyName{" + "name=" + name + ", identifier=" + identifiersString + '}';
	}

	@Override
	public void setIdentifiers(Collection<CompanyIdentifierInterface> identifiers) {
		if (this.identifiers != null) {
			this.identifiers.clear();
		} else {
			this.identifiers = new ArrayList<>();
		}
		addIdentifiers(identifiers);
	}

	@Override
	public Collection<CompanyIdentifierInterface> getIdentifiers() {
		return new ArrayList<>(identifiers);
	}

	private void addIdentifiers(Collection<CompanyIdentifierInterface> identifiers) {
		for (CompanyIdentifierInterface identifier : identifiers) {
			this.identifiers.add(new CompanyIdentifier(identifier));
		}
	}
}
