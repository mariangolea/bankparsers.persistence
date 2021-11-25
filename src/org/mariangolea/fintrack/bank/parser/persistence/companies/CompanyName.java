package org.mariangolea.fintrack.bank.parser.persistence.repository.companies;

import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "companynames")
public class CompanyName implements Serializable{
	private static final long serialVersionUID = 8119331797537769458L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "display_name", unique=true, nullable = false)
    private String name;
    
    @OneToMany(cascade = CascadeType.ALL)
    private Collection<CompanyIdentifier> identifiers;

    public CompanyName() {
    }

    public CompanyName(Long id, String name, Collection<CompanyIdentifier> identifiers) {
        this.id = id;
        this.name = Objects.requireNonNull(name);
        this.identifiers = identifiers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = Objects.requireNonNull(name);
    }

    public Collection<CompanyIdentifier> getIdentifiers() {
        return identifiers;
    }

    public void setIdentifiers(Collection<CompanyIdentifier> identifiers) {
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
}
