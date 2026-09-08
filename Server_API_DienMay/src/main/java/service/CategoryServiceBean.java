package service;

import dao.CategoryDAO;
import entity.Category;
import jakarta.ejb.Stateless;

import java.util.List;

@Stateless
public class CategoryServiceBean {

    private final CategoryDAO dao = new CategoryDAO();

    public List<Category> getAllCategories() {
        return dao.getAllCategories();
    }

    public Category getCategoryById(int id) {
        return dao.getCategoryById(id);
    }

    public void addCategory(Category category) {
        dao.addCategory(category);
    }

    public void updateCategory(Category category) {
        dao.updateCategory(category);
    }

    public void deleteCategory(int id) {
        dao.deleteCategory(id);
    }

    public List<Category> searchCategory(String keyword) {
        return dao.searchCategory(keyword);
    }
}