/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.dao;

import mx.bidg.model.CBranchs;

/**
 *
 * @author sistemask
 */
public interface CBranchsDao extends InterfaceDao<CBranchs> {
    CBranchs findByName(String branchName);
}
