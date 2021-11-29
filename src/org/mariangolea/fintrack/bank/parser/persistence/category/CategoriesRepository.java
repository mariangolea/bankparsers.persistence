package org.mariangolea.fintrack.bank.parser.persistence.category;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriesRepository extends JpaRepository<Category, Long>{
    
	Collection<Category> findByParent(Category parent);
	
	Category findByName(String categoryName);
}
