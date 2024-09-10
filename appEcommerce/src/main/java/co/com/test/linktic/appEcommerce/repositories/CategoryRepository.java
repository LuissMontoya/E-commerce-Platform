package co.com.test.linktic.appEcommerce.repositories;

import org.springframework.stereotype.Repository;

import co.com.test.linktic.appEcommerce.entity.Category;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Integer>{
	
	public Optional<Category> findCategoryById(Integer id);
    List<Category> findAllByOrderByIdDesc();
}