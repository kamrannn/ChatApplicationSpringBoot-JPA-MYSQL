package com.chatapplicationspringBoot.repository;

import com.chatapplicationspringBoot.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findAllByStatus(boolean status);
    Category findByName(String name);
}
