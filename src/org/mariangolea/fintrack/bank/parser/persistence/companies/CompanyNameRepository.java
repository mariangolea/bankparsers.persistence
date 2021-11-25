package org.mariangolea.fintrack.bank.parser.persistence.companies;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyNameRepository extends JpaRepository<CompanyName, Long>{
}
