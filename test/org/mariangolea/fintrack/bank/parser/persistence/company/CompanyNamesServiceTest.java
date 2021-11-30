package org.mariangolea.fintrack.bank.parser.persistence.company;

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
import org.mariangolea.fintrack.company.CompanyIdentifierInterface;
import org.mariangolea.fintrack.company.CompanyInterface;
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
		Collection<CompanyIdentifierInterface> preexisting = service.getAllCompanyIdentifierStrings();
		assertNotNull(preexisting);
		assertTrue(preexisting.isEmpty());

		CompanyIdentifier toAdd = new CompanyIdentifier();
		toAdd.setText("AlohaParent");
		idRepo.save(toAdd);

		toAdd = new CompanyIdentifier();
		toAdd.setText("Aloha");
		idRepo.save(toAdd);

		preexisting = service.getAllCompanyIdentifierStrings();
		assertNotNull(preexisting);
		assertEquals(2, preexisting.size());
	}

	@Test
	public void testGetCompanyIdentifierStrings() {
		Collection<CompanyIdentifierInterface> preexisting = service.getCompanyIdentifierStrings("Aloha");
		assertNotNull(preexisting);
		assertTrue(preexisting.isEmpty());

		CompanyIdentifier toAdd = new CompanyIdentifier();
		toAdd.setText("AlohaParent");
		idRepo.save(toAdd);

		toAdd = new CompanyIdentifier();
		toAdd.setText("Aloha");
		idRepo.save(toAdd);

		preexisting = service.getAllCompanyIdentifierStrings();
		assertNotNull(preexisting);
		assertEquals(2, preexisting.size());
	}

	@Test
	public void testGetMatchingIdentifierStrings() {
		Collection<CompanyIdentifierInterface> preexisting = service.getMatchingIdentifierStrings("Aloha");
		assertNotNull(preexisting);
		assertTrue(preexisting.isEmpty());

		CompanyIdentifier toAdd = new CompanyIdentifier();
		toAdd.setText("AlohaParent");
		idRepo.save(toAdd);

		toAdd = new CompanyIdentifier();
		toAdd.setText("Meloha");
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
		toAdd.setText("Aloha Identifier");
		toAdd.setCompanyName(name);
		idRepo.save(toAdd);
		Collection<CompanyIdentifier> identifiers = new ArrayList<>();
		identifiers.add(toAdd);
		name.setIdentifiersLocal(identifiers);
		namesRepo.save(name);

		preexisting = service.getCompanyDisplayName("Aloha Identifier");
		assertNotNull(preexisting);
		assertEquals("Aloha", preexisting);
	}

	@Test
	public void testGeCompanyDisplayNames() {
		Collection<CompanyInterface> preexisting = service.getCompanyDisplayNames();
		assertNotNull(preexisting);
		assertTrue(preexisting.isEmpty());

		CompanyName name = new CompanyName("Aloha");
		namesRepo.save(name);

		name = new CompanyName("Beloha");
		namesRepo.save(name);

		preexisting = service.getCompanyDisplayNames();
		assertNotNull(preexisting);
		assertEquals(2, preexisting.size());
	}

	@Test
	public void testDeleteCompanyName() {
		CompanyName name = new CompanyName("Aloha");
		namesRepo.save(name);

		service.deleteCompanyName(name);

		name = new CompanyName("Aloha");
		Optional<CompanyName> notFound = namesRepo.findOne(Example.of(new CompanyName()));
		assertFalse(notFound.isPresent());
	}

	@Test
	public void testEditCompanyName() {
		CompanyName name = new CompanyName("Aloha");
		namesRepo.save(name);

		service.editCompanyName("Aloha", "Behe");

		name = new CompanyName("Behe");
		Optional<CompanyName> notFound = namesRepo.findOne(Example.of(new CompanyName()));
		assertTrue(notFound.isPresent());
	}

	@Test
	public void testEditCompanyIdentifier() {
		CompanyName name = new CompanyName("Aloha");
		CompanyIdentifier toAdd = new CompanyIdentifier();
		toAdd.setText("Aloha Identifier");
		toAdd.setCompanyName(name);
		idRepo.save(toAdd);
		Collection<CompanyIdentifier> identifiers = new ArrayList<>();
		identifiers.add(toAdd);
		name.setIdentifiersLocal(identifiers);
		namesRepo.save(name);

		service.editCompanyIdentifier("Aloha Identifier", "Bermuda");

		toAdd = new CompanyIdentifier();
		toAdd.setText("Bermuda");
		Optional<CompanyIdentifier> notFound = idRepo.findOne(Example.of(toAdd));
		assertTrue(notFound.isPresent());
	}

	@Test
	public void testResetCompanyIdentifierStrings() {
		CompanyName name = new CompanyName("Aloha");
		Collection<CompanyIdentifierInterface> identifiers = new ArrayList<>();
		name.setIdentifiers(identifiers);

		CompanyIdentifier toAdd = new CompanyIdentifier();
		toAdd.setText("Aloha Identifier");
		toAdd.setCompanyName(name);
		identifiers.add(toAdd);
		idRepo.save(toAdd);

		toAdd = new CompanyIdentifier();
		toAdd.setText("Aloha Identifier 33");
		toAdd.setCompanyName(name);
		identifiers.add(toAdd);
		idRepo.save(toAdd);

		namesRepo.save(name);

		identifiers = new ArrayList<>();
		toAdd = new CompanyIdentifier();
		toAdd.setText("Beta");
		toAdd.setCompanyName(name);
		identifiers.add(toAdd);
		idRepo.save(toAdd);

		toAdd = new CompanyIdentifier();
		toAdd.setText("Delta");
		toAdd.setCompanyName(name);
		identifiers.add(toAdd);
		idRepo.save(toAdd);

		service.resetCompanyIdentifierStrings("Aloha", identifiers);

		CompanyName found = get(namesRepo.findOne(Example.of(new CompanyName("Aloha"))));
		assertNotNull(found);
		assertEquals(2, found.getIdentifiers().size());
		for (CompanyIdentifierInterface id : found.getIdentifiers()) {
			switch (id.getText()) {
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
		
		CompanyName name = new CompanyName("Aloha");
		namesRepo.save(name);
		
		preexisting = service.hasCompanyDisplayName("Aloha");
		assertTrue(preexisting);
	}

}
