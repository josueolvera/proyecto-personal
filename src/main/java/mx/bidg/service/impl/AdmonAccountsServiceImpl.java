package mx.bidg.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import mx.bidg.dao.AdmonAccountsDao;
import mx.bidg.model.AdmonAccounts;
import mx.bidg.service.AdmonAccountsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by jolvera on 14/04/2016.
 */
@Service
@Transactional
public class AdmonAccountsServiceImpl implements AdmonAccountsService {

    @Autowired
    AdmonAccountsDao admonAccountsDao;

    @Override
    public List<AdmonAccounts> findAll() {
        return admonAccountsDao.findAll();
    }
}
