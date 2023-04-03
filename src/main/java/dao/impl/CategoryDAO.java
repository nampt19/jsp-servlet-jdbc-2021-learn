package dao.impl;

import dao.ICategoryDAO;
import mapper.CategoryMapper;
import model.CategoryModel;

import java.util.List;

public class CategoryDAO extends AbstractDAO<CategoryModel> implements ICategoryDAO {

    @Override
    public Long findIdByCategoryCode(String code) {
        String sql = "select * from category where code = ?";
        List<CategoryModel> list = query(sql, new CategoryMapper(), code);
        if (list.isEmpty()) return -1l;
        else return list.get(0).getId();
    }
}
