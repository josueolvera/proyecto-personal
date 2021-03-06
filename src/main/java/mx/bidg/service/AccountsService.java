/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.service;

import java.util.List;
import mx.bidg.model.Accounts;
import mx.bidg.model.Providers;
import mx.bidg.model.Users;

/**
 *
 * @author sistemask
 */
public interface AccountsService {
    List<Accounts> findByProvider(Providers provider);
    List<Accounts> findByUser(Users user);
    Accounts addAccountForProvider(Providers provider, Accounts account);
    Boolean delete(Accounts account);
    Accounts save(Accounts account);
    void low(Integer idAccount);
    List<Accounts> findAccountsActive();
    Accounts findById(Integer idAccount);
    Accounts update(Accounts accounts);
}
