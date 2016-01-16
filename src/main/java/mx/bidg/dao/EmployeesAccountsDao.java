/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.dao;

import java.util.List;
import mx.bidg.model.Employees;
import mx.bidg.model.EmployeesAccounts;

/**
 *
 * @author sistemask
 */
public interface EmployeesAccountsDao extends InterfaceDao<EmployeesAccounts> {
    
    public List<EmployeesAccounts> findByEmployee(Employees e);
    
}
