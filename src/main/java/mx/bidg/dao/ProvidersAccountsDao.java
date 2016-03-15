/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.dao;

import java.util.List;
import mx.bidg.model.Accounts;
import mx.bidg.model.Providers;
import mx.bidg.model.ProvidersAccounts;

/**
 *
 * @author sistemask
 */
public interface ProvidersAccountsDao extends InterfaceDao<ProvidersAccounts> {
    
    public List<ProvidersAccounts> findByProvider(Providers p);
    
    public List<ProvidersAccounts> findByAccount(Accounts a);
    
}
