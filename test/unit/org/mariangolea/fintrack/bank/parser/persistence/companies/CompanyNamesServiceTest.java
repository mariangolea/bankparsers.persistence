package org.mariangolea.fintrack.bank.parser.persistence.repository.companies;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collection;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Test;
import org.mariangolea.fintrack.bank.parser.persistence.repository.BaseDataJPATest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = { CompanyNamesService.class, CompanyNameRepository.class,
		CompanyIdentifierRepository.class })
public class CompanyNamesServiceTest extends BaseDataJPATest {

	@Autowired
	private CompanyNamesService service;
	
	@Autowired
	private CompanyIdentifierRepository idRepo;
	
	@Autowired
	private CompanyNameRepository namesRepo;
	
	
	@Test
	public void testGetAllCompanyIdentifierStrings() {
		Collection<CompanyIdentifier> preexisting = service.getAllCompanyIdentifierStrings();
		assertNotNull(preexisting);
		assertTrue(preexisting.isEmpty());
		
		CompanyIdentifier toAdd = new CompanyIdentifier();
		toAdd.setName("AlohaParent");
		idRepo.save(toAdd);
		
		toAdd = new CompanyIdentifier();
		toAdd.setName("Aloha");
		idRepo.save(toAdd);
		
		preexisting = service.getAllCompanyIdentifierStrings();
		assertNotNull(preexisting);
		assertEquals(2, preexisting.size());
	}
	
	@Test
	public void testGetCompanyIdentifierStrings() {
		Collection<CompanyIdentifier> preexisting = service.getCompanyIdentifierStrings("Aloha");
		assertNotNull(preexisting);
		assertTrue(preexisting.isEmpty());
		
		CompanyIdentifier toAdd = new CompanyIdentifier();
		toAdd.setName("AlohaParent");
		idRepo.save(toAdd);
		
		toAdd = new CompanyIdentifier();
		toAdd.setName("Aloha");
		idRepo.save(toAdd);
		
		preexisting = service.getAllCompanyIdentifierStrings();
		assertNotNull(preexisting);
		assertEquals(2, preexisting.size());
	}
	
	@Test
	public void testGetMatchingIdentifierStrings() {
		Collection<CompanyIdentifier> preexisting = service.getMatchingIdentifierStrings("Aloha");
		assertNotNull(preexisting);
		assertTrue(preexisting.isEmpty());
		
		CompanyIdentifier toAdd = new CompanyIdentifier();
		toAdd.setName("AlohaParent");
		idRepo.save(toAdd);
		
		toAdd = new CompanyIdentifier();
		toAdd.setName("Meloha");
		idRepo.save(toAdd);
		
		//one transaction will most likely only have one single company identifier string, not two...
		preexisting = service.getMatchingIdentifierStrings("Pre AlohaParent Meloha Post Transaction description");
		assertNotNull(preexisting);
		assertEquals(2, preexisting.size());
	}
	
	@Test
	public void testGeCompanyDisplayName() {
		String preexisting = service.getCompanyDisplayName("Aloha");
		assertNotNull(preexisting);
		assertTrue(preexisting.isEmpty());
		
		CompanyName name = new CompanyName();
		name.setName("Aloha");
		CompanyIdentifier toAdd = new CompanyIdentifier();
		toAdd.setName("Aloha Identifier");
		toAdd.setCompanyName(name);
		idRepo.save(toAdd);
		name.setIdentifiers(Arrays.asList(toAdd));
		namesRepo.save(name);
		
		//one transaction will most likely only have one single company identifier string, not two...
		preexisting = service.getMatchingIdentifierStrings("Pre AlohaParent Meloha Post Transaction description");
		assertNotNull(preexisting);
		assertEquals(2, preexisting.size());
	}

}
