package dao.impl;

import dao.ICategoryDAO;
import dao.INewDAO;
import mapper.NewMapper;
import model.NewModel;
import org.apache.commons.lang3.StringUtils;
import pageAndSort.Pageable;

import java.util.ArrayList;
import java.util.List;

public class NewDAO extends AbstractDAO<NewModel> implements INewDAO {


    @Override
    public List<NewModel> findAll(Pageable pageable) {
        StringBuilder sql = new StringBuilder("SELECT * FROM news");
        if (pageable.getSorter() != null
                && StringUtils.isNotBlank(pageable.getSorter().getSortName())
                && StringUtils.isNotBlank(pageable.getSorter().getSortBy())) {
            sql.append(" ORDER BY " + pageable.getSorter().getSortName() + " " + pageable.getSorter().getSortBy() + "");
        }
        if (pageable.getPage() != null && pageable.getLimit() != null) {
            sql.append(" LIMIT " + pageable.getOffset() + "," + pageable.getLimit()+"");
        }
        System.out.println(sql.toString());
        return query(sql.toString(), new NewMapper());
    }

    @Override
    public Long save(NewModel newModel) {
        StringBuilder sql;
        NewModel newSave = findOne(newModel.getId());
        if (newSave == null) {
            sql = new StringBuilder("INSERT INTO news (title, content,");
            sql.append(" thumbnail, shortdescription, categoryid, createddate, createdby)");
            sql.append(" VALUES(?, ?, ?, ?, ?, ?, ?)");
            return insert(sql.toString(), newModel.getTitle(), newModel.getContent(),
                    newModel.getThumbnail(), newModel.getShortDescription(), newModel.getCategoryId(),
                    newModel.getCreatedDate(), newModel.getCreatedBy());
        } else {
            sql = new StringBuilder("UPDATE news SET title = ?, thumbnail = ?,");
            sql.append(" shortdescription = ?, content = ?, categoryid = ?,");
            sql.append(" createddate = ?, createdby = ?, modifieddate = ?, modifiedby = ? WHERE id = ?");
            update(sql.toString(), newModel.getTitle(), newModel.getThumbnail(), newModel.getShortDescription(),
                    newModel.getContent(), newModel.getCategoryId(), newModel.getCreatedDate(),
                    newModel.getCreatedBy(), newModel.getModifiedDate(),
                    newModel.getModifiedBy(), newModel.getId());
            return newModel.getId();
        }
    }

    @Override
    public List<NewModel> findByCategoryId(int categoryId) {
        String sql = "SELECT * FROM news WHERE categoryId= ?";
        return query(sql, new NewMapper(), categoryId);
    }

    @Override
    public NewModel findOne(Long id) {
        String sql = "SELECT * FROM news WHERE id= ?";
        List<NewModel> list = query(sql, new NewMapper(), id);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM news WHERE id = ?";
        update(sql, id);
    }
}
