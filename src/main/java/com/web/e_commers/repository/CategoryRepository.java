package com.web.e_commers.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.web.e_commers.model.Category;

public interface CategoryRepository extends JpaRepository<Category,Long> {
	// CategoryRepository.java
	@Query("Select c from Category c Where c.name =:name")
	List<Category> findAllByName(@Param("name") String name);

	@Query("Select c from Category c Where c.name =:name And c.parentCategory.name =:parentCategoryName")
	List<Category> findAllByNameAndParent(@Param("name") String name, @Param("parentCategoryName") String parentCategoryName);
}