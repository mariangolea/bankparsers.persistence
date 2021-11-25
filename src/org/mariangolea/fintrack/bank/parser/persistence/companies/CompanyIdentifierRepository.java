package org.mariangolea.fintrack.bank.parser.persistence.repository.companies;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyIdentifierRepository  extends JpaRepository<CompanyIdentifier, Long>{
    
}
