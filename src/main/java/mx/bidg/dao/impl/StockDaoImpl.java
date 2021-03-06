package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.StockDao;
import mx.bidg.model.DwEnterprises;
import mx.bidg.model.Stocks;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Rafael Viveros
 * Created on 8/12/15.
 */
@SuppressWarnings("unchecked")
@Repository
public class StockDaoImpl extends AbstractDao<Integer, Stocks> implements StockDao {
    @Override
    public Stocks save(Stocks entity) {
        persist(entity);
        return entity;
    }

    @Override
    public Stocks findById(int id) {
        return (Stocks) createEntityCriteria()
                .add(Restrictions.eq("idStock", id))
                .setFetchMode("dwEnterprises", FetchMode.JOIN)
                .setFetchMode("propertiesList", FetchMode.JOIN)
                .uniqueResult();
    }

    @Override
    public Stocks findSimpleById(Integer idStock) {
        return (Stocks) createEntityCriteria()
                .add(Restrictions.eq("idStock", idStock))
                .uniqueResult();
    }

    @Override
    public List<Stocks> findAll() {
        return  createEntityCriteria()
                .setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY)
                .createCriteria("dwEnterprises")
                .list();
    }

    @Override
    public List<Stocks> findByDwEnterprises(List<DwEnterprises> dwEnterprises) {
        if (dwEnterprises.isEmpty()) {
            return new ArrayList<>();
        }

        Criteria criteria = createEntityCriteria();
        Disjunction disjunction = Restrictions.disjunction();

        for(DwEnterprises dwEnterprise : dwEnterprises) {
            disjunction.add(Restrictions.eq("idDwEnterprises",dwEnterprise.getIdDwEnterprise()));
        }

        criteria.add(disjunction);

        return criteria.list();
    }

    @Override
    public List<Stocks> findByDistributor(Integer idDistributor) {
        return  createEntityCriteria()
                .setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY)
                .createCriteria("dwEnterprises")
                .add(Restrictions.eq("idDistributor", idDistributor))
                .list();
    }

    @Override
    public Stocks update(Stocks entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(Stocks entity) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public Stocks updateEntity(Stocks stock) {
        globalTracer("UPDATE", stock);
        Query query = getSession().createQuery("" +
                "UPDATE Stocks SET idArticleStatus = :idStatus, " +
                "serialNumber = :serialNumber, stockFolio = :folio WHERE idStock = :id"
        );
        query.setInteger("idStatus", stock.getIdArticleStatus());
        query.setString("serialNumber", stock.getSerialNumber());
        query.setString("folio", stock.getStockFolio());
        query.setInteger("id", stock.getIdStock());
        query.executeUpdate();
        return stock;
    }
}
