package mx.bidg.service;

import mx.bidg.model.AccountingAccounts;
import mx.bidg.model.CDistributors;

import java.util.List;

/**
 * @author Rafael Viveros
 * Created on 6/06/16.
 */
public interface AccountingAccountsService {
    AccountingAccounts findByThreeLevels(Integer firstLevel, Integer secondLevel, Integer thirdLevel);
    List<AccountingAccounts> findByFirstLevel(Integer firstLevel);
    List<AccountingAccounts> findBySecondLevel(Integer secondLevel);
    List<AccountingAccounts> findByThirdLevel(Integer thirdLevel);
    List<AccountingAccounts> findAll();
    AccountingAccounts save(AccountingAccounts accountingAccounts);
    AccountingAccounts update(AccountingAccounts accountingAccounts);
    AccountingAccounts findById(Integer idAccountingAccount);
    List<AccountingAccounts> findAllCategories();
}
