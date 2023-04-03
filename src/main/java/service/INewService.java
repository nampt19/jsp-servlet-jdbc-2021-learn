package service;

import model.NewModel;
import pageAndSort.Pageable;

import java.util.List;

public interface INewService {

    List<NewModel> findAll(Pageable pageable);

    List<NewModel> findByCategoryId(int id);

    List<NewModel> findByCategoryCode(String code);

    NewModel insert(NewModel newModel);

    NewModel update(NewModel newModel);

    void delete(Long id);
}
