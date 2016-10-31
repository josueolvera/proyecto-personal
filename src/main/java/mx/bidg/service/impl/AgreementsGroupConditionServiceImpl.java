package mx.bidg.service.impl;

import mx.bidg.dao.*;
import mx.bidg.model.*;
import mx.bidg.service.AgreementsGroupConditionService;
import mx.bidg.service.BonusCommisionableEmployeeService;
import mx.bidg.service.MultilevelEmployeeService;
import org.exolab.castor.xml.descriptors.ListClassDescriptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by josueolvera on 2/09/16.
 */
@Service
@Transactional
public class AgreementsGroupConditionServiceImpl implements AgreementsGroupConditionService {

    @Autowired
    AgreementsGroupConditionDao agreementsGroupConditionDao;

    @Autowired
    CommissionAmountGroupDao commissionAmountGroupDao;

    @Autowired
    BonusCommisionableEmployeeDao bonusCommisionableEmployeeDao;

    @Autowired
    EmployeesDao employeesDao;

    @Autowired
    DwEmployeesDao dwEmployeesDao;

    @Autowired
    MultilevelEmployeeDao multilevelEmployeeDao;

    @Override
    public AgreementsGroupCondition save(AgreementsGroupCondition agreementsGroupCondition) {
        return agreementsGroupConditionDao.save(agreementsGroupCondition);
    }

    @Override
    public AgreementsGroupCondition update(AgreementsGroupCondition agreementsGroupCondition) {
        return agreementsGroupConditionDao.update(agreementsGroupCondition);
    }

    @Override
    public AgreementsGroupCondition findById(Integer idGroupCondition) {
        return agreementsGroupConditionDao.findById(idGroupCondition);
    }

    @Override
    public boolean delete(AgreementsGroupCondition agreementsGroupCondition) {
        agreementsGroupConditionDao.delete(agreementsGroupCondition);
        return true;
    }

    @Override
    public List<AgreementsGroupCondition> findAll() {
        return agreementsGroupConditionDao.findAll();
    }

    @Override
    public List<CommissionAmountGroup> setTabulator(List<AgreementsGroupCondition> agreementsGroupConditionList) {

        for (AgreementsGroupCondition aGC: agreementsGroupConditionList){
                AgreementsGroupCondition groupCondition = agreementsGroupConditionDao.getTabulator(aGC.getOrder(),aGC);

            if (groupCondition.getTypeOperation() == 1){
                //1 significa que calcule la comision del monto comisionable
                List<CommissionAmountGroup> amountGroups = commissionAmountGroupDao.getComissionsByConditon(groupCondition);

                for (CommissionAmountGroup cAGroup :  amountGroups){
                    cAGroup.setTabulator(groupCondition.getTabulator());
                    BigDecimal divisor = new BigDecimal(100);
                    BigDecimal comission = groupCondition.getTabulator().divide(divisor);
                    cAGroup.setCommission(cAGroup.getAmount().multiply(comission));
                    commissionAmountGroupDao.update(cAGroup);
                }
            } else if(groupCondition.getTypeOperation() == 2) {
                //2 significa que calcule bono cumplimiento
                List<CommissionAmountGroup> requestGroups = commissionAmountGroupDao.getBonusByConditon(groupCondition);

                for (CommissionAmountGroup cAGroup :  requestGroups){

                    BonusCommisionableEmployee bonusCommisionableEmployee = new BonusCommisionableEmployee();
                    bonusCommisionableEmployee.setIdEmployee(cAGroup.getIdEmployee());
                    bonusCommisionableEmployee.setBonusAmount(groupCondition.getTabulator());
                    bonusCommisionableEmployee.setcCommissionBonus(CCommissionBonus.BONO_POR_CUMPLIMIENTO);

                    bonusCommisionableEmployeeDao.save(bonusCommisionableEmployee);
//                    cAGroup.setTabulator(groupCondition.getAmountMin());
//                    BigDecimal comission = groupCondition.getTabulator();
//                    cAGroup.setCommission(comission);
//                    commissionAmountGroupDao.update(cAGroup);
                }
            }
//            } else if (groupCondition.getTypeOperation() == 3){
//                //3 signifique que calcule el alcance de la sucursal
//                List<CommissionAmountGroup> scopeGroups = commissionAmountGroupDao.getScopeByConditon(groupCondition);
//
//                for(CommissionAmountGroup commissionAmountGroup : scopeGroups){
//                    commissionAmountGroup.setTabulator(groupCondition.getTabulator());
//                    BigDecimal divide = new BigDecimal(100);
//                    BigDecimal comission = groupCondition.getTabulator().divide(divide);
//                    commissionAmountGroup.setCommission(commissionAmountGroup.getAmount().multiply(comission));
//                    commissionAmountGroupDao.update(commissionAmountGroup);
//                }
//            }
        }

        return commissionAmountGroupDao.findAll();
    }

    @Override
    public List<AgreementsGroupCondition> conditions(Integer idAg, Integer idDateCalculation) {
        return agreementsGroupConditionDao.conditionList(idAg, idDateCalculation);
    }

    @Override
    public List<AgreementsGroupCondition> findByGroupCondition(Integer idAg) {
        return agreementsGroupConditionDao.listByAgreementGroup(idAg);
    }

    @Override
    public AgreementsGroupCondition updateStatus(Integer idGroupCondition, boolean statusBoolean) {
        return agreementsGroupConditionDao.updateStatus(idGroupCondition, statusBoolean);
    }

    @Override
    public AgreementsGroupCondition getFinalOrder(Integer idAg) {
        return agreementsGroupConditionDao.getFinalOrder(idAg);
    }

    @Override
    public List<CommissionAmountGroup> obtainCommissionByGoalBranchToZona(List<AgreementsGroupCondition> agreementsGroupConditionList) {
        for (AgreementsGroupCondition aGC: agreementsGroupConditionList){
            AgreementsGroupCondition groupCondition = agreementsGroupConditionDao.getTabulator(aGC.getOrder(),aGC);

            List<CommissionAmountGroup> zonasList = commissionAmountGroupDao.findByGroupZonalAndZona();

            for (CommissionAmountGroup commissionAmountGroup : zonasList){
                List<CommissionAmountGroup> commissionAmountGroupList = commissionAmountGroupDao.obtainBranchByZonaAndCondition(commissionAmountGroup.getIdZona(), groupCondition);
                if( commissionAmountGroup.getIdZona() == 10){
                    if (commissionAmountGroupList.size() == 5 ){
                        BigDecimal defaultValue = new BigDecimal(0.40);
                        commissionAmountGroup.setTabulator(defaultValue.divide(new BigDecimal(100)));
                        BigDecimal divisor = new BigDecimal(100);
                        BigDecimal percentage = defaultValue.divide(divisor);
                        BigDecimal comission = percentage.multiply(commissionAmountGroup.getAmount());
                        commissionAmountGroup.setCommission(comission);
                        commissionAmountGroupDao.update(commissionAmountGroup);
                    }  else if (commissionAmountGroupList.size() >= 2) {
                        int size = commissionAmountGroupList.size();
                        BigDecimal value = new BigDecimal(size);
                        BigDecimal tabulatorBranch = groupCondition.getTabulator().multiply(value);
                        BigDecimal tabulator = tabulatorBranch.divide(new BigDecimal(100));
                        commissionAmountGroup.setTabulator(tabulator);
                        BigDecimal divisor = new BigDecimal(100);
                        BigDecimal percentage = tabulatorBranch.divide(divisor);
                        BigDecimal commissionByBranch = percentage.multiply(commissionAmountGroup.getAmount());
                        commissionAmountGroup.setCommission(commissionByBranch);
                        commissionAmountGroupDao.update(commissionAmountGroup);
                    }
                }else {
                    if (commissionAmountGroupList.size() >= 6){
                        BigDecimal defaultValue = new BigDecimal(0.40);
                        commissionAmountGroup.setTabulator(defaultValue.divide(new BigDecimal(100)));
                        BigDecimal divisor = new BigDecimal(100);
                        BigDecimal percentage = defaultValue.divide(divisor);
                        BigDecimal comission = percentage.multiply(commissionAmountGroup.getAmount());
                        commissionAmountGroup.setCommission(comission);
                        commissionAmountGroupDao.update(commissionAmountGroup);
                    } else if (commissionAmountGroupList.size() >= 2) {
                        int size = commissionAmountGroupList.size();
                        BigDecimal value = new BigDecimal(size);
                        BigDecimal tabulatorBranch = groupCondition.getTabulator().multiply(value);
                        BigDecimal tabulator = tabulatorBranch.divide(new BigDecimal(100));
                        commissionAmountGroup.setTabulator(tabulator);
                        BigDecimal divisor = new BigDecimal(100);
                        BigDecimal percentage = tabulatorBranch.divide(divisor);
                        BigDecimal commissionByBranch = percentage.multiply(commissionAmountGroup.getAmount());
                        commissionAmountGroup.setCommission(commissionByBranch);
                        commissionAmountGroupDao.update(commissionAmountGroup);
                    }
                }
            }
        }

        return commissionAmountGroupDao.findAll();
    }

    @Override
    public List<CommissionAmountGroup> obtainCommissionByGoalBranchToRegion(List<AgreementsGroupCondition>  agreementsGroupConditionList) {
        for (AgreementsGroupCondition aGC: agreementsGroupConditionList){
            AgreementsGroupCondition groupCondition = agreementsGroupConditionDao.getTabulator(aGC.getOrder(),aGC);

            List<CommissionAmountGroup> regionList = commissionAmountGroupDao.findByGroupRegionslAndRegion();

            for (CommissionAmountGroup commissionAmountGroups : regionList){
                List<CommissionAmountGroup> branchsList = commissionAmountGroupDao.obtainBranchByRegionAndCondition(commissionAmountGroups.getIdRegion(), groupCondition);
                if (branchsList.size() >= 25){
                    BigDecimal defaultValue = new BigDecimal(25);
                    BigDecimal tabulator = groupCondition.getTabulator().multiply(defaultValue);
                    commissionAmountGroups.setTabulator(tabulator);
                    BigDecimal divisor = new BigDecimal(100);
                    BigDecimal percentage = tabulator.divide(divisor);
                    BigDecimal comission = percentage.multiply(commissionAmountGroups.getAmount());
                    commissionAmountGroups.setCommission(comission);
                    commissionAmountGroupDao.update(commissionAmountGroups);
                }else {
                    int size = branchsList.size();
                    BigDecimal value = new BigDecimal(size);
                    BigDecimal tabulatorBranch = groupCondition.getTabulator().multiply(value);
                    commissionAmountGroups.setTabulator(tabulatorBranch);
                    BigDecimal divisor = new BigDecimal(100);
                    BigDecimal percentage = tabulatorBranch.divide(divisor);
                    BigDecimal commissionByBranch = percentage.multiply(commissionAmountGroups.getAmount());
                    commissionAmountGroups.setCommission(commissionByBranch);
                    commissionAmountGroupDao.update(commissionAmountGroups);
                }
//                BigDecimal salaryRegionalSur = new BigDecimal(56092.80);
//                if (commissionAmountGroups.getIdRegion() == 2){
//                    if (commissionAmountGroups.getAmount().doubleValue() >= 18000000){
//                        commissionAmountGroups.setCommission(salaryRegionalSur);
//                        commissionAmountGroupDao.update(commissionAmountGroups);
//                    }else {
//                        if (branchsList.size() >= 25){
//                            BigDecimal defaultValue = new BigDecimal(25);
//                            BigDecimal tabulator = groupCondition.getTabulator().multiply(defaultValue);
//                            commissionAmountGroups.setTabulator(tabulator);
//                            BigDecimal divisor = new BigDecimal(100);
//                            BigDecimal percentage = tabulator.divide(divisor);
//                            BigDecimal comission = percentage.multiply(commissionAmountGroups.getAmount());
//                            BigDecimal adjustment = comission.add(new BigDecimal(12500));
//                            BigDecimal totalCommission = salaryRegionalSur.subtract(adjustment);
//                            commissionAmountGroups.setCommission(totalCommission);
//                            commissionAmountGroupDao.update(commissionAmountGroups);
//                        }else {
//                            int size = branchsList.size();
//                            BigDecimal value = new BigDecimal(size);
//                            BigDecimal tabulatorBranch = groupCondition.getTabulator().multiply(value);
//                            commissionAmountGroups.setTabulator(tabulatorBranch);
//                            BigDecimal divisor = new BigDecimal(100);
//                            BigDecimal percentage = tabulatorBranch.divide(divisor);
//                            BigDecimal commissionByBranch = percentage.multiply(commissionAmountGroups.getAmount());
//                            BigDecimal adjustment = commissionByBranch.add(new BigDecimal(12500));
//                            BigDecimal totalCommission = salaryRegionalSur.subtract(adjustment);
//                            commissionAmountGroups.setCommission(totalCommission);
//                            commissionAmountGroupDao.update(commissionAmountGroups);
//                        }
//                    }
//                }else {
//                    if (branchsList.size() >= 25){
//                        BigDecimal defaultValue = new BigDecimal(25);
//                        BigDecimal tabulator = groupCondition.getTabulator().multiply(defaultValue);
//                        commissionAmountGroups.setTabulator(tabulator);
//                        BigDecimal divisor = new BigDecimal(100);
//                        BigDecimal percentage = tabulator.divide(divisor);
//                        BigDecimal comission = percentage.multiply(commissionAmountGroups.getAmount());
//                        commissionAmountGroups.setCommission(comission);
//                        commissionAmountGroupDao.update(commissionAmountGroups);
//                    }else {
//                        int size = branchsList.size();
//                        BigDecimal value = new BigDecimal(size);
//                        BigDecimal tabulatorBranch = groupCondition.getTabulator().multiply(value);
//                        commissionAmountGroups.setTabulator(tabulatorBranch);
//                        BigDecimal divisor = new BigDecimal(100);
//                        BigDecimal percentage = tabulatorBranch.divide(divisor);
//                        BigDecimal commissionByBranch = percentage.multiply(commissionAmountGroups.getAmount());
//                        commissionAmountGroups.setCommission(commissionByBranch);
//                        commissionAmountGroupDao.update(commissionAmountGroups);
//                    }
//                }
            }
        }

        return commissionAmountGroupDao.findAll();
    }

    @Override
    public List<CommissionAmountGroup> obtainCommissionByGoalBranchToDistributor(List<AgreementsGroupCondition> agreementsGroupConditionList) {
        for (AgreementsGroupCondition aGC: agreementsGroupConditionList){
            AgreementsGroupCondition groupCondition = agreementsGroupConditionDao.getTabulator(aGC.getOrder(),aGC);

            List<CommissionAmountGroup> distributorList = commissionAmountGroupDao.findByGroupComercialAndDistributor();

            for (CommissionAmountGroup commissionAmountGroups : distributorList){
                List<CommissionAmountGroup> branchsList = commissionAmountGroupDao.obtainBranchByDistributorAndCondition(commissionAmountGroups.getIdDistributor(), groupCondition);
                if (branchsList.size() >= 40){
                    BigDecimal defaultValue = new BigDecimal(40);
                    BigDecimal tabulator = groupCondition.getTabulator().multiply(defaultValue);
                    commissionAmountGroups.setTabulator(tabulator);
                    BigDecimal divisor = new BigDecimal(100);
                    BigDecimal percentage = tabulator.divide(divisor);
                    BigDecimal comission = percentage.multiply(commissionAmountGroups.getAmount());
                    commissionAmountGroups.setCommission(comission);
                    commissionAmountGroupDao.update(commissionAmountGroups);
                }else {
                    int size = branchsList.size();
                    BigDecimal value = new BigDecimal(size);
                    BigDecimal tabulatorBranch = groupCondition.getTabulator().multiply(value);
                    commissionAmountGroups.setTabulator(tabulatorBranch);
                    BigDecimal divisor = new BigDecimal(100);
                    BigDecimal percentage = tabulatorBranch.divide(divisor);
                    BigDecimal commissionByBranch = percentage.multiply(commissionAmountGroups.getAmount());
                    commissionAmountGroups.setCommission(commissionByBranch);
                    commissionAmountGroupDao.update(commissionAmountGroups);
                }
            }
        }

        return commissionAmountGroupDao.findAll();
    }

    @Override
    public List<CommissionAmountGroup> obtainCommissionByReprocessingToAuxiluiar(List<AgreementsGroupCondition> agreementsGroupConditionList) {
        for (AgreementsGroupCondition aGC : agreementsGroupConditionList) {
            AgreementsGroupCondition groupCondition = agreementsGroupConditionDao.getTabulator(aGC.getOrder(), aGC);

            List<CommissionAmountGroup> reprocessingGroups = commissionAmountGroupDao.getReprocessingByCondition(groupCondition);

            for (CommissionAmountGroup commissionAxiliarGroup : reprocessingGroups) {
                commissionAxiliarGroup.setTabulator(groupCondition.getTabulator());
                BigDecimal divide = new BigDecimal(100);
                BigDecimal tabulatorAuxiliar = groupCondition.getTabulator().divide(divide);
                commissionAxiliarGroup.setCommission(commissionAxiliarGroup.getAmount().multiply(tabulatorAuxiliar));
                commissionAmountGroupDao.update(commissionAxiliarGroup);
            }
        }
        return commissionAmountGroupDao.findAll();
    }

    @Override
    public List<CommissionAmountGroup> obtainCommissionByGoalBranchToBranchManagaer(List<AgreementsGroupCondition> agreementsGroupConditions) {
        for (AgreementsGroupCondition aGC : agreementsGroupConditions) {
            AgreementsGroupCondition groupCondition = agreementsGroupConditionDao.getTabulator(aGC.getOrder(), aGC);

            List<CommissionAmountGroup> scopeGroups = commissionAmountGroupDao.getScopeByConditon(groupCondition);

            for(CommissionAmountGroup commissionAmountGroup : scopeGroups){
                commissionAmountGroup.setTabulator(groupCondition.getTabulator());
                BigDecimal divide = new BigDecimal(100);
                BigDecimal tabulatorBranchManager = groupCondition.getTabulator().divide(divide);
                commissionAmountGroup.setCommission(commissionAmountGroup.getAmount().multiply(tabulatorBranchManager));
                commissionAmountGroupDao.update(commissionAmountGroup);
            }
        }
        return commissionAmountGroupDao.findAll();
    }

    @Override
    public List<CommissionAmountGroup> obtainCommissionByPromotor(List<AgreementsGroupCondition> agreementsGroupConditions) {
        for (AgreementsGroupCondition aGC: agreementsGroupConditions) {
            AgreementsGroupCondition groupCondition = agreementsGroupConditionDao.getTabulator(aGC.getOrder(), aGC);

            if (groupCondition.getTypeOperation() == 1) {
                List<CommissionAmountGroup> amountGroups = commissionAmountGroupDao.getComissionsByConditon(groupCondition);

                for (CommissionAmountGroup cAGroup : amountGroups) {
                    cAGroup.setTabulator(groupCondition.getTabulator());
                    BigDecimal divisor = new BigDecimal(100);
                    BigDecimal comission = groupCondition.getTabulator().divide(divisor);
                    cAGroup.setCommission(cAGroup.getAmount().multiply(comission));
                    commissionAmountGroupDao.update(cAGroup);
                }
            }else if (groupCondition.getTypeOperation() == 5){
                List<CommissionAmountGroup> amountGroups = commissionAmountGroupDao.getComissionsByConditon(groupCondition);
                BigDecimal defaultValue = new BigDecimal(300000.00);

                for (CommissionAmountGroup cAGroup : amountGroups) {
                    cAGroup.setTabulator(groupCondition.getTabulator());
//                    BigDecimal divisor = new BigDecimal(100);
//                    BigDecimal comission = groupCondition.getTabulator().divide(divisor);
                    cAGroup.setCommission(groupCondition.getTabulator());
                    commissionAmountGroupDao.update(cAGroup);
                }
            }
        }
        return commissionAmountGroupDao.findAll();
    }

    @Override
    public List<CommissionAmountGroup> bonusJoinDate(LocalDateTime joinDateFrom, LocalDateTime toDateFrom) {

        List<Employees> employeesList = employeesDao.findByJoinDate(joinDateFrom, toDateFrom);
        List<DwEmployees> dwEmployeesList = dwEmployeesDao.findByRolePromotor(employeesList);

        for (DwEmployees dwEmployees : dwEmployeesList){
            CommissionAmountGroup newEmployee = commissionAmountGroupDao.getOnlyDataOfGroupNineTeen(dwEmployees.getIdEmployee());
            if (newEmployee !=null){
                BonusCommisionableEmployee bonusJoinDate = new BonusCommisionableEmployee();
                BigDecimal bonus = new BigDecimal(375);
                bonusJoinDate.setBonusAmount(bonus);
                bonusJoinDate.setcCommissionBonus(CCommissionBonus.BONO_POR_NUEVO_INGRESO);
                bonusJoinDate.setIdEmployee(newEmployee.getIdEmployee());

                bonusCommisionableEmployeeDao.save(bonusJoinDate);

            } else{
                CommissionAmountGroup commissionAmountGroup = new CommissionAmountGroup();
                commissionAmountGroup.setClaveSap(dwEmployees.getEmployee().getClaveSap());
                commissionAmountGroup.setIdAg(19);
                commissionAmountGroup.setApplicationsNumber(new BigDecimal(0));
                commissionAmountGroup.setAmount(new BigDecimal(0));
                commissionAmountGroup.setIdEmployee(dwEmployees.getEmployee().getIdEmployee());
                commissionAmountGroup.setGroupName("PROMOTOR TODOS");
                commissionAmountGroup =commissionAmountGroupDao.save(commissionAmountGroup);

                BonusCommisionableEmployee bonusJoinDate = new BonusCommisionableEmployee();
                BigDecimal bonus = new BigDecimal(375);
                bonusJoinDate.setBonusAmount(bonus);
                bonusJoinDate.setcCommissionBonus(CCommissionBonus.BONO_POR_NUEVO_INGRESO);
                bonusJoinDate.setIdEmployee(dwEmployees.getEmployee().getIdEmployee());

                bonusCommisionableEmployeeDao.save(bonusJoinDate);
            }

            List<AgreementsGroupCondition> groupConditionList = agreementsGroupConditionDao.getRuleTransport();

            for (AgreementsGroupCondition condition : groupConditionList){
                CommissionAmountGroup commissionAmountGroup = commissionAmountGroupDao.getGroupNineTeenAndConditons(dwEmployees.getIdEmployee(), condition);
                if (commissionAmountGroup != null){
                    BonusCommisionableEmployee bonusCommisionableEmployee = new BonusCommisionableEmployee();
                    bonusCommisionableEmployee.setIdEmployee(commissionAmountGroup.getIdEmployee());
                    bonusCommisionableEmployee.setBonusAmount(condition.getTabulator());
                    bonusCommisionableEmployee.setcCommissionBonus(CCommissionBonus.APOYO_PASAJE);

                    bonusCommisionableEmployeeDao.save(bonusCommisionableEmployee);
                }

            }
        }

        return commissionAmountGroupDao.findAll();
    }

    @Override
    public List<CommissionAmountGroup> obtainCommissionBySupervisor(List<AgreementsGroupCondition> agreementsGroupConditionList) {

        for (AgreementsGroupCondition aGC : agreementsGroupConditionList) {
            AgreementsGroupCondition groupCondition = agreementsGroupConditionDao.getTabulator(aGC.getOrder(), aGC);
            List<CommissionAmountGroup> supervisorsGroup = commissionAmountGroupDao.findByGroupSupervisorAndSupervisors();

            for (CommissionAmountGroup supervisorG : supervisorsGroup){
                List<MultilevelEmployee> multilevelEmployees = multilevelEmployeeDao.findByIdEmployeeMultilevel(supervisorG.getIdEmployee());
                BigDecimal totalCelula = new BigDecimal(0);
                BigDecimal numRequest = new BigDecimal(0);
                for (MultilevelEmployee multilevelEmployee : multilevelEmployees){
                    CommissionAmountGroup adviser = commissionAmountGroupDao.getOnlyDataOfGroupNineTeen(multilevelEmployee.getIdEmployee());
                    totalCelula = totalCelula.add(adviser.getAmount());
                    numRequest = numRequest.add(adviser.getApplicationsNumber());
                }
                BigDecimal scope = supervisorG.getAmount().divide(totalCelula, 2, RoundingMode.HALF_UP);

                BigDecimal divisor = new BigDecimal(100);
                BigDecimal tabulator = groupCondition.getTabulator().divide(divisor);
                supervisorG.setTabulator(groupCondition.getTabulator());
                supervisorG.setCommission(totalCelula.multiply(tabulator));
                supervisorG.setScope(scope);
                supervisorG.setApplicationsNumber(numRequest);
                supervisorG.setAmount(totalCelula);
                commissionAmountGroupDao.update(supervisorG);

//                if (scope.doubleValue() >= groupCondition.getAmountMax().doubleValue()){
//                    commissionAmountGroupDao.delete(supervisorG);
//                }else {
//
//                    List<CommissionAmountGroup> supervisorDataByPromotor = commissionAmountGroupDao.findAllRegisterBySupervisorExceptGroupSupervisor(supervisorG.getClaveSap());
//
//                    if (!supervisorDataByPromotor.isEmpty()){
//                        for (CommissionAmountGroup data : supervisorDataByPromotor){
//                            commissionAmountGroupDao.delete(data);
//                        }
//                    }
//
//                    BigDecimal divisor = new BigDecimal(100);
//                    BigDecimal tabulator = groupCondition.getTabulator().divide(divisor);
//                    supervisorG.setTabulator(groupCondition.getTabulator());
//                    supervisorG.setCommission(totalCelula.multiply(tabulator));
//                    supervisorG.setScope(scope);
//                    supervisorG.setApplicationsNumber(numRequest);
//                    supervisorG.setAmount(totalCelula);
//                    commissionAmountGroupDao.update(supervisorG);
//                }
            }

        }

        return commissionAmountGroupDao.findAll();
    }

    @Override
    public List<AgreementsGroupCondition> changeStatusByCalculationDate(Integer idDateCalculation) {
        List<AgreementsGroupCondition> activesConditions = agreementsGroupConditionDao.findByCalculationStatus(idDateCalculation);
        List<AgreementsGroupCondition> disabledConditions = agreementsGroupConditionDao.findByCalculationStatusDifferent(idDateCalculation);

        for (AgreementsGroupCondition groupCondition : activesConditions){
            agreementsGroupConditionDao.updateStatus(groupCondition.getIdGroupCondition(),true);
        }

        for (AgreementsGroupCondition agreementsGroupCondition : disabledConditions){
            agreementsGroupConditionDao.updateStatus(agreementsGroupCondition.getIdGroupCondition(),false);
        }

        return agreementsGroupConditionDao.findAll();
    }

}
