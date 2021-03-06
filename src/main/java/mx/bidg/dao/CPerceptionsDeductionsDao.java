package mx.bidg.dao;

import mx.bidg.model.CPerceptionsDeductions;
import mx.bidg.model.DistributorPerceptionDeduction;
import mx.bidg.model.PerceptionsDeductions;

import java.util.List;

/**
 * Created by josueolvera on 28/09/16.
 */
public interface CPerceptionsDeductionsDao extends InterfaceDao<CPerceptionsDeductions> {
    List<CPerceptionsDeductions> findByIdTypeOperation(Integer idTypeOperation);
}
