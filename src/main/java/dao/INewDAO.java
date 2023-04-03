package dao;

import dao.impl.NewDAO;
import model.NewModel;
import pageAndSort.Pageable;

import java.util.List;

public interface INewDAO extends GenericDAO<NewModel> {
    List<NewModel> findAll(Pageable pageable);

    Long save(NewModel newModel);

    List<NewModel> findByCategoryId(int categoryId);

    NewModel findOne(Long id);

    void delete(Long id);
}
