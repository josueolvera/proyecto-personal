/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.service;

import java.util.List;
import java.util.Set;

import mx.bidg.model.CBudgetSubcategories;

/**
 *
 * @author sistemask
 */
public interface CBudgetSubcategoriesService {
    List<CBudgetSubcategories> findAll();
    Set<CBudgetSubcategories> getByBudgetCategory(Integer idBudgetCategory);
    CBudgetSubcategories save(CBudgetSubcategories budgetSubcategory);
}
