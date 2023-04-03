package dao;

import model.CategoryModel;

import java.util.List;

public interface ICategoryDAO extends GenericDAO<CategoryModel> {
    Long findIdByCategoryCode(String code);
}
