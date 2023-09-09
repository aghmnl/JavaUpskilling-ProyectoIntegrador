/* By Agus */
package dao.dto;

public class CategoryDTO {
    private String categoryName;

    public CategoryDTO(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public String toString() {
        return this.categoryName;
    }

}
