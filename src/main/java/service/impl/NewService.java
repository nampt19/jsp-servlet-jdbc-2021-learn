package service.impl;

import dao.ICategoryDAO;
import dao.INewDAO;
import model.NewModel;
import pageAndSort.Pageable;
import service.INewService;

import javax.inject.Inject;
import java.util.List;

public class NewService implements INewService {
    @Inject
    INewDAO newDAO;

    @Inject
    ICategoryDAO categoryDAO;

    @Override
    public List<NewModel> findAll(Pageable pageable) {

        return newDAO.findAll(pageable);
    }

    @Override
    public List<NewModel> findByCategoryId(int id) {
        return newDAO.findByCategoryId(id);
    }

    @Override
    public List<NewModel> findByCategoryCode(String code) {
        Long categoryId = categoryDAO.findIdByCategoryCode(code);

        if (categoryId <= 0) return null;

        return newDAO.findByCategoryId(Math.toIntExact(categoryId));
    }

    @Override
    public NewModel insert(NewModel newModel) {
        Long newId = newDAO.save(newModel);
        return newDAO.findOne(newId);
    }

    @Override
    public NewModel update(NewModel newModel) {
        NewModel newUpdate = newDAO.findOne(newModel.getId());
        if (newUpdate == null) {
            return null;
        }
        newUpdate.setTitle(newModel.getTitle());
        newUpdate.setContent(newModel.getContent());
        newUpdate.setCategoryId(newModel.getCategoryId());
        newUpdate.setModifiedDate(newModel.getModifiedDate());
        newUpdate.setModifiedBy(newModel.getModifiedBy());

        Long newId = newDAO.save(newUpdate);
        return newDAO.findOne(newId);
    }

    @Override
    public void delete(Long id) {
        newDAO.delete(id);
    }


}
