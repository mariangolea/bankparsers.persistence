package org.mariangolea.fintrack.bank.parser.persistence.category;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Table;

import org.mariangolea.fintrack.bank.parser.persistence.FintrackEntityBase;
import org.mariangolea.fintrack.category.CategoryInterface;

@Entity
@Table(name = "categories")
public class Category extends FintrackEntityBase implements Serializable, CategoryInterface {

	private static final long serialVersionUID = 5859495735283407656L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "display_name", unique = true, nullable = false)
	private String name;

	@OneToMany(mappedBy = "parent")
	@OrderColumn
//	@JoinColumn(name = "parent_id")
	private List<Category> children = new ArrayList<>();

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "parent_id",insertable=false,updatable=false)
	private Category parent;

	public Category() {
	}

	Category(CategoryInterface category) {
		name = adjustString(category.getName(), 250);
		if (category != null) {
			if (category.getParent() != null) {
				parent = new Category(category.getParent());
			}
			Collection<CategoryInterface> childrenToAdd = category.getChildren();
			if (childrenToAdd != null && !childrenToAdd.isEmpty()) {
				for (CategoryInterface child : childrenToAdd) {
					children.add(new Category(child));
				}
			}
		}
	}

	public Category(String name, Category parent) {
		this.name = adjustString(name);
		this.parent = parent;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = Objects.requireNonNull(name);
	}

	public Category getParent() {
		return parent;
	}

	public Long getParentId() {
		return parent == null ? null : parent.id;
	}

	public void setParent(Category parent) {
		this.parent = parent;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 97 * hash + Objects.hashCode(this.id);
		hash = 97 * hash + Objects.hashCode(this.name);
		hash = 97 * hash + Objects.hashCode(this.parent);
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Category other = (Category) obj;
		if (!Objects.equals(this.name, other.name)) {
			return false;
		}
		if (!Objects.equals(this.id, other.id)) {
			return false;
		}
		if (!Objects.equals(this.parent, other.parent)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Category{" + "id=" + id + ", name=" + name + ", parent=" + parent + '}';
	}

	@Override
	public void setParentCategory(CategoryInterface parent) {
		parent = new Category(parent);
		parent.addChildren(this);
	}

	@Override
	public Collection<CategoryInterface> getChildren() {
		if (children == null) {
			return null;
		}
		Collection<CategoryInterface> ret = new ArrayList<>();
		ret.addAll(children);
		return ret;
	}

	Collection<Category> getChildrenLocal() {
		return children;
	}

	void addChildrenLocal(Category... children) {
		if (children != null) {
			for (Category child : children) {
				this.children.add(child);
			}
		}
	}

	@Override
	public void addChildren(CategoryInterface... children) {
		if (children != null) {
			for (CategoryInterface child : children) {
				this.children.add(new Category(child));
			}
		}
	}

}
