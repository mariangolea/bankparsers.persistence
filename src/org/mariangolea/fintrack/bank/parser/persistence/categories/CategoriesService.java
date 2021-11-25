package org.mariangolea.fintrack.bank.parser.persistence.repository.categories;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

@Service
public class CategoriesService {

	@Autowired
	private CategoriesRepository categoriesRepo;

	public static final String UNCATEGORIZED = "Uncategorized";

	public Category getParent(final String categoryName) {
		Category match = categoriesRepo.findByName(categoryName);
		Long parentId = match == null ? null : match.getParent();
		if (parentId == null) {
			return null;
		}

		return getCategory(categoriesRepo.findById(parentId));
	}

	public Collection<Category> getCategoryNames() {
		return categoriesRepo.findAll();
	}

	/**
	 * Get the list of categories which have no parent category.
	 */
	public Collection<Category> getTopMostCategories() {
		return categoriesRepo.findByParent(null);
	}

	/**
	 * Get the first level children of a specific category
	 * 
	 * @param categoryName category name
	 * @return list of first level children
	 */
	public Collection<Category> getSubCategories(final String categoryName) {
		Long categoryID = categoriesRepo.findByName(categoryName).getId();
		Category sample = new Category();
		sample.setParent(categoryID);
		return categoriesRepo.findAll(Example.of(sample));
	}

	/**
	 * When removing a category, it is important to find its direct children and
	 * change their parent to the parent of the category to be removed.
	 * 
	 * @param parentCategory
	 * @param category
	 * @return false if category was not found
	 */
	public boolean removeCategory(final String categoryName) {
		Category toRemove = categoriesRepo.findByName(categoryName);
		if (toRemove == null) {
			return false;
		}
		
		Long parentId = toRemove.getParent();
		
		Collection<Category> children = getSubCategories(categoryName);
		if (children != null && !children.isEmpty()) {
			for (Category child : children) {
				child.setParent(parentId);
			}
		}
		
		categoriesRepo.saveAll(children);
		categoriesRepo.deleteById(toRemove.getId());
		
		return true;
	}

	private Category getCategory(Optional<Category> optional) {
		if (optional == null || optional.isEmpty()) {
			return null;
		}
		return optional.get();
	}
}
