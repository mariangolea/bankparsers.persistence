package org.mariangolea.fintrack.bank.parser.persistence.company;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

@Service
public class CompanyNamesService {

	@Autowired
	private CompanyNameRepository companyNamesRepo;

	@Autowired
	private CompanyIdentifierRepository companyIdentifiersRepo;

	public Collection<CompanyIdentifier> getAllCompanyIdentifierStrings() {
		return companyIdentifiersRepo.findAll();
	}

	/**
	 * Get all strings that are interpreted as matching a company name.
	 * 
	 * @param companyDisplayName company name
	 */
	public Collection<CompanyIdentifier> getCompanyIdentifierStrings(final String companyDisplayName) {
		CompanyName sample = new CompanyName();
		sample.setName(companyDisplayName);
		Optional<CompanyName> match = companyNamesRepo.findOne(Example.of(sample));
		return !match.isPresent() ? Collections.emptyList() : match.get().getIdentifiers();
	}

	/**
	 * Get the list of recognized company identifier strings that are found within a
	 * specific transaction description.
	 * 
	 * @param transactionDescription transaction description string, which contains
	 *                               potentially known company identifier strings
	 */
	public Collection<CompanyIdentifier> getMatchingIdentifierStrings(final String transactionDescription) {
		List<CompanyIdentifier> identifiers = companyIdentifiersRepo.findAll();
		Collection<CompanyIdentifier> matching = new ArrayList<>();
		identifiers.forEach(identifier -> {
			String lower1 = transactionDescription.toLowerCase();
			String lower2 = identifier.getName().toLowerCase();
			if (lower1.contains(lower2)) {
				matching.add(identifier);
			}
		});

		return matching;
	}

	/**
	 * Get the company name that this identifier string is associated with.
	 * 
	 * @param companyIdentifier company identifier string
	 */
	public String getCompanyDisplayName(final String companyIdentifier) {
		CompanyIdentifier sample = new CompanyIdentifier();
		sample.setName(companyIdentifier);
		Optional<CompanyIdentifier> match = companyIdentifiersRepo.findOne(Example.of(sample));
		return !match.isPresent() ? null : match.get().getCompanyName().getName();
	}

	public Collection<CompanyName> getCompanyDisplayNames() {
		return companyNamesRepo.findAll();
	}

	public void deleteCompanyName(final CompanyName company) {
		companyNamesRepo.delete(company);
	}

	public void editCompanyName(final String existingName, final String newName) {
		CompanyName sample = new CompanyName();
		sample.setName(existingName);
		CompanyName company = companyNamesRepo.findOne(Example.of(sample)).orElse(null);
		if (company != null) {
			company.setName(newName);
			companyNamesRepo.save(company);
		}
	}

	public void editCompanyIdentifier(final String existingIdentifier, final String newIdentifier) {
		CompanyIdentifier sample = new CompanyIdentifier();
		sample.setName(existingIdentifier);
		CompanyIdentifier target = companyIdentifiersRepo.findOne(Example.of(sample)).orElse(null);
		if (target != null) {
			target.setName(newIdentifier);
			companyIdentifiersRepo.save(target);
		}
	}

	public void resetCompanyIdentifierStrings(final String displayName,
			final Collection<CompanyIdentifier> newIdentifiers) {
		CompanyName sample = new CompanyName();
		sample.setName(displayName);
		CompanyName company = companyNamesRepo.findOne(Example.of(sample)).orElse(null);
		if (company != null) {
			company.getIdentifiers().clear();
			company.getIdentifiers().addAll(newIdentifiers);
			companyNamesRepo.save(company);
		}
	}

	public boolean hasCompanyDisplayName(final String companyDisplayName) {
		CompanyName sample = new CompanyName();
		sample.setName(companyDisplayName);
		CompanyName company = companyNamesRepo.findOne(Example.of(sample)).orElse(null);
		return company != null;
	}
}
