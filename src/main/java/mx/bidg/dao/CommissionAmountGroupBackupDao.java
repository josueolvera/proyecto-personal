package mx.bidg.dao;

import mx.bidg.model.CommissionAmountGroupBackup;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by josueolvera on 22/09/16.
 */
public interface CommissionAmountGroupBackupDao extends InterfaceDao<CommissionAmountGroupBackup> {
    List<CommissionAmountGroupBackup> findTotalAmountGroupGobierno(LocalDateTime fromDate, LocalDateTime toDate);
    List<CommissionAmountGroupBackup> findTotalAmountGroupSalud(LocalDateTime fromDate, LocalDateTime toDate);
    List<CommissionAmountGroupBackup> findTotalAmountGroupSaludCI(LocalDateTime fromDate, LocalDateTime toDate);
}
