package org.mariangolea.fintrack.bank.parser.persistence.company;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyIdentifierRepository extends JpaRepository<CompanyIdentifier, Long> {
	//Regex is not actually supported since database behavior differs too much.
//	@Query("select t from CompanyIdentifier t where t.text like %?1")
//	public Collection<CompanyIdentifier> findAllByTextContained(String containingText);
	
	public CompanyIdentifier findByText(String text);
}
