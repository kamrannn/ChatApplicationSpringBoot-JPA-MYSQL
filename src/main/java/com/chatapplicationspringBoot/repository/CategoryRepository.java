package com.chatapplicationspringBoot.repository;

import com.chatapplicationspringBoot.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * The interface Category repository.
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    /**
     * Find all by status list.
     *
     * @param status the status
     * @return the list
     */
    List<Category> findAllByStatus(boolean status);

    /**
     * Find by name category.
     *
     * @param name the name
     * @return the category
     */
    Category findByName(String name);
}
