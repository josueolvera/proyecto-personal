package mx.bidg.service;

import mx.bidg.model.CRequestsCategories;

import java.util.List;

/**
 * @author Rafael Viveros
 * Created on 19/11/15.
 */
public interface CRequestCategoriesService {
    public CRequestsCategories findById(Integer id);
    public List<CRequestsCategories> findAll();
}
