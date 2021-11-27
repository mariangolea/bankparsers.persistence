package org.mariangolea.fintrack.bank.parser.persistence.companies;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mariangolea.fintrack.bank.parser.persistence.BaseDataJPATest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
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

		// one transaction will most likely only have one single company identifier
		// string, not two...
		preexisting = service.getMatchingIdentifierStrings("Pre AlohaParent Meloha Post Transaction description");
		assertNotNull(preexisting);
		assertEquals(2, preexisting.size());
	}

	@Test
	public void testGeCompanyDisplayName() {
		String preexisting = service.getCompanyDisplayName("Aloha");
		assertNull(preexisting);

		CompanyName name = new CompanyName();
		name.setName("Aloha");
		CompanyIdentifier toAdd = new CompanyIdentifier();
		toAdd.setName("Aloha Identifier");
		toAdd.setCompanyName(name);
		idRepo.save(toAdd);
		Collection<CompanyIdentifier> identifiers = new ArrayList<>();
		identifiers.add(toAdd);
		name.setIdentifiers(identifiers);
		namesRepo.save(name);

		preexisting = service.getCompanyDisplayName("Aloha Identifier");
		assertNotNull(preexisting);
		assertEquals("Aloha", preexisting);
	}

	@Test
	public void testGeCompanyDisplayNames() {
		Collection<CompanyName> preexisting = service.getCompanyDisplayNames();
		assertNotNull(preexisting);
		assertTrue(preexisting.isEmpty());

		CompanyName name = new CompanyName();
		name.setName("Aloha");
		namesRepo.save(name);

		name = new CompanyName();
		name.setName("Beloha");
		namesRepo.save(name);

		preexisting = service.getCompanyDisplayNames();
		assertNotNull(preexisting);
		assertEquals(2, preexisting.size());
	}

	@Test
	public void testDeleteCompanyName() {
		CompanyName name = new CompanyName();
		name.setName("Aloha");
		namesRepo.save(name);

		service.deleteCompanyName(name);

		name = new CompanyName();
		name.setName("Aloha");
		Optional<CompanyName> notFound = namesRepo.findOne(Example.of(new CompanyName()));
		assertFalse(notFound.isPresent());
	}

	@Test
	public void testEditCompanyName() {
		CompanyName name = new CompanyName();
		name.setName("Aloha");
		namesRepo.save(name);

		service.editCompanyName("Aloha", "Behe");

		name = new CompanyName();
		name.setName("Behe");
		Optional<CompanyName> notFound = namesRepo.findOne(Example.of(new CompanyName()));
		assertTrue(notFound.isPresent());
	}

	@Test
	public void testEditCompanyIdentifier() {
		CompanyName name = new CompanyName();
		name.setName("Aloha");
		CompanyIdentifier toAdd = new CompanyIdentifier();
		toAdd.setName("Aloha Identifier");
		toAdd.setCompanyName(name);
		idRepo.save(toAdd);
		Collection<CompanyIdentifier> identifiers = new ArrayList<>();
		identifiers.add(toAdd);
		name.setIdentifiers(identifiers);
		namesRepo.save(name);

		service.editCompanyIdentifier("Aloha Identifier", "Bermuda");

		toAdd = new CompanyIdentifier();
		toAdd.setName("Bermuda");
		Optional<CompanyIdentifier> notFound = idRepo.findOne(Example.of(toAdd));
		assertTrue(notFound.isPresent());
	}

	@Test
	public void testResetCompanyIdentifierStrings() {
		CompanyName name = new CompanyName();
		name.setName("Aloha");
		Collection<CompanyIdentifier> identifiers = new ArrayList<>();
		name.setIdentifiers(identifiers);

		CompanyIdentifier toAdd = new CompanyIdentifier();
		toAdd.setName("Aloha Identifier");
		toAdd.setCompanyName(name);
		identifiers.add(toAdd);
		idRepo.save(toAdd);

		toAdd = new CompanyIdentifier();
		toAdd.setName("Aloha Identifier 33");
		toAdd.setCompanyName(name);
		identifiers.add(toAdd);
		idRepo.save(toAdd);

		namesRepo.save(name);

		identifiers = new ArrayList<>();
		toAdd = new CompanyIdentifier();
		toAdd.setName("Beta");
		toAdd.setCompanyName(name);
		identifiers.add(toAdd);
		idRepo.save(toAdd);

		toAdd = new CompanyIdentifier();
		toAdd.setName("Delta");
		toAdd.setCompanyName(name);
		identifiers.add(toAdd);
		idRepo.save(toAdd);

		service.resetCompanyIdentifierStrings("Aloha", identifiers);

		name = new CompanyName();
		name.setName("Aloha");
		Optional<CompanyName> found = namesRepo.findOne(Example.of(new CompanyName()));
		assertTrue(found.isPresent());
		name = found.get();
		assertEquals(2, name.getIdentifiers().size());
		for (CompanyIdentifier id : name.getIdentifiers()) {
			switch (id.getName()) {
			case "Beta":
			case "Delta":
				break;
			default:
				assertTrue(false);
				break;
			}
		}
	}
	
	@Test
	public void testHasCompanyDisplayName() {
		boolean preexisting = service.hasCompanyDisplayName("Aloha");
		assertTrue(!preexisting);
		
		CompanyName name = new CompanyName();
		name.setName("Aloha");
		namesRepo.save(name);
		
		preexisting = service.hasCompanyDisplayName("Aloha");
		assertTrue(preexisting);
	}

}
