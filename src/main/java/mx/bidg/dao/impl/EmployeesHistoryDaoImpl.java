package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.EmployeesHistoryDao;
import mx.bidg.model.CDistributors;
import mx.bidg.model.EmployeesHistory;
import org.hibernate.Criteria;
import org.hibernate.criterion.*;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.FetchMode;

/**
 * Created by gerardo8 on 24/06/16.
 */
@SuppressWarnings("unchecked")
@Repository
public class EmployeesHistoryDaoImpl extends AbstractDao<Integer, EmployeesHistory> implements EmployeesHistoryDao {
    @Override
    public EmployeesHistory save(EmployeesHistory entity) {
        persist(entity);
        return entity;
    }

    @Override
    public EmployeesHistory findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<EmployeesHistory> findAll() {
        return createEntityCriteria()
                .add(Restrictions.eq("hStatus",1))
                .list();
    }

    @Override
    public EmployeesHistory update(EmployeesHistory entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(EmployeesHistory entity) {
        remove(entity);
        return true;
    }

    @Override
    public List<EmployeesHistory> findByDistributorAndRegionAndBranchAndAreaAndRoleAndStartDateAndEndDate
        (Integer status, List<CDistributors> distributors, Integer idRegion, Integer idZona, Integer idBranch, Integer idArea, Integer idRole,
         String fullname, String rfc, String startDate, String endDate, Integer idEmployee) {
        Criteria criteria = createEntityCriteria();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        Disjunction dis = Restrictions.disjunction();
        Disjunction distribuidorDisjunction = Restrictions.disjunction();

        if (startDate != null && endDate != null) {
            LocalDateTime startLocalDateTime = LocalDateTime.parse(startDate + " 00:00:00",formatter);
            LocalDateTime endLocalDateTime = LocalDateTime.parse(endDate + " 23:59:59",formatter);
            criteria.add(Restrictions.between("joinDate",startLocalDateTime,endLocalDateTime));
        }

        for(CDistributors distributor : distributors ){
            distribuidorDisjunction.add(Restrictions.eq("idDistributor", distributor.getIdDistributor()));
        }
        criteria.add(distribuidorDisjunction);

        if (idRegion != null) {
            criteria.add(Restrictions.eq("idRegion",idRegion));
        }
        if (idZona != null) {
            criteria.add(Restrictions.eq("idZona",idZona));
        }
        if (idBranch != null) {
            criteria.add(Restrictions.eq("idBranch",idBranch));
        }
        if (idArea != null) {
            criteria.add(Restrictions.eq("idArea",idArea));
        }
        if (idRole != null) {
            criteria.add(Restrictions.eq("idRole",idRole));
        }
        if (idEmployee != null) {
            criteria.add(Restrictions.eq("idEmployee",idEmployee));
        }

        if(fullname != null){
            dis.add(Restrictions.ilike("firstName",fullname, MatchMode.ANYWHERE));
            dis.add(Restrictions.ilike("middleName",fullname, MatchMode.ANYWHERE));
            dis.add(Restrictions.ilike("parentalLast",fullname, MatchMode.ANYWHERE));
            dis.add(Restrictions.ilike("motherLast",fullname, MatchMode.ANYWHERE));
            criteria.add(dis);
        }

        if (rfc != null){
            criteria.add(Restrictions.ilike("rfc", rfc, MatchMode.ANYWHERE));
        }

        if (status == 0) {
           criteria.add(Restrictions.eq("hStatus",1)); 
        }
        else if (status == 1){
            Disjunction or = Restrictions.disjunction();
            criteria.add(Restrictions.eq("hStatus",1));
            criteria.add(Restrictions.disjunction()
                    .add(Restrictions.eq("idActionType",1))
                    .add(Restrictions.eq("idActionType",3))
                    .add(Restrictions.eq("idActionType",4)));
        }
        else{
            criteria.add(Restrictions.eq("hStatus",1));
            criteria.add(Restrictions.eq("idActionType",2));
        }
        

        return criteria.addOrder(Order.asc("rfc")).list();
    }

    @Override
    public List<EmployeesHistory> findByIdEmployee(Integer idEmployee) {
        return createEntityCriteria()
                .add(Restrictions.eq("idEmployee",idEmployee))
                .list();
    }

    @Override
    public EmployeesHistory findByIdEmployeeAndLastRegister(Integer idEmployee) {
        return (EmployeesHistory) createEntityCriteria()
                .add(Restrictions.eq("idEmployee",idEmployee))
                .add(Restrictions.eq("hStatus",1))
                .uniqueResult();
    }

    @Override
    public List<EmployeesHistory> findByDistributor(Integer idDistributor) {
        Criteria criteria = createEntityCriteria();
        return criteria.add(Restrictions.eq("idDistributor",idDistributor))
                .add(Restrictions.eq("hStatus",1)).list();
    }

    @Override
    public EmployeesHistory findIdEmployee(Integer idEmployee) {
        Criteria criteria = createEntityCriteria();
        return (EmployeesHistory) criteria.add(Restrictions.eq("idEmployee",idEmployee))
                .add(Restrictions.eq("hStatus",1)).uniqueResult();
    }

    @Override
    public EmployeesHistory findIdDistributor(Integer idDistributor) {
        Criteria criteria = createEntityCriteria();
        return (EmployeesHistory) criteria.add(Restrictions.eq("idDistributor",idDistributor))
                .add(Restrictions.eq("hStatus",1)).uniqueResult();
    }

    @Override
    public List<EmployeesHistory> findIdEmployeeAndIdDistributor(Integer idEmployee,Integer idDistributor) {
        Criteria criteria = createEntityCriteria();
        return criteria
                .add(Restrictions.eq("idEmployee",idEmployee))
                .add(Restrictions.eq("idDistributor",idDistributor))
                .add(Restrictions.eq("hStatus",1)).list();
    }

    @Override
    public EmployeesHistory findEmployeeByClaveSap(String claveSap) {
        return (EmployeesHistory) createEntityCriteria()
                .add(Restrictions.eq("claveSap",claveSap))
                .add(Restrictions.eq("hStatus",1))
                .uniqueResult();
    }

    @Override
    public EmployeesHistory findByDistributorAreaRolLastRegister(Integer idDistributor, Integer idArea, Integer idRole) {
        Criteria criteria = createEntityCriteria();
        Criterion activo = Restrictions.eq("idActionType", 1);
        Criterion modificacion = Restrictions.eq("idActionType", 3);
        Criterion reactivacion = Restrictions.eq("idActionType", 4);

        LogicalExpression expression = Restrictions.or(activo, modificacion);
        LogicalExpression expression2 = Restrictions.or(reactivacion, expression);


        return (EmployeesHistory) criteria
                .add(expression2)
                .add(Restrictions.eq("idDistributor", idDistributor))
                .add(Restrictions.eq("idArea", idArea))
                .add(Restrictions.eq("idRole", idRole))
                .add(Restrictions.eq("hStatus",1))
                .uniqueResult();
    }
}
