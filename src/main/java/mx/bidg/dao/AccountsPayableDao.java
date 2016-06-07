/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.dao;

import java.util.List;
import mx.bidg.model.AccountsPayable;

/**
 *
 * @author sistemask
 */
public interface AccountsPayableDao extends InterfaceDao<AccountsPayable> {
    List<AccountsPayable> findByFolio(String folio);
    Boolean deleteByFolio(String folio);
    List<AccountsPayable> findAccountsofDay();
}
