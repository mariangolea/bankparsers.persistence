package org.mariangolea.fintrack.bank.parser.persistence.repository.categories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriesRepository extends JpaRepository<Category, Long>{
    
	Collection<Category> findByParent(Long parentId);
	
	Category findByName(String categoryName);
}
