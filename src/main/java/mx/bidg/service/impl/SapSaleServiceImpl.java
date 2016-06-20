package mx.bidg.service.impl;


import mx.bidg.dao.SapSaleDao;
import mx.bidg.exceptions.ValidationException;
import mx.bidg.model.SapSale;
import mx.bidg.service.SapSaleService;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;

/**
 * Created by gerardo8 on 29/04/16.
 */
@Transactional
@Service
public class SapSaleServiceImpl implements SapSaleService {

    @Autowired
    private SapSaleDao sapSaleDao;

    @Override
    public List<SapSale> findAll() {
        return sapSaleDao.findAll();
    }

    @Override
    public SapSale findById(Integer id) {
        return sapSaleDao.findById(id);
    }

    @Override
    public List<SapSale> saveFromExcel(MultipartFile file) throws IOException, InvalidFormatException {

        Workbook workbook = WorkbookFactory.create(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);

        for (int i=1;i<=sheet.getLastRowNum();i++) {
            Row currentRow = sheet.getRow(i);
            Cell idSale = currentRow.getCell(0);
            Cell creationDate = currentRow.getCell(1);
            Cell interlocCom = currentRow.getCell(2);
            Cell clientParentLast = currentRow.getCell(3);
            Cell clientMotherLast = currentRow.getCell(4);
            Cell clientName = currentRow.getCell(5);
            Cell clientSecName = currentRow.getCell(6);
            Cell clientSingleLast = currentRow.getCell(7);
            Cell clientId = currentRow.getCell(8);
            Cell imssNum = currentRow.getCell(9);
            Cell agreementName = currentRow.getCell(10);
            Cell product = currentRow.getCell(11);
            Cell dependency = currentRow.getCell(12);
            Cell statusSale = currentRow.getCell(13);
            Cell lastUpdate = currentRow.getCell(14);
            Cell approvalDate = currentRow.getCell(15);
            Cell requestedAmount = currentRow.getCell(16);
            Cell payments = currentRow.getCell(17);
            Cell depositAmount = currentRow.getCell(18);
            Cell comissionableAmount = currentRow.getCell(19);
            Cell purchaseDate = currentRow.getCell(20);
            Cell companyName = currentRow.getCell(21);
            Cell distributorName = currentRow.getCell(22);
            Cell claveSap = currentRow.getCell(23);
            Cell branchName = currentRow.getCell(24);
            Cell region = currentRow.getCell(25);
            Cell bonification = currentRow.getCell(26);
            Cell liquidation = currentRow.getCell(27);

            SapSale sapSale = new SapSale();


            if (agreementName != null) sapSale.setAgreementName(agreementName.getStringCellValue());
            if (approvalDate != null) sapSale.setApprovalDate(approvalDate.getDateCellValue());
            if (bonification != null) {
                BigDecimal bdBonification = new BigDecimal(bonification.getNumericCellValue());
                sapSale.setBonification(bdBonification);
            }
            if (branchName != null) sapSale.setBranchName(branchName.getStringCellValue());
            if (claveSap != null) sapSale.setClaveSap(claveSap.getStringCellValue());
            if (clientId != null) sapSale.setClientId(clientId.getStringCellValue());
            if (clientMotherLast != null) sapSale.setClientMotherLast(clientMotherLast.getStringCellValue());
            if (clientName != null) sapSale.setClientName(clientName.getStringCellValue());
            if (clientSecName != null) sapSale.setClientSecName(clientSecName.getStringCellValue());
            if (clientSingleLast != null) sapSale.setClientSingleLast(clientSingleLast.getStringCellValue());
            if (clientParentLast != null) sapSale.setClientParentLast(clientParentLast.getStringCellValue());
            if (comissionableAmount != null) {
                BigDecimal bdComissionableAmount = new BigDecimal(comissionableAmount.getNumericCellValue());
                sapSale.setComissionableAmount(bdComissionableAmount);
            }
            if (companyName != null) sapSale.setCompanyName(companyName.getStringCellValue());
            if (creationDate != null) sapSale.setCreationDate(creationDate.getDateCellValue());
            if (dependency != null) sapSale.setDependency(dependency.getStringCellValue());
            if (depositAmount != null) {
                BigDecimal bdDepositAmount = new BigDecimal(depositAmount.getNumericCellValue());
                sapSale.setDepositAmount(bdDepositAmount);
            }
            if (distributorName != null) sapSale.setDistributorName(distributorName.getStringCellValue());
            if (idSale != null) sapSale.setIdSale(idSale.getStringCellValue());
            if (imssNum != null) sapSale.setImssNum(imssNum.getStringCellValue());
            if (interlocCom != null) sapSale.setInterlocCom(interlocCom.getStringCellValue());
            if (liquidation != null) {
                BigDecimal bdLiquidation = new BigDecimal(liquidation.getNumericCellValue());
                sapSale.setLiquidation(bdLiquidation);
            }
            if (payments != null) sapSale.setPayments(payments.getStringCellValue());
            if (product != null) sapSale.setProduct(product.getStringCellValue());
            if (region != null) sapSale.setRegionName(region.getStringCellValue());
            if (requestedAmount != null) {
                BigDecimal bdRequestedAmount = new BigDecimal(requestedAmount.getNumericCellValue());
                sapSale.setRequestedAmount(bdRequestedAmount);
            }
            if (statusSale != null) sapSale.setStatusSale(statusSale.getStringCellValue());
            if (purchaseDate != null) sapSale.setPurchaseDate(purchaseDate.getDateCellValue());
            if (lastUpdate != null) sapSale.setLastUpdate(lastUpdate.getDateCellValue());



            List<SapSale> sapSales = sapSaleDao.findAllByIdSale(sapSale.getIdSale());

            for (SapSale sapSaleFromDb: sapSales) {
                if (sapSaleFromDb != null) {
                    sapSaleFromDb.setStatus(1);
                    sapSaleDao.update(sapSaleFromDb);
                }
            }

            sapSaleDao.save(sapSale);

        }
        return sapSaleDao.findAll();
    }

    @Override
    public List<SapSale> updateFromExcel(MultipartFile file) throws IOException, InvalidFormatException {

        Workbook workbook = WorkbookFactory.create(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);

        Integer idSapSaleFromDB;

        for (int i=1;i<=sheet.getLastRowNum();i++) {
            Row currentRow = sheet.getRow(i);
            Cell idSale = currentRow.getCell(0);
            Cell creationDate = currentRow.getCell(1);
            Cell interlocCom = currentRow.getCell(2);
            Cell clientParentLast = currentRow.getCell(3);
            Cell clientMotherLast = currentRow.getCell(4);
            Cell clientName = currentRow.getCell(5);
            Cell clientSecName = currentRow.getCell(6);
            Cell clientSingleLast = currentRow.getCell(7);
            Cell clientId = currentRow.getCell(8);
            Cell imssNum = currentRow.getCell(9);
            Cell agreementName = currentRow.getCell(10);
            Cell product = currentRow.getCell(11);
            Cell dependency = currentRow.getCell(12);
            Cell statusSale = currentRow.getCell(13);
            Cell lastUpdate = currentRow.getCell(14);
            Cell approvalDate = currentRow.getCell(15);
            Cell requestedAmount = currentRow.getCell(16);
            Cell payments = currentRow.getCell(17);
            Cell depositAmount = currentRow.getCell(18);
            Cell comissionableAmount = currentRow.getCell(19);
            Cell purchaseDate = currentRow.getCell(20);
            Cell companyName = currentRow.getCell(21);
            Cell distributorName = currentRow.getCell(22);
            Cell claveSap = currentRow.getCell(23);
            Cell branchName = currentRow.getCell(24);
            Cell region = currentRow.getCell(25);
            Cell bonification = currentRow.getCell(26);
            Cell liquidation = currentRow.getCell(27);

            SapSale sapSale;

            if (idSale != null) {

                sapSale = sapSaleDao.findByIdSale(idSale.getStringCellValue());

                if (sapSale != null) {

                    sapSale.setIdSale(idSale.getStringCellValue());

                    if (agreementName != null) sapSale.setAgreementName(agreementName.getStringCellValue());
                    if (approvalDate != null) sapSale.setApprovalDate(approvalDate.getDateCellValue());
                    if (bonification != null) {
                        BigDecimal bdBonification = new BigDecimal(bonification.getNumericCellValue());
                        sapSale.setBonification(bdBonification);
                    }
                    if (branchName != null) sapSale.setBranchName(branchName.getStringCellValue());
                    if (claveSap != null) sapSale.setClaveSap(claveSap.getStringCellValue());
                    if (clientId != null) sapSale.setClientId(clientId.getStringCellValue());
                    if (clientMotherLast != null) sapSale.setClientMotherLast(clientMotherLast.getStringCellValue());
                    if (clientName != null) sapSale.setClientName(clientName.getStringCellValue());
                    if (clientSecName != null) sapSale.setClientSecName(clientSecName.getStringCellValue());
                    if (clientSingleLast != null) sapSale.setClientSingleLast(clientSingleLast.getStringCellValue());
                    if (clientParentLast != null) sapSale.setClientParentLast(clientParentLast.getStringCellValue());
                    if (comissionableAmount != null) {
                        BigDecimal bdComissionableAmount = new BigDecimal(comissionableAmount.getNumericCellValue());
                        sapSale.setComissionableAmount(bdComissionableAmount);
                    }
                    if (companyName != null) sapSale.setCompanyName(companyName.getStringCellValue());
                    if (creationDate != null) sapSale.setCreationDate(creationDate.getDateCellValue());
                    if (dependency != null) sapSale.setDependency(dependency.getStringCellValue());
                    if (depositAmount != null) {
                        BigDecimal bdDepositAmount = new BigDecimal(depositAmount.getNumericCellValue());
                        sapSale.setDepositAmount(bdDepositAmount);
                    }
                    if (distributorName != null) sapSale.setDistributorName(distributorName.getStringCellValue());
                    if (imssNum != null) sapSale.setImssNum(imssNum.getStringCellValue());
                    if (interlocCom != null) sapSale.setInterlocCom(interlocCom.getStringCellValue());
                    if (liquidation != null) {
                        BigDecimal bdLiquidation = new BigDecimal(liquidation.getNumericCellValue());
                        sapSale.setLiquidation(bdLiquidation);
                    }
                    if (payments != null) sapSale.setPayments(payments.getStringCellValue());
                    if (product != null) sapSale.setProduct(product.getStringCellValue());
                    if (region != null) sapSale.setRegionName(region.getStringCellValue());
                    if (requestedAmount != null) {
                        BigDecimal bdRequestedAmount = new BigDecimal(requestedAmount.getNumericCellValue());
                        sapSale.setRequestedAmount(bdRequestedAmount);
                    }
                    if (statusSale != null) sapSale.setStatusSale(statusSale.getStringCellValue());
                    if (purchaseDate != null) sapSale.setPurchaseDate(purchaseDate.getDateCellValue());
                    if (lastUpdate != null) sapSale.setLastUpdate(lastUpdate.getDateCellValue());

                    sapSaleDao.update(sapSale);

                } else {

                    SapSale newSapSale = new SapSale();

                    newSapSale.setIdSale(idSale.getStringCellValue());

                    if (agreementName != null) newSapSale.setAgreementName(agreementName.getStringCellValue());
                    if (approvalDate != null) newSapSale.setApprovalDate(approvalDate.getDateCellValue());
                    if (bonification != null) {
                        BigDecimal bdBonification = new BigDecimal(bonification.getNumericCellValue());
                        newSapSale.setBonification(bdBonification);
                    }
                    if (branchName != null) newSapSale.setBranchName(branchName.getStringCellValue());
                    if (claveSap != null) newSapSale.setClaveSap(claveSap.getStringCellValue());
                    if (clientId != null) newSapSale.setClientId(clientId.getStringCellValue());
                    if (clientMotherLast != null) newSapSale.setClientMotherLast(clientMotherLast.getStringCellValue());
                    if (clientName != null) newSapSale.setClientName(clientName.getStringCellValue());
                    if (clientSecName != null) newSapSale.setClientSecName(clientSecName.getStringCellValue());
                    if (clientSingleLast != null) newSapSale.setClientSingleLast(clientSingleLast.getStringCellValue());
                    if (clientParentLast != null) newSapSale.setClientParentLast(clientParentLast.getStringCellValue());
                    if (comissionableAmount != null) {
                        BigDecimal bdComissionableAmount = new BigDecimal(comissionableAmount.getNumericCellValue());
                        newSapSale.setComissionableAmount(bdComissionableAmount);
                    }
                    if (companyName != null) newSapSale.setCompanyName(companyName.getStringCellValue());
                    if (creationDate != null) newSapSale.setCreationDate(creationDate.getDateCellValue());
                    if (dependency != null) newSapSale.setDependency(dependency.getStringCellValue());
                    if (depositAmount != null) {
                        BigDecimal bdDepositAmount = new BigDecimal(depositAmount.getNumericCellValue());
                        newSapSale.setDepositAmount(bdDepositAmount);
                    }
                    if (distributorName != null) newSapSale.setDistributorName(distributorName.getStringCellValue());
                    if (imssNum != null) newSapSale.setImssNum(imssNum.getStringCellValue());
                    if (interlocCom != null) newSapSale.setInterlocCom(interlocCom.getStringCellValue());
                    if (liquidation != null) {
                        BigDecimal bdLiquidation = new BigDecimal(liquidation.getNumericCellValue());
                        newSapSale.setLiquidation(bdLiquidation);
                    }
                    if (payments != null) newSapSale.setPayments(payments.getStringCellValue());
                    if (product != null) newSapSale.setProduct(product.getStringCellValue());
                    if (region != null) newSapSale.setRegionName(region.getStringCellValue());
                    if (requestedAmount != null) {
                        BigDecimal bdRequestedAmount = new BigDecimal(requestedAmount.getNumericCellValue());
                        newSapSale.setRequestedAmount(bdRequestedAmount);
                    }
                    if (statusSale != null) newSapSale.setStatusSale(statusSale.getStringCellValue());
                    if (purchaseDate != null) newSapSale.setPurchaseDate(purchaseDate.getDateCellValue());
                    if (lastUpdate != null) newSapSale.setLastUpdate(lastUpdate.getDateCellValue());

                    sapSaleDao.save(newSapSale);

                }




            }
        }
        return sapSaleDao.findAll();
    }

    @Override
    public Boolean existsSales(MultipartFile file) throws IOException, InvalidFormatException {

        Workbook workbook = WorkbookFactory.create(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);

        Row headerRow = sheet.getRow(0);
        String[] headersToSkip = {
                "Número de operación", "Fecha de Registro","Interloc.comercial",
                "Apellidos","Nombre de pila","2º apellido","Apellido de soltera",
                "2º nombre", "Nº identificación","Nº. IMSS","Nombre de un convenio",
                "Producto", "Dependencia","Status","Fecha Última Actualización",
                "Fecha de Aprobación", "Monto Solicitado", "Numero de pagos",
                "Monto a depositar","Monto comisionable","Fecha de compra",
                "EMPRESA", "Distribuidor","Vendedor","Sucursal","Region",
                "Bonificación Autoservicio","Liquidación Intercompañias"
        };

        if (headerRow.getPhysicalNumberOfCells() != 28) {
            throw new ValidationException("Tipo de formato no compatible.",
                    "Los datos de este archivo no son los correctos o no cumplen con los datos de venta.");
        } else {
            for (int i = 0 ; i < 28 ;i++) {
                if (!headerRow.getCell(i).getStringCellValue().equals(headersToSkip[i])) {
                    throw new ValidationException("Tipo de formato no compatible.",
                            "Los datos de este archivo no son los correctos o no cumplen con los datos de venta.");
                }
            }
        }


        boolean existsSale = false;

        for (int i=1;i<=sheet.getLastRowNum();i++) {
            Row currentRow = sheet.getRow(i);
            Cell idSale = currentRow.getCell(0);

            SapSale sapSale = new SapSale();

            if (idSale != null) {
                sapSale.setIdSale(idSale.getStringCellValue());
            }

            List<SapSale> sapSales = sapSaleDao.findAllByIdSale(sapSale.getIdSale());

            for (SapSale sapSaleFromDB: sapSales) {
                if (sapSaleFromDB != null) {
                    existsSale = true;
                }
            }
        }
        return existsSale;
    }
}
