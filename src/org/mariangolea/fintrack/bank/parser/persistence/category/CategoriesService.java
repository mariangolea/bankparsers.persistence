package org.mariangolea.fintrack.bank.parser.persistence.category;

import java.util.ArrayList;
import java.util.Collection;

import org.mariangolea.fintrack.bank.parser.persistence.FintrackEntityBase;
import org.mariangolea.fintrack.category.CategoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoriesService extends FintrackEntityBase {

	@Autowired
	private CategoriesRepository categoriesRepo;

	public static final String UNCATEGORIZED = "Uncategorized";

	/**
	 * Get the list of categories which have no parent category.
	 */
	public Collection<CategoryInterface> getTopMostCategories() {
		Collection<CategoryInterface> topMost = new ArrayList<>();
		topMost.addAll(categoriesRepo.findByParent(null));
		return topMost;
	}

	/**
	 * When removing a category, it is important to find its direct children and
	 * change their parent to the parent of the category to be removed. <br>
	 * We're just eliminating one artificial grouping of several categories, so they
	 * are available to the higher parent.
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

		Category parent = toRemove.getParent();

		Collection<Category> children = toRemove.getChildrenLocal();
		if (children != null && !children.isEmpty()) {
			for (Category child : children) {
				child.setParent(parent);
			}
			categoriesRepo.saveAll(children);
		}

		categoriesRepo.deleteById(toRemove.getId());

		return true;
	}
}
