package mx.bidg.dao.impl;

import mx.bidg.dao.AbstractDao;
import mx.bidg.dao.DistributorCostCenterDao;
import mx.bidg.model.DistributorCostCenter;
import org.hibernate.Criteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.sql.JoinType;

/**
 * Created by Kevin Salvador on 16/03/2017.
 */
@Repository
public class DistributorCostCenterDaoImpl extends AbstractDao<Integer, DistributorCostCenter> implements DistributorCostCenterDao{
    @Override
    public DistributorCostCenter save(DistributorCostCenter entity) {
        persist(entity);
        return entity;
    }

    @Override
    public DistributorCostCenter findById(int id) {
        return getByKey(id);
    }

    @Override
    public List<DistributorCostCenter> findAll() {
        return createEntityCriteria().list();
    }

    @Override
    public DistributorCostCenter update(DistributorCostCenter entity) {
        modify(entity);
        return entity;
    }

    @Override
    public boolean delete(DistributorCostCenter entity) {
        remove(entity);
        return true;
    }

    @Override
    public List<DistributorCostCenter> findByCostCenter(Integer idCostCenter) {
        return createEntityCriteria().add(Restrictions.eq("idCostCenter", idCostCenter)).list();
    }

    @Override
    public DistributorCostCenter findByIdCostCenter(Integer idCostCenter) {
        Criteria criteria = createEntityCriteria();
        return (DistributorCostCenter) criteria.add(Restrictions.eq("idCostCenter",idCostCenter)).uniqueResult();
    }

    @Override
    public List<DistributorCostCenter> findByIdBussinessAndDistributorAndCostCenter(Integer idBussinessLine, Integer idDistributor,Integer idCostCenter) {
        Criteria criteria = createEntityCriteria();
        return criteria.add(Restrictions.eq("idBussinessLine",idBussinessLine)).
                add(Restrictions.eq("idDistributor",idDistributor)).
                add(Restrictions.eq("idCostCenter",idCostCenter)).list();
    }

    @Override
    public List<DistributorCostCenter> findByCostCenterAndDistributors(Integer idCostCenter, List<Integer> idDistributors) {
        Criteria criteria = createEntityCriteria();
        Disjunction disjunctionDistributors = Restrictions.disjunction();

        if (!idDistributors.isEmpty()){
            for (Integer idDistributor : idDistributors){
                disjunctionDistributors.add(Restrictions.eq("idDistributor", idDistributor));
            }
        }

        if (idCostCenter != null){
            criteria.add(Restrictions.eq("idCostCenter",idCostCenter));
        }

        criteria.add(Restrictions.disjunction(disjunctionDistributors));
        return criteria.list();
    }

    @Override
    public List<Integer> getIdsDistributorsByBusinessLine(Integer idBusinessLine) {
        return createEntityCriteria().add(Restrictions.eq("idBussinessLine", idBusinessLine))
                .setProjection(Projections.distinct(Projections.property("idDistributor")))
                .list();
    }

    @Override
    public List<Integer> getIdsCostCentersByBDistributor(Integer idDistributor, List<Integer> idsBussinessLines) {
        Criteria criteria = createEntityCriteria();
        Disjunction disjunctionIdsDistributor = Restrictions.disjunction();

        if (!idsBussinessLines.isEmpty()){
            for (Integer idBussinessLine : idsBussinessLines){
                disjunctionIdsDistributor.add(Restrictions.eq("idBussinessLine", idBussinessLine));
            }
        }
        return criteria.add(Restrictions.eq("idDistributor", idDistributor))
                .add(Restrictions.disjunction(disjunctionIdsDistributor))
                .setProjection(Projections.distinct(Projections.property("idCostCenter")))
                .list();
    }

    @Override
    public List<DistributorCostCenter> getAllByBusinessLineDistributorCC(List<Integer> idsBussinessLines, List<Integer> idsDistributors, List<Integer> idsCC) {
        Criteria criteria = createEntityCriteria();
        Disjunction disjunctionIdsBussinessLines = Restrictions.disjunction();
        Disjunction disjunctionIdsDistributors = Restrictions.disjunction();
        Disjunction disjunctionIdsCCs = Restrictions.disjunction();

        if (!idsBussinessLines.isEmpty()){
            for (Integer idBussinessLine : idsBussinessLines){
                disjunctionIdsBussinessLines.add(Restrictions.eq("idBussinessLine", idBussinessLine));
            }
        }

        if (!idsDistributors.isEmpty()){
            for (Integer idDistributor : idsDistributors){
                disjunctionIdsDistributors.add(Restrictions.eq("idDistributor", idDistributor));
            }
        }

        if (!idsCC.isEmpty()){
            for (Integer idCostCenter : idsCC){
                disjunctionIdsCCs.add(Restrictions.eq("idCostCenter", idCostCenter));
            }
        }

        return criteria
                .add(Restrictions.disjunction(disjunctionIdsBussinessLines))
                .add(Restrictions.disjunction(disjunctionIdsDistributors))
                .add(Restrictions.disjunction(disjunctionIdsCCs))
                .list();
    }

    @Override
    public List<Integer> getIdsCostCentersByBusinessLineDistributorCC(List<Integer> idsBussinessLines, List<Integer> idsDistributors, List<Integer> idsCC) {
        Criteria criteria = createEntityCriteria();
        Disjunction disjunctionIdsBussinessLines = Restrictions.disjunction();
        Disjunction disjunctionIdsDistributors = Restrictions.disjunction();
        Disjunction disjunctionIdsCCs = Restrictions.disjunction();

        if (!idsBussinessLines.isEmpty()){
            for (Integer idBussinessLine : idsBussinessLines){
                disjunctionIdsBussinessLines.add(Restrictions.eq("idBussinessLine", idBussinessLine));
            }
        }

        if (!idsDistributors.isEmpty()){
            for (Integer idDistributor : idsDistributors){
                disjunctionIdsDistributors.add(Restrictions.eq("idDistributor", idDistributor));
            }
        }

        if (!idsCC.isEmpty()){
            for (Integer idCostCenter : idsCC){
                disjunctionIdsCCs.add(Restrictions.eq("idCostCenter", idCostCenter));
            }
        }

        return criteria
                .add(Restrictions.disjunction(disjunctionIdsBussinessLines))
                .add(Restrictions.disjunction(disjunctionIdsDistributors))
                .add(Restrictions.disjunction(disjunctionIdsCCs))
                .setProjection(Projections.distinct(Projections.property("idCostCenter")))
                .list();
    }

    @Override
    public List<Integer> getIdsAccountingAccountsByCostCenter(Integer idCostCenter){

        Criteria criteria = createEntityCriteria();


        LogicalExpression expression = Restrictions.or(Restrictions.eq("idModuleStatus",2), Restrictions.eq("idModuleStatus", 3));

        if (idCostCenter != null){
            criteria.add(Restrictions.eq("idCostCenter",idCostCenter));
        }

        criteria.add(expression);

        return criteria.setProjection(Projections.distinct(Projections.property("idAccountingAccount"))).list();
    }

    @Override
    public DistributorCostCenter findByIdCostCenterAndAA(Integer idCostCenter, Integer idAccountingAccounts) {
        return (DistributorCostCenter) createEntityCriteria()
                .add(Restrictions.eq("idCostCenter", idCostCenter))
                .add(Restrictions.eq("idAccountingAccount", idAccountingAccounts))
                .uniqueResult();
    }

    @Override
    public List<Integer> getIdsDCCByDistributor(Integer idDistributor){
        Criteria criteria = createEntityCriteria();
        if(idDistributor != null){
            criteria.add(Restrictions.eq("idDistributor", idDistributor));
        }
        return criteria.setProjection(Projections.distinct(Projections.property("idDistributorCostCenter")))
                .list();

    }

    @Override
    public List<Integer> idDistributors(List<Integer> idsDCCs) {

        Criteria criteria = createEntityCriteria();
        Disjunction disjunction = Restrictions.disjunction();

        if (!idsDCCs.isEmpty()){
            for (Integer idDistributorCostCenter : idsDCCs){
                disjunction.add(Restrictions.eq("idDistributorCostCenter", idDistributorCostCenter));
            }
        }

        return criteria
                .setProjection(Projections.distinct(Projections.property("idDistributor")))
                .add(disjunction)
                .list();
    }
}
