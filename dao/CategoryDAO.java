/* By Agus */
package dao;

import dao.dto.CategoryDTO;

public interface CategoryDAO extends CRUD<CategoryDTO>{
    String selectCategory(String message);
    void addCategory();
    void editCategory();
    void  deleteCategory();
}
