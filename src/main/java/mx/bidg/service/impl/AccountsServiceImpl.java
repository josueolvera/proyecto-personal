/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import mx.bidg.dao.AccountsDao;
import mx.bidg.dao.ProvidersAccountsDao;
import mx.bidg.exceptions.ValidationException;
import mx.bidg.model.Accounts;
import mx.bidg.model.Providers;
import mx.bidg.model.ProvidersAccounts;
import mx.bidg.model.Users;
import mx.bidg.service.AccountsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AccountsServiceImpl implements AccountsService {
    
    @Autowired
    private AccountsDao accountsDao;
    
    @Autowired
    private ProvidersAccountsDao providersAccountsDao;

    @Override
    public List<Accounts> findByProvider(Providers provider) {
        List<Accounts> list = new ArrayList<>();
        List<ProvidersAccounts> providersAccounts = providersAccountsDao.findByProvider(provider.getIdProvider());
        for(ProvidersAccounts providersAccount : providersAccounts) {
            list.add(providersAccount.getAccount());
        }
        return list;
    }

    @Override
    public List<Accounts> findByUser(Users user) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Accounts addAccountForProvider(Providers provider, Accounts account) {
        ProvidersAccounts providerAccount = new ProvidersAccounts();
        account = accountsDao.save(account);
        providerAccount.setAccount(account);
        providerAccount.setProvider(provider);
        providersAccountsDao.save(providerAccount);
        return account;
    }

    @Override
    public Boolean delete(Accounts account) {
        accountsDao.delete(account);
        return true;
    }

    @Override
    public Accounts save(Accounts account) {   
        accountsDao.save(account);
        return account;
    }

    @Override
    public void low(Integer idAccount) {
        Accounts accounts = accountsDao.findById(idAccount);
        accounts.setDeleteDay(LocalDateTime.now());
        accountsDao.update(accounts);
    }

    @Override
    public List<Accounts> findAccountsActive() {
        return accountsDao.accountsActives();
    }

    @Override
    public Accounts findById(Integer idAccount) {
        return accountsDao.findById(idAccount);
    }

    @Override
    public Accounts update(Accounts accounts) {
        return accountsDao.update(accounts);
    }
}
