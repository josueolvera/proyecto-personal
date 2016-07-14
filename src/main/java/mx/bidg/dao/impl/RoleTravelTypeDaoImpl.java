package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.RoleTravelTypeDao;
import mx.bidg.model.RoleTravelType;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by josueolvera on 13/07/16.
 */
@Repository
public class RoleTravelTypeDaoImpl extends AbstractDao<Integer, RoleTravelType> implements RoleTravelTypeDao {

    @Override
    public RoleTravelType save(RoleTravelType entity) {
        persist(entity);
        return entity;
    }

    @Override
    public RoleTravelType findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<RoleTravelType> findAll() {
        return (List<RoleTravelType>) createEntityCriteria().list();
    }

    @Override
    public RoleTravelType update(RoleTravelType entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(RoleTravelType entity) {
        remove(entity);
        return true;
    }
}