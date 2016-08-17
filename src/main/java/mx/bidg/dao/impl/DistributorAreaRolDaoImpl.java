package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.DistributorAreaRolDao;
import mx.bidg.model.CZonas;
import mx.bidg.model.DistributorAreaRol;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by gerardo8 on 15/08/16.
 */
@Repository
@SuppressWarnings("unchecked")
public class DistributorAreaRolDaoImpl extends AbstractDao<Integer, DistributorAreaRol> implements DistributorAreaRolDao {
    @Override
    public DistributorAreaRol save(DistributorAreaRol entity) {
        persist(entity);
        return entity;
    }

    @Override
    public DistributorAreaRol findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<DistributorAreaRol> findAll() {
        return createEntityCriteria()
                .list();
    }

    @Override
    public DistributorAreaRol update(DistributorAreaRol entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(DistributorAreaRol entity) {
        remove(entity);
        return true;
    }
}
