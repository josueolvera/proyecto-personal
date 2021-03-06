package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.CArticlesDao;
import mx.bidg.model.CArticles;
import mx.bidg.model.CProducts;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Rafael Viveros
 * Created on 9/12/15.
 */
@SuppressWarnings("unchecked")
@Repository
public class CArticlesDaoImpl extends AbstractDao<Integer, CArticles> implements CArticlesDao {
    @Override
    public CArticles findByProduct(CProducts product) {
        return (CArticles) createEntityCriteria()
                .add(Restrictions.eq("idProduct", product.getIdProduct())).uniqueResult();
    }

    @Override
    public List<CArticles> findByArticleCategory(Integer idArticleCategory) {
        return createEntityCriteria()
                .add(Restrictions.eq("idArticleCategory", idArticleCategory)).list();
    }

    @Override
    public CArticles save(CArticles entity) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public CArticles findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<CArticles> findAll() {
        return createEntityCriteria().list();
    }

    @Override
    public CArticles update(CArticles entity) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public boolean delete(CArticles entity) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public List<CArticles> findAllWithIdProduct() {
        return createEntityCriteria().add(Restrictions.isNotNull("idProduct")).list();
    }
}
