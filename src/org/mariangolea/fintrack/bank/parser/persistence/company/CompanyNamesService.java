package org.mariangolea.fintrack.bank.parser.persistence.company;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import org.mariangolea.fintrack.bank.parser.persistence.FintrackEntityBase;
import org.mariangolea.fintrack.company.CompanyIdentifierInterface;
import org.mariangolea.fintrack.company.CompanyInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

@Service
public class CompanyNamesService extends FintrackEntityBase {

	@Autowired
	private CompanyNameRepository companyNamesRepo;

	@Autowired
	private CompanyIdentifierRepository companyIdentifiersRepo;

	public Collection<CompanyIdentifierInterface> getAllCompanyIdentifierStrings() {
		return toInterface(companyIdentifiersRepo.findAll());
	}

	/**
	 * Get all strings that are interpreted as matching a company name.
	 * 
	 * @param companyDisplayName company name
	 */
	public Collection<CompanyIdentifierInterface> getCompanyIdentifierStrings(final String companyDisplayName) {
		CompanyName sample = new CompanyName(companyDisplayName);
		CompanyName match = get(companyNamesRepo.findOne(Example.of(sample)));
		return match == null ? Collections.emptyList() : match.getIdentifiers();
	}

	/**
	 * Get the list of recognised company identifier strings that are found within a
	 * specific transaction description.
	 * 
	 * @param transactionDescription transaction description string, which contains
	 *                               potentially known company identifier strings
	 */
	public Collection<CompanyIdentifierInterface> getMatchingIdentifierStrings(final String transactionDescription) {
		Collection<CompanyIdentifierInterface> identifiers = toInterface(companyIdentifiersRepo.findAll());
		Collection<CompanyIdentifierInterface> matching = new ArrayList<>();
		identifiers.forEach(identifier -> {
			String lower1 = transactionDescription.toLowerCase();
			String lower2 = identifier.getText().toLowerCase();
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
		sample.setText(companyIdentifier);
		Optional<CompanyIdentifier> match = companyIdentifiersRepo.findOne(Example.of(sample));
		return !match.isPresent() ? null : match.get().getCompanyName().getName();
	}

	public Collection<CompanyInterface> getCompanyDisplayNames() {
		return toInterface(companyNamesRepo.findAll());
	}

	public void deleteCompanyName(final CompanyName company) {
		companyNamesRepo.delete(company);
	}

	public void editCompanyName(final String existingName, final String newName) {
		CompanyName sample = new CompanyName(existingName);
		CompanyName company = get(companyNamesRepo.findOne(Example.of(sample)));
		if (company != null) {
			company.setName(newName);
			companyNamesRepo.save(company);
		}
	}

	public void editCompanyIdentifier(final String existingIdentifier, final String newIdentifier) {
		CompanyIdentifier sample = new CompanyIdentifier(existingIdentifier);
		CompanyIdentifier target = companyIdentifiersRepo.findOne(Example.of(sample)).orElse(null);
		if (target != null) {
			target.setText(newIdentifier);
			companyIdentifiersRepo.save(target);
		}
	}

	public void resetCompanyIdentifierStrings(final String displayName,
			final Collection<CompanyIdentifierInterface> newIdentifiers) {
		//make sure database contains identifiers not previously specified.
		for (CompanyIdentifierInterface id : newIdentifiers) {
			CompanyIdentifier identifier = companyIdentifiersRepo.findByText(id.getText()); 
			if (identifier == null) {
				companyIdentifiersRepo.save(new CompanyIdentifier(id.getText()));
			}
		}
		
		CompanyName sample = new CompanyName(displayName);
		CompanyName company = get(companyNamesRepo.findOne(Example.of(sample)));
		if (company != null) {
			Collection<CompanyIdentifier> identifiers = company.getIdentifiersLocal();
			identifiers.clear();
			for (CompanyIdentifierInterface interfaced : newIdentifiers) {
				CompanyIdentifier id = get(companyIdentifiersRepo.findOne(Example.of(new CompanyIdentifier(interfaced))));
				identifiers.add(id);
			}
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
