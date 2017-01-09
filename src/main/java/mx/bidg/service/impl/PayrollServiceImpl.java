package mx.bidg.service.impl;

import mx.bidg.dao.DwEnterprisesDao;
import mx.bidg.dao.OutsourcingDao;
import mx.bidg.dao.PayrollDao;
import mx.bidg.dao.SapSaleDao;
import mx.bidg.model.DwEnterprises;
import mx.bidg.model.Outsourcing;
import mx.bidg.model.Payroll;
import mx.bidg.service.PayrollService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

/**
 * Created by Desarrollador on 19/11/2016.
 */
@Service
@Transactional
public class PayrollServiceImpl implements PayrollService {

    @Autowired
    PayrollDao payrollDao;

    @Autowired
    DwEnterprisesDao dwEnterprisesDao;

    @Autowired
    OutsourcingDao outsourcingDao;

    @Autowired
    SapSaleDao sapSaleDao;

    @Override
    public Payroll save(Payroll payroll) {
        return payrollDao.save(payroll);
    }

    @Override
    public Payroll update(Payroll payroll) {
        return payrollDao.update(payroll);
    }

    @Override
    public Payroll findById(Integer idPayroll) {
        return payrollDao.findById(idPayroll);
    }

    @Override
    public List<Payroll> findAll() {
        return payrollDao.findAll();
    }

    @Override
    public boolean delete(Payroll payroll) {
        return payrollDao.delete(payroll);
    }

    @Override
    public void reportCorporate(OutputStream outputStream, LocalDateTime applicatioDateStart, LocalDateTime applicationDateEnd, FileOutputStream fileOutputStream) throws IOException {

        Workbook wb = new XSSFWorkbook();
        //Definicion del estilo de la cabecera
        Font font = wb.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 10);
        font.setFontName("Arial");
        font.setColor(IndexedColors.WHITE.getIndex());
        CellStyle style = wb.createCellStyle();
        style.setFont(font);
        style.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        style.setAlignment(CellStyle.ALIGN_CENTER);

        CellStyle cellDateStyle = wb.createCellStyle();
        CreationHelper createHelper = wb.getCreationHelper();
        cellDateStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/MM/yyyy"));

        Sheet hoja1 = wb.createSheet("CALCULO");

        Row row1 = hoja1.createRow(0);

        row1.createCell(0).setCellValue("NÚMERO DE EMPLEADO");
        row1.createCell(1).setCellValue("NOMBRE");
        row1.createCell(2).setCellValue("BANCO");
        row1.createCell(3).setCellValue("CUENTA");
        row1.createCell(4).setCellValue("CLABE");
        row1.createCell(5).setCellValue("PLAZA");
        row1.createCell(6).setCellValue("AREA");
        row1.createCell(7).setCellValue("PUESTO");
        row1.createCell(8).setCellValue("RFC");
        row1.createCell(9).setCellValue("CURP");
        row1.createCell(10).setCellValue("FECHA DE INGRESO");
        row1.createCell(11).setCellValue("SUELDO REAL QUINCENAL");
        row1.createCell(12).setCellValue("MONTO RETARDO");
        row1.createCell(13).setCellValue("DESCUENTO");
        row1.createCell(14).setCellValue("BONO");
        row1.createCell(15).setCellValue("AJUSTE");
        row1.createCell(16).setCellValue("PRIMA VACACIONAL");
        row1.createCell(17).setCellValue("EFECTIVO");
        row1.createCell(18).setCellValue("EFECTIVO EDMON");
        row1.createCell(19).setCellValue("COMISIONES EMCOFIN");
        row1.createCell(20).setCellValue("RHMAS PAGO");
        row1.createCell(21).setCellValue("RHMAS TOTAL A FACTURAR");
        row1.createCell(22).setCellValue("PERCEPCIONES");
        row1.createCell(23).setCellValue("DEDUCCIONES");
        row1.createCell(24).setCellValue("DEPOSITO ASIMILABLES");

        //Implementacion del estilo
        for (Cell celda : row1) {
            celda.setCellStyle(style);
        }

        int aux1 = 1;

        Double sum1 = 0.00;
        Double sum2 = 0.00;
        Double sum3 = 0.00;
        Double sum4 = 0.00;
        Double sum5 = 0.00;
        Double sum6 = 0.00;
        Double sum7 = 0.00;
        Double sum8 = 0.00;
        Double sum9 = 0.00;
        Double sum10 = 0.00;
        Double sum11 = 0.00;
        Double sum12 = 0.00;
        Double sum13 = 0.00;
        Double sum14 = 0.00;

        List<Payroll> payrollList = payrollDao.findAll();

        for (Payroll payroll : payrollList){
            row1 = hoja1.createRow(aux1);

            if (payroll.getNumeroDeEmpleado() != null){
                row1.createCell(0).setCellValue(payroll.getNumeroDeEmpleado());
            }
            if (payroll.getNombre() != null){
                row1.createCell(1).setCellValue(payroll.getNombre());
            }
            if (payroll.getBanco() != null){
                row1.createCell(2).setCellValue(payroll.getBanco());
            }
            if (payroll.getNumeroDeCuenta() != null){
                row1.createCell(3).setCellValue(payroll.getNumeroDeCuenta());
            }
            if (payroll.getCuentaClabe() != null){
                row1.createCell(4).setCellValue(payroll.getCuentaClabe());
            }
            if (payroll.getSucursal() != null){
                row1.createCell(5).setCellValue(payroll.getSucursal());
            }
            if (payroll.getArea() != null){
                row1.createCell(6).setCellValue(payroll.getArea());
            }
            if (payroll.getPuesto() != null){
                row1.createCell(7).setCellValue(payroll.getPuesto());
            }
            if (payroll.getRfc() != null){
                row1.createCell(8).setCellValue(payroll.getRfc());
            }
            if (payroll.getCurp() != null){
                row1.createCell(9).setCellValue(payroll.getCurp());
            }
            if(payroll.getFechaIngreso() != null){
                Date joinDate = Date.from(payroll.getFechaIngreso().atStartOfDay(ZoneId.systemDefault()).toInstant());
                row1.createCell(10);
                row1.getCell(10).setCellValue(joinDate);
                row1.getCell(10).setCellStyle(cellDateStyle);
            }
            if (payroll.getSueldo() != null){
                row1.createCell(11).setCellValue(payroll.getSueldo().doubleValue());
                sum1 += payroll.getSueldo().doubleValue();
            }
            if (payroll.getMontoRetardo() != null){
                row1.createCell(12).setCellValue(payroll.getMontoRetardo().doubleValue());
                sum2 += payroll.getMontoRetardo().doubleValue();
            }
            if (payroll.getDescuento() != null){
                row1.createCell(13).setCellValue(payroll.getDescuento().doubleValue());
                sum3 += payroll.getDescuento().doubleValue();
            }
            if (payroll.getAjuste() != null){
                row1.createCell(15).setCellValue(payroll.getAjuste().doubleValue());
                sum4 += payroll.getAjuste().doubleValue();
            }
            if (payroll.getBono() != null){
                row1.createCell(14).setCellValue(payroll.getBono().doubleValue());
                sum5 += payroll.getBono().doubleValue();
            }
            if (payroll.getPrimaVacacional() != null){
                row1.createCell(16).setCellValue(payroll.getPrimaVacacional().doubleValue());
                sum6 += payroll.getPrimaVacacional().doubleValue();
            }
            if (payroll.getEfectivo() != null){
                row1.createCell(17).setCellValue(payroll.getEfectivo().doubleValue());
                sum7 += payroll.getEfectivo().doubleValue();
            }
            if (payroll.getEfectivoEdmon() != null){
                row1.createCell(18).setCellValue(payroll.getEfectivoEdmon().doubleValue());
                sum8 += payroll.getEfectivoEdmon().doubleValue();
            }
            if (payroll.getComisionEmcofin() != null){
                row1.createCell(19).setCellValue(payroll.getComisionEmcofin().doubleValue());
                sum9 += payroll.getComisionEmcofin().doubleValue();
            }
            if (payroll.getRhmasPago() != null){
                row1.createCell(20).setCellValue(payroll.getRhmasPago().doubleValue());
                sum10 += payroll.getRhmasPago().doubleValue();
            }
            if (payroll.getRhmasTotalFacturar() != null){
                row1.createCell(21).setCellValue(payroll.getRhmasTotalFacturar().doubleValue());
                sum11 += payroll.getRhmasTotalFacturar().doubleValue();
            }
            if (payroll.getPercepcion() != null){
                row1.createCell(22).setCellValue(payroll.getPercepcion().doubleValue());
                sum12 += payroll.getPercepcion().doubleValue();
            }
            if (payroll.getDeduccion() != null){
                row1.createCell(23).setCellValue(payroll.getDeduccion().doubleValue());
                sum13 += payroll.getDeduccion().doubleValue();
            }
            if (payroll.getPago() != null){
                row1.createCell(24).setCellValue(payroll.getPago().doubleValue());
                sum14 += payroll.getPago().doubleValue();
            }

            aux1++;
        }

        row1 = hoja1.createRow(aux1+2);

        row1.createCell(10).setCellValue("TOTALES");
        row1.createCell(11).setCellValue(sum1);
        row1.createCell(12).setCellValue(sum2);
        row1.createCell(13).setCellValue(sum3);
        row1.createCell(14).setCellValue(sum4);
        row1.createCell(15).setCellValue(sum5);
        row1.createCell(16).setCellValue(sum6);
        row1.createCell(17).setCellValue(sum7);
        row1.createCell(18).setCellValue(sum8);
        row1.createCell(19).setCellValue(sum9);
        row1.createCell(20).setCellValue(sum10);
        row1.createCell(21).setCellValue(sum11);
        row1.createCell(22).setCellValue(sum12);
        row1.createCell(23).setCellValue(sum13);
        row1.createCell(24).setCellValue(sum14);

        Sheet hoja2 = wb.createSheet("RHMAS GMT E ");

        //Se crea la fila que contiene la cabecera
        Row row2 = hoja2.createRow(0);

        row2.createCell(0).setCellValue("DEPARTAMENTO");
        row2.createCell(1).setCellValue("CODIGO");
        row2.createCell(2).setCellValue("NOMBRE");
        row2.createCell(3).setCellValue("SUELDO");
        row2.createCell(4).setCellValue("SUBSIDIO");
        row2.createCell(5).setCellValue("IMSS EMPLEADO");
        row2.createCell(6).setCellValue("ISR");
        row2.createCell(7).setCellValue("AJUSTE AL NETO");
        row2.createCell(8).setCellValue("TOTAL DEDUCCIONES");
        row2.createCell(9).setCellValue("NETO SUELDO FISCAL");
        row2.createCell(10).setCellValue("IMSS");
        row2.createCell(11).setCellValue("RCV");
        row2.createCell(12).setCellValue("INFONAVIT EMPRESA");
        row2.createCell(13).setCellValue("IMPUESTO SOBRE NÓMINA");
        row2.createCell(14).setCellValue("TOTAL PREVISION SOCIAL");
        row2.createCell(15).setCellValue("COMISIÓN");
        row2.createCell(16).setCellValue("SUBTOTAL");
        row2.createCell(17).setCellValue("IVA");
        row2.createCell(18).setCellValue("TOTAL");

        //Implementacion del estilo
        for (Cell celda : row2) {
            celda.setCellStyle(style);
        }

        List<DwEnterprises> dwEnterprisesList = dwEnterprisesDao.findOnlyCorporate();
        List<Outsourcing> outsourcingEList = outsourcingDao.findByType(1,applicatioDateStart,applicationDateEnd);

        int aux2 = 1;

        Double sum15 = 0.0;
        Double sum16 = 0.0;
        Double sum17 = 0.0;
        Double sum18 = 0.0;
        Double sum19 = 0.0;
        Double sum20 = 0.0;
        Double sum21 = 0.0;
        Double sum22 = 0.0;
        Double sum23 = 0.0;
        Double sum24 = 0.0;
        Double sum25 = 0.0;
        Double sum26 = 0.0;
        Double sum27 = 0.0;
        Double sum28 = 0.0;
        Double sum29 = 0.0;
        Double sum30 = 0.0;

        for (Outsourcing outsourcing : outsourcingEList){
            row2 = hoja2.createRow(aux2);

            if (outsourcing.getDwEnterprises() != null){
                if (outsourcing.getDwEnterprises().getArea() != null){
                    row2.createCell(0).setCellValue(outsourcing.getDwEnterprises().getArea().getAreaName());
                }
            }
            if(outsourcing.getEmployee() != null){
                row2.createCell(1).setCellValue(outsourcing.getEmployee().getIdEmployee());
            }
            if(outsourcing.getEmployee() != null){
                row2.createCell(2).setCellValue(outsourcing.getEmployee().getFullName());
            }
            if(outsourcing.getSalary() != null){
                row2.createCell(3).setCellValue(outsourcing.getSalary().doubleValue());
                sum15 += outsourcing.getSalary().doubleValue();
            }
            if(outsourcing.getSubsidy() != null){
                row2.createCell(4).setCellValue(outsourcing.getSubsidy().doubleValue());
                sum16 += outsourcing.getSalary().doubleValue();
            }
            if (outsourcing.getImssEmployee() != null){
                row2.createCell(5).setCellValue(outsourcing.getImssEmployee().doubleValue());
                sum17 += outsourcing.getSubsidy().doubleValue();
            }
            if (outsourcing.getIsr() != null){
                row2.createCell(6).setCellValue(outsourcing.getIsr().doubleValue());
                sum18 += outsourcing.getIsr().doubleValue();
            }
            if (outsourcing.getAdjustment() != null){
                row2.createCell(7).setCellValue(outsourcing.getAdjustment().doubleValue());
                sum19 += outsourcing.getAdjustment().doubleValue();
            }
            if (outsourcing.getTotalDeductions() != null){
                row2.createCell(8).setCellValue(outsourcing.getTotalDeductions().doubleValue());
                sum20 += outsourcing.getTotalDeductions().doubleValue();
            }
            if (outsourcing.getNetAssetTax() != null){
                row2.createCell(9).setCellValue(outsourcing.getNetAssetTax().doubleValue());
                sum21 += outsourcing.getNetAssetTax().doubleValue();
            }
            if (outsourcing.getImss() != null){
                row2.createCell(10).setCellValue(outsourcing.getImss().doubleValue());
                sum22 += outsourcing.getImss().doubleValue();
            }
            if (outsourcing.getRcv() != null){
                row2.createCell(11).setCellValue(outsourcing.getRcv().doubleValue());
                sum23 += outsourcing.getRcv().doubleValue();
            }
            if (outsourcing.getEnterpriseInfonavit() != null){
                row2.createCell(12).setCellValue(outsourcing.getEnterpriseInfonavit().doubleValue());
                sum24 += outsourcing.getEnterpriseInfonavit().doubleValue();
            }
            if (outsourcing.getPayrollTax() != null){
                row2.createCell(13).setCellValue(outsourcing.getPayrollTax().doubleValue());
                sum25 += outsourcing.getPayrollTax().doubleValue();
            }
            if (outsourcing.getTotalSocialSecurity() != null){
                row2.createCell(14).setCellValue(outsourcing.getTotalSocialSecurity().doubleValue());
                sum26 += outsourcing.getTotalSocialSecurity().doubleValue();
            }
            if (outsourcing.getCommission() != null){
                row2.createCell(15).setCellValue(outsourcing.getCommission().doubleValue());
                sum27 += outsourcing.getCommission().doubleValue();
            }
            if (outsourcing.getSubtotal() != null){
                row2.createCell(16).setCellValue(outsourcing.getSubtotal().doubleValue());
                sum28 += outsourcing.getSubtotal().doubleValue();
            }
            if (outsourcing.getIva() != null){
                row2.createCell(17).setCellValue(outsourcing.getIva().doubleValue());
                sum29 += outsourcing.getIva().doubleValue();
            }
            if (outsourcing.getTotal() != null){
                row2.createCell(18).setCellValue(outsourcing.getTotal().doubleValue());
                sum30 += outsourcing.getTotal().doubleValue();
            }

            aux2++;
        }

        row2 = hoja2.createRow(aux2+2);

        row2.createCell(2).setCellValue("TOTALES");
        row2.createCell(3).setCellValue(sum15);
        row2.createCell(4).setCellValue(sum16);
        row2.createCell(5).setCellValue(sum17);
        row2.createCell(6).setCellValue(sum18);
        row2.createCell(7).setCellValue(sum19);
        row2.createCell(8).setCellValue(sum20);
        row2.createCell(9).setCellValue(sum21);
        row2.createCell(10).setCellValue(sum22);
        row2.createCell(11).setCellValue(sum23);
        row2.createCell(12).setCellValue(sum24);
        row2.createCell(13).setCellValue(sum25);
        row2.createCell(14).setCellValue(sum26);
        row2.createCell(15).setCellValue(sum27);
        row2.createCell(16).setCellValue(sum28);
        row2.createCell(17).setCellValue(sum29);
        row2.createCell(18).setCellValue(sum30);

        Sheet hoja5 = wb.createSheet("RHMAS GMT C ");

        //Se crea la fila que contiene la cabecera
        Row row5 = hoja5.createRow(0);

        row5.createCell(0).setCellValue("DEPARTAMENTO");
        row5.createCell(1).setCellValue("CODIGO");
        row5.createCell(2).setCellValue("NOMBRE");
        row5.createCell(3).setCellValue("SUELDO");
        row5.createCell(4).setCellValue("SUBSIDIO");
        row5.createCell(5).setCellValue("IMSS EMPLEADO");
        row5.createCell(6).setCellValue("ISR");
        row5.createCell(7).setCellValue("AJUSTE AL NETO");
        row5.createCell(8).setCellValue("TOTAL DEDUCCIONES");
        row5.createCell(9).setCellValue("NETO SUELDO FISCAL");
        row5.createCell(10).setCellValue("IMSS");
        row5.createCell(11).setCellValue("RCV");
        row5.createCell(12).setCellValue("INFONAVIT EMPRESA");
        row5.createCell(13).setCellValue("IMPUESTO SOBRE NÓMINA");
        row5.createCell(14).setCellValue("TOTAL PREVISION SOCIAL");
        row5.createCell(15).setCellValue("COMISIÓN");
        row5.createCell(16).setCellValue("SUBTOTAL");
        row5.createCell(17).setCellValue("IVA");
        row5.createCell(18).setCellValue("TOTAL");

        //Implementacion del estilo
        for (Cell celda : row5) {
            celda.setCellStyle(style);
        }

        List<Outsourcing> outsourcingCList = outsourcingDao.findByType(2,applicatioDateStart,applicationDateEnd);

        int aux5 = 1;

        Double sum31 = 0.0;
        Double sum32 = 0.0;
        Double sum33 = 0.0;
        Double sum34 = 0.0;
        Double sum35 = 0.0;
        Double sum36 = 0.0;
        Double sum37 = 0.0;
        Double sum38 = 0.0;
        Double sum39 = 0.0;
        Double sum40 = 0.0;
        Double sum41 = 0.0;
        Double sum42 = 0.0;
        Double sum43 = 0.0;
        Double sum44 = 0.0;
        Double sum45 = 0.0;
        Double sum46 = 0.0;

        for (Outsourcing outsourcing : outsourcingCList){
            row5 = hoja5.createRow(aux5);

            if (outsourcing.getDwEnterprises() != null){
                if (outsourcing.getDwEnterprises().getArea() != null){
                    row5.createCell(0).setCellValue(outsourcing.getDwEnterprises().getArea().getAreaName());
                }
            }
            if(outsourcing.getEmployee() != null){
                row5.createCell(1).setCellValue(outsourcing.getEmployee().getIdEmployee());
            }
            if(outsourcing.getEmployee() != null){
                row5.createCell(2).setCellValue(outsourcing.getEmployee().getFullName());
            }
            if(outsourcing.getSalary() != null){
                row5.createCell(3).setCellValue(outsourcing.getSalary().doubleValue());
                sum31 += outsourcing.getSalary().doubleValue();
            }
            if(outsourcing.getSubsidy() != null){
                row5.createCell(4).setCellValue(outsourcing.getSubsidy().doubleValue());
                sum32 += outsourcing.getSubsidy().doubleValue();
            }
            if (outsourcing.getImssEmployee() != null){
                row5.createCell(5).setCellValue(outsourcing.getImssEmployee().doubleValue());
                sum33 += outsourcing.getImssEmployee().doubleValue();
            }
            if (outsourcing.getIsr() != null){
                row5.createCell(6).setCellValue(outsourcing.getIsr().doubleValue());
                sum34 += outsourcing.getIsr().doubleValue();
            }
            if (outsourcing.getAdjustment() != null){
                row5.createCell(7).setCellValue(outsourcing.getAdjustment().doubleValue());
                sum35 += outsourcing.getAdjustment().doubleValue();
            }
            if (outsourcing.getTotalDeductions() != null){
                row5.createCell(8).setCellValue(outsourcing.getTotalDeductions().doubleValue());
                sum36 += outsourcing.getTotalDeductions().doubleValue();
            }
            if (outsourcing.getNetAssetTax() != null){
                row5.createCell(9).setCellValue(outsourcing.getNetAssetTax().doubleValue());
                sum37 += outsourcing.getNetAssetTax().doubleValue();
            }
            if (outsourcing.getImss() != null){
                row5.createCell(10).setCellValue(outsourcing.getImss().doubleValue());
                sum38 += outsourcing.getImss().doubleValue();
            }
            if (outsourcing.getRcv() != null){
                row5.createCell(11).setCellValue(outsourcing.getRcv().doubleValue());
                sum39 += outsourcing.getRcv().doubleValue();
            }
            if (outsourcing.getEnterpriseInfonavit() != null){
                row5.createCell(12).setCellValue(outsourcing.getEnterpriseInfonavit().doubleValue());
                sum40 += outsourcing.getEnterpriseInfonavit().doubleValue();
            }
            if (outsourcing.getPayrollTax() != null){
                row5.createCell(13).setCellValue(outsourcing.getPayrollTax().doubleValue());
                sum41 += outsourcing.getPayrollTax().doubleValue();
            }
            if (outsourcing.getTotalSocialSecurity() != null){
                row5.createCell(14).setCellValue(outsourcing.getTotalSocialSecurity().doubleValue());
                sum42 += outsourcing.getTotalSocialSecurity().doubleValue();
            }
            if (outsourcing.getCommission() != null){
                row5.createCell(15).setCellValue(outsourcing.getCommission().doubleValue());
                sum43 += outsourcing.getCommission().doubleValue();
            }
            if (outsourcing.getSubtotal() != null){
                row5.createCell(16).setCellValue(outsourcing.getSubtotal().doubleValue());
                sum44 += outsourcing.getSubtotal().doubleValue();
            }
            if (outsourcing.getIva() != null){
                row5.createCell(17).setCellValue(outsourcing.getIva().doubleValue());
                sum45 += outsourcing.getIva().doubleValue();
            }
            if (outsourcing.getTotal() != null){
                row5.createCell(18).setCellValue(outsourcing.getTotal().doubleValue());
                sum46 += outsourcing.getTotal().doubleValue();
            }

            aux5++;
        }

        row5 = hoja5.createRow(aux5+2);

        row5.createCell(2).setCellValue("TOTALES");
        row5.createCell(3).setCellValue(sum31);
        row5.createCell(4).setCellValue(sum32);
        row5.createCell(5).setCellValue(sum33);
        row5.createCell(6).setCellValue(sum34);
        row5.createCell(7).setCellValue(sum35);
        row5.createCell(8).setCellValue(sum36);
        row5.createCell(9).setCellValue(sum37);
        row5.createCell(10).setCellValue(sum38);
        row5.createCell(11).setCellValue(sum39);
        row5.createCell(12).setCellValue(sum40);
        row5.createCell(13).setCellValue(sum41);
        row5.createCell(14).setCellValue(sum42);
        row5.createCell(15).setCellValue(sum43);
        row5.createCell(16).setCellValue(sum44);
        row5.createCell(17).setCellValue(sum45);
        row5.createCell(18).setCellValue(sum46);

        Sheet hoja6 = wb.createSheet("RHMAS GMT BID ENERGY");

        //Se crea la fila que contiene la cabecera
        Row row6 = hoja6.createRow(0);

        row6.createCell(0).setCellValue("DEPARTAMENTO");
        row6.createCell(1).setCellValue("CODIGO");
        row6.createCell(2).setCellValue("NOMBRE");
        row6.createCell(3).setCellValue("SUELDO");
        row6.createCell(4).setCellValue("SUBSIDIO");
        row6.createCell(5).setCellValue("IMSS EMPLEADO");
        row6.createCell(6).setCellValue("ISR");
        row6.createCell(7).setCellValue("AJUSTE AL NETO");
        row6.createCell(8).setCellValue("TOTAL DEDUCCIONES");
        row6.createCell(9).setCellValue("NETO SUELDO FISCAL");
        row6.createCell(10).setCellValue("IMSS");
        row6.createCell(11).setCellValue("RCV");
        row6.createCell(12).setCellValue("INFONAVIT EMPRESA");
        row6.createCell(13).setCellValue("IMPUESTO SOBRE NÓMINA");
        row6.createCell(14).setCellValue("TOTAL PREVISION SOCIAL");
        row6.createCell(15).setCellValue("COMISIÓN");
        row6.createCell(16).setCellValue("SUBTOTAL");
        row6.createCell(17).setCellValue("IVA");
        row6.createCell(18).setCellValue("TOTAL");

        //Implementacion del estilo
        for (Cell celda : row6) {
            celda.setCellStyle(style);
        }

        List<Outsourcing> outsourcingBIDList = outsourcingDao.findByType(3,applicatioDateStart,applicationDateEnd);

        int aux6 = 1;

        Double sum47 = 0.0;
        Double sum48 = 0.0;
        Double sum49 = 0.0;
        Double sum50 = 0.0;
        Double sum51 = 0.0;
        Double sum52 = 0.0;
        Double sum53 = 0.0;
        Double sum54 = 0.0;
        Double sum55 = 0.0;
        Double sum56 = 0.0;
        Double sum57 = 0.0;
        Double sum58 = 0.0;
        Double sum59 = 0.0;
        Double sum60 = 0.0;
        Double sum61 = 0.0;
        Double sum62 = 0.0;

        for (Outsourcing outsourcing : outsourcingBIDList){
            row6 = hoja6.createRow(aux6);

            if (outsourcing.getDwEnterprises() != null){
                if (outsourcing.getDwEnterprises().getArea() != null){
                    row6.createCell(0).setCellValue(outsourcing.getDwEnterprises().getArea().getAreaName());
                }
            }
            if(outsourcing.getEmployee() != null){
                row6.createCell(1).setCellValue(outsourcing.getEmployee().getIdEmployee());
            }
            if(outsourcing.getEmployee() != null){
                row6.createCell(2).setCellValue(outsourcing.getEmployee().getFullName());
            }
            if(outsourcing.getSalary() != null){
                row6.createCell(3).setCellValue(outsourcing.getSalary().doubleValue());
                sum47 += outsourcing.getSalary().doubleValue();
            }
            if(outsourcing.getSubsidy() != null){
                row6.createCell(4).setCellValue(outsourcing.getSubsidy().doubleValue());
                sum48 += outsourcing.getSubsidy().doubleValue();
            }
            if (outsourcing.getImssEmployee() != null){
                row6.createCell(5).setCellValue(outsourcing.getImssEmployee().doubleValue());
                sum49 += outsourcing.getImssEmployee().doubleValue();
            }
            if (outsourcing.getIsr() != null){
                row6.createCell(6).setCellValue(outsourcing.getIsr().doubleValue());
                sum50 += outsourcing.getIsr().doubleValue();
            }
            if (outsourcing.getAdjustment() != null){
                row6.createCell(7).setCellValue(outsourcing.getAdjustment().doubleValue());
                sum51 += outsourcing.getAdjustment().doubleValue();
            }
            if (outsourcing.getTotalDeductions() != null){
                row6.createCell(8).setCellValue(outsourcing.getTotalDeductions().doubleValue());
                sum52 += outsourcing.getTotalDeductions().doubleValue();
            }
            if (outsourcing.getNetAssetTax() != null){
                row6.createCell(9).setCellValue(outsourcing.getNetAssetTax().doubleValue());
                sum53 += outsourcing.getNetAssetTax().doubleValue();
            }
            if (outsourcing.getImss() != null){
                row6.createCell(10).setCellValue(outsourcing.getImss().doubleValue());
                sum54 += outsourcing.getImss().doubleValue();
            }
            if (outsourcing.getRcv() != null){
                row6.createCell(11).setCellValue(outsourcing.getRcv().doubleValue());
                sum55 += outsourcing.getRcv().doubleValue();
            }
            if (outsourcing.getEnterpriseInfonavit() != null){
                row6.createCell(12).setCellValue(outsourcing.getEnterpriseInfonavit().doubleValue());
                sum56 += outsourcing.getEnterpriseInfonavit().doubleValue();
            }
            if (outsourcing.getPayrollTax() != null){
                row6.createCell(13).setCellValue(outsourcing.getPayrollTax().doubleValue());
                sum57 += outsourcing.getPayrollTax().doubleValue();
            }
            if (outsourcing.getTotalSocialSecurity() != null){
                row6.createCell(14).setCellValue(outsourcing.getTotalSocialSecurity().doubleValue());
                sum58 += outsourcing.getTotalSocialSecurity().doubleValue();
            }
            if (outsourcing.getCommission() != null){
                row6.createCell(15).setCellValue(outsourcing.getCommission().doubleValue());
                sum59 += outsourcing.getCommission().doubleValue();
            }
            if (outsourcing.getSubtotal() != null){
                row6.createCell(16).setCellValue(outsourcing.getSubtotal().doubleValue());
                sum60 += outsourcing.getSubtotal().doubleValue();
            }
            if (outsourcing.getIva() != null){
                row6.createCell(17).setCellValue(outsourcing.getIva().doubleValue());
                sum61 += outsourcing.getIva().doubleValue();
            }
            if (outsourcing.getTotal() != null){
                row6.createCell(18).setCellValue(outsourcing.getTotal().doubleValue());
                sum62 += outsourcing.getTotal().doubleValue();
            }

            aux6++;
        }

        row6 = hoja6.createRow(aux6+2);

        row6.createCell(2).setCellValue("TOTALES");
        row6.createCell(3).setCellValue(sum47);
        row6.createCell(4).setCellValue(sum48);
        row6.createCell(5).setCellValue(sum49);
        row6.createCell(6).setCellValue(sum50);
        row6.createCell(7).setCellValue(sum51);
        row6.createCell(8).setCellValue(sum52);
        row6.createCell(9).setCellValue(sum53);
        row6.createCell(10).setCellValue(sum54);
        row6.createCell(11).setCellValue(sum55);
        row6.createCell(12).setCellValue(sum56);
        row6.createCell(13).setCellValue(sum57);
        row6.createCell(14).setCellValue(sum58);
        row6.createCell(15).setCellValue(sum59);
        row6.createCell(16).setCellValue(sum60);
        row6.createCell(17).setCellValue(sum61);
        row6.createCell(18).setCellValue(sum62);

        Sheet hoja3 = wb.createSheet("GMT ENZO");

        //Se crea la fila que contiene la cabecera
        Row row3 = hoja3.createRow(0);

        row3.createCell(0).setCellValue("N");
        row3.createCell(1).setCellValue("NOMBRE DEL EMPLEADO");
        row3.createCell(2).setCellValue("BANCO");
        row3.createCell(3).setCellValue("N. DE CUENTA");
        row3.createCell(4).setCellValue("CLABE");
        row3.createCell(5).setCellValue("SUCURSAL");
        row3.createCell(6).setCellValue("AREA");
        row3.createCell(7).setCellValue("RFC");
        row3.createCell(8).setCellValue("CURP");
        row3.createCell(9).setCellValue("MONTO A PAGAR");
        row3.createCell(10).setCellValue("11.50%");
        row3.createCell(11).setCellValue("TOTAL A FACTURAR");

        //Implementacion del estilo
        for (Cell celda : row3) {
            celda.setCellStyle(style);
        }

        int aux3 = 1;

        List<Payroll> payrolls = payrollDao.findAllByAmountPositives();

        Double sum63 = 0.0;
        Double sum64 = 0.0;
        Double sum65 = 0.0;

        for(Payroll payroll : payrolls){
            row3 = hoja3.createRow(aux3);

            if (payroll.getNumeroDeEmpleado() != null){
                row3.createCell(0).setCellValue(payroll.getNumeroDeEmpleado());
            }
            if (payroll.getNombre() != null){
                row3.createCell(1).setCellValue(payroll.getNombre());
            }
            if (payroll.getBanco() != null){
                row3.createCell(2).setCellValue(payroll.getBanco());
            }
            if (payroll.getNumeroDeCuenta() != null){
                row3.createCell(3).setCellValue(payroll.getNumeroDeCuenta());
            }
            if (payroll.getCuentaClabe() != null){
                row3.createCell(4).setCellValue(payroll.getCuentaClabe());
            }
            if (payroll.getSucursal() != null){
                row3.createCell(5).setCellValue(payroll.getSucursal());
            }
            if (payroll.getArea() != null){
                row3.createCell(6).setCellValue(payroll.getArea());
            }
            if (payroll.getRfc() != null){
                row3.createCell(7).setCellValue(payroll.getRfc());
            }
            if (payroll.getCurp() != null){
                row3.createCell(8).setCellValue(payroll.getCurp());
            }
            if (payroll.getPago() != null){
                row3.createCell(9).setCellValue(payroll.getPago().doubleValue());
                sum63 += payroll.getPago().doubleValue();
            }
            if (payroll.getComisionNec() != null){
                row3.createCell(10).setCellValue(payroll.getComisionNec().doubleValue());
                sum64 += payroll.getComisionNec().doubleValue();
            }
            if (payroll.getTotalFacturar() != null){
                row3.createCell(11).setCellValue(payroll.getTotalFacturar().doubleValue());
                sum65 += payroll.getTotalFacturar().doubleValue();
            }

            aux3 ++;

        }

        row3 = hoja3.createRow(aux3+2);

        row3.createCell(8).setCellValue("TOTALES");
        row3.createCell(9).setCellValue(sum63);
        row3.createCell(10).setCellValue(sum64);
        row3.createCell(11).setCellValue(sum65);

        Sheet hoja4 = wb.createSheet("TOTALES");

        //Se crea la fila que contiene la cabecera
        Row row4 = hoja4.createRow(0);


        //Implementacion del estilo
        for (Cell celda : row4) {
            celda.setCellStyle(style);
        }

        BigDecimal gmtNec = (BigDecimal) payrollDao.sumGmtNec();
        BigDecimal efectivoEdmon = (BigDecimal) payrollDao.sumEfectivoEdmon();
        BigDecimal rhmas = (BigDecimal) outsourcingDao.findSumRhmasByDwEnterprise(dwEnterprisesList,applicatioDateStart,applicationDateEnd);
        Double totalDeTotales = 0.00;
        if(gmtNec != null){
            totalDeTotales+=gmtNec.doubleValue();
            Row rowGmt = hoja4.createRow(3);

            rowGmt.createCell(1).setCellValue("GMT NEC");
            rowGmt.createCell(2).setCellValue(gmtNec.doubleValue());
        }
        if (efectivoEdmon != null){
            totalDeTotales+=efectivoEdmon.doubleValue();
            Row rowEfectivoEdmon = hoja4.createRow(5);

            rowEfectivoEdmon.createCell(1).setCellValue("EFECTIVO EDMON");
            rowEfectivoEdmon.createCell(2).setCellValue(efectivoEdmon.doubleValue());
        }
        if (rhmas != null){
            totalDeTotales+=rhmas.doubleValue();
            Row rowRhmas = hoja4.createRow(4);

            rowRhmas.createCell(1).setCellValue("RHMAS SEG, MSJ y GMT");
            rowRhmas.createCell(2).setCellValue(rhmas.doubleValue());
        }


        Row rowTotal = hoja4.createRow(6);

        rowTotal.createCell(1).setCellValue("TOTAL");
        rowTotal.createCell(2).setCellValue(totalDeTotales);

        hoja1.autoSizeColumn(0);
        hoja2.autoSizeColumn(0);
        hoja3.autoSizeColumn(0);
        hoja4.autoSizeColumn(0);

        wb.write(fileOutputStream);
        wb.write(outputStream);
    }

    @Override
    public void corporateFortyName(OutputStream outputStream, LocalDateTime applicatioDateStart, LocalDateTime applicationDateEnd, FileOutputStream fileOutputStream) throws IOException {

        Workbook wb = new XSSFWorkbook();
        //Definicion del estilo de la cabecera
        Font font = wb.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 10);
        font.setFontName("Arial");
        font.setColor(IndexedColors.WHITE.getIndex());
        CellStyle style = wb.createCellStyle();
        style.setFont(font);
        style.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        style.setAlignment(CellStyle.ALIGN_CENTER);

        CellStyle cellDateStyle = wb.createCellStyle();
        CreationHelper createHelper = wb.getCreationHelper();
        cellDateStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/MM/yyyy"));

        Sheet hoja1 = wb.createSheet("MATRIZ");

        Row row1 = hoja1.createRow(0);

        row1.createCell(0).setCellValue("NÚMERO DE EMPLEADO");
        row1.createCell(1).setCellValue("EMPRESA");
        row1.createCell(2).setCellValue("REGION");
        row1.createCell(3).setCellValue("ZONA");
        row1.createCell(4).setCellValue("NOMBRE DEL EMPLEADO");
        row1.createCell(5).setCellValue("CLAVE SAP");
        row1.createCell(6).setCellValue("PUESTO");
        row1.createCell(7).setCellValue("BANCO");
        row1.createCell(8).setCellValue("CUENTA");
        row1.createCell(9).setCellValue("CLABE");
        row1.createCell(10).setCellValue("SUCURSAL");
        row1.createCell(11).setCellValue("RFC");
        row1.createCell(12).setCellValue("CURP");
        row1.createCell(13).setCellValue("FECHA DE INGRESO");
        row1.createCell(14).setCellValue("SUELDO QUINCENAL");
        row1.createCell(15).setCellValue("PRIMA VACACIONAL");
        row1.createCell(16).setCellValue("BONO");
        row1.createCell(17).setCellValue("AJUSTE");
        row1.createCell(18).setCellValue("IMMS RHMAS PAGO");
        row1.createCell(19).setCellValue("RH MAS TOTAL A FACTURAR");
        row1.createCell(20).setCellValue("DESCUENTO");
        row1.createCell(21).setCellValue("PERCEPCIONES");
        row1.createCell(22).setCellValue("DEDUCCIONES");
        row1.createCell(23).setCellValue("DEPOSITOS ASIMILABLES");

        //Implementacion del estilo
        for (Cell celda : row1) {
            celda.setCellStyle(style);
        }

        int aux1 = 1;

        List<Payroll> payrollList = payrollDao.findAll();

        for (Payroll payroll : payrollList){
            row1 = hoja1.createRow(aux1);

            if (payroll.getNumeroDeEmpleado() != null){
                row1.createCell(0).setCellValue(payroll.getNumeroDeEmpleado());
            }
            if (payroll.getDistributorName() != null){
                row1.createCell(1).setCellValue(payroll.getDistributorName());
            }
            if (payroll.getRegionName() != null){
                row1.createCell(2).setCellValue(payroll.getRegionName());
            }
            if (payroll.getZonaName() != null){
                row1.createCell(3).setCellValue(payroll.getZonaName());
            }
            if (payroll.getNombre() != null){
                row1.createCell(4).setCellValue(payroll.getNombre());
            }
            if (payroll.getClaveSap() != null){
                row1.createCell(5).setCellValue(payroll.getClaveSap());
            }
            if (payroll.getPuesto() != null){
                row1.createCell(6).setCellValue(payroll.getPuesto());
            }
            if (payroll.getBanco() != null){
                row1.createCell(7).setCellValue(payroll.getBanco());
            }
            if (payroll.getNumeroDeCuenta() != null){
                row1.createCell(8).setCellValue(payroll.getNumeroDeCuenta());
            }
            if (payroll.getCuentaClabe() != null){
                row1.createCell(9).setCellValue(payroll.getCuentaClabe());
            }
            if (payroll.getSucursal() != null){
                row1.createCell(10).setCellValue(payroll.getSucursal());
            }
            if (payroll.getRfc() != null){
                row1.createCell(11).setCellValue(payroll.getRfc());
            }
            if (payroll.getCurp() != null){
                row1.createCell(12).setCellValue(payroll.getCurp());
            }
            if(payroll.getFechaIngreso() != null){
                Date joinDate = Date.from(payroll.getFechaIngreso().atStartOfDay(ZoneId.systemDefault()).toInstant());
                row1.createCell(13);
                row1.getCell(13).setCellValue(joinDate);
                row1.getCell(13).setCellStyle(cellDateStyle);
            }
            if (payroll.getSueldo() != null){
                row1.createCell(14).setCellValue(payroll.getSueldo().doubleValue());
            }
            if (payroll.getPrimaVacacional() != null){
                row1.createCell(15).setCellValue(payroll.getPrimaVacacional().doubleValue());
            }
            if (payroll.getBono() != null){
                row1.createCell(16).setCellValue(payroll.getBono().doubleValue());
            }
            if (payroll.getAjuste() != null){
                row1.createCell(17).setCellValue(payroll.getAjuste().doubleValue());
            }
            if (payroll.getRhmasPago() != null){
                row1.createCell(18).setCellValue(payroll.getRhmasPago().doubleValue());
            }
            if (payroll.getRhmasTotalFacturar() != null){
                row1.createCell(19).setCellValue(payroll.getRhmasTotalFacturar().doubleValue());
            }
            if (payroll.getDescuento() != null){
                row1.createCell(20).setCellValue(payroll.getDescuento().doubleValue());
            }
            if (payroll.getPercepcion() != null){
                row1.createCell(21).setCellValue(payroll.getPercepcion().doubleValue());
            }
            if (payroll.getDeduccion() != null){
                row1.createCell(22).setCellValue(payroll.getDeduccion().doubleValue());
            }
            if (payroll.getPago() != null){
                row1.createCell(23).setCellValue(payroll.getPago().doubleValue());
            }

            aux1++;
        }

        Sheet hoja2 = wb.createSheet("RHMAS AMER");

        //Se crea la fila que contiene la cabecera
        Row row2 = hoja2.createRow(0);

        row2.createCell(0).setCellValue("DEPARTAMENTO");
        row2.createCell(1).setCellValue("CODIGO");
        row2.createCell(2).setCellValue("NOMBRE");
        row2.createCell(3).setCellValue("SUELDO");
        row2.createCell(4).setCellValue("SUBSIDIO");
        row2.createCell(5).setCellValue("IMSS EMPLEADO");
        row2.createCell(6).setCellValue("ISR");
        row2.createCell(7).setCellValue("AJUSTE AL NETO");
        row2.createCell(8).setCellValue("TOTAL DEDUCCIONES");
        row2.createCell(9).setCellValue("NETO SUELDO FISCAL");
        row2.createCell(10).setCellValue("IMSS");
        row2.createCell(11).setCellValue("RCV");
        row2.createCell(12).setCellValue("INFONAVIT EMPRESA");
        row2.createCell(13).setCellValue("IMPUESTO SOBRE NÓMINA");
        row2.createCell(14).setCellValue("TOTAL PREVISION SOCIAL");
        row2.createCell(15).setCellValue("COMISIÓN");
        row2.createCell(16).setCellValue("SUBTOTAL");
        row2.createCell(17).setCellValue("IVA");
        row2.createCell(18).setCellValue("TOTAL");

        //Implementacion del estilo
        for (Cell celda : row2) {
            celda.setCellStyle(style);
        }

        List<DwEnterprises> dwEnterprisesList = dwEnterprisesDao.findByDistributor(2);
        List<Outsourcing> outsourcingList = outsourcingDao.findByDwEnterprise(dwEnterprisesList,applicatioDateStart,applicationDateEnd);

        int aux2 = 1;

        Double sum1 = 0.0;
        Double sum2 = 0.0;
        Double sum3 = 0.0;
        Double sum4 = 0.0;
        Double sum5 = 0.0;
        Double sum6 = 0.0;
        Double sum7 = 0.0;
        Double sum8 = 0.0;
        Double sum9 = 0.0;
        Double sum10 = 0.0;
        Double sum11 = 0.0;
        Double sum12 = 0.0;
        Double sum13 = 0.0;
        Double sum14 = 0.0;
        Double sum15 = 0.0;
        Double sum16 = 0.0;

        for (Outsourcing outsourcing : outsourcingList){
            row2 = hoja2.createRow(aux2);

            if (outsourcing.getDwEnterprises() != null){
                if (outsourcing.getDwEnterprises().getArea() != null){
                    row2.createCell(0).setCellValue(outsourcing.getDwEnterprises().getArea().getAreaName());
                }
            }
            if(outsourcing.getEmployee() != null){
                row2.createCell(1).setCellValue(outsourcing.getEmployee().getIdEmployee());
            }
            if(outsourcing.getEmployee() != null){
                row2.createCell(2).setCellValue(outsourcing.getEmployee().getFullName());
            }
            if(outsourcing.getSalary() != null){
                row2.createCell(3).setCellValue(outsourcing.getSalary().doubleValue());
                sum1 += outsourcing.getSalary().doubleValue();
            }
            if(outsourcing.getSubsidy() != null){
                row2.createCell(4).setCellValue(outsourcing.getSubsidy().doubleValue());
                sum2 += outsourcing.getSubsidy().doubleValue();
            }
            if (outsourcing.getImssEmployee() != null){
                row2.createCell(5).setCellValue(outsourcing.getImssEmployee().doubleValue());
                sum3 += outsourcing.getImssEmployee().doubleValue();
            }
            if (outsourcing.getIsr() != null){
                row2.createCell(6).setCellValue(outsourcing.getIsr().doubleValue());
                sum4 += outsourcing.getIsr().doubleValue();
            }
            if (outsourcing.getAdjustment() != null){
                row2.createCell(7).setCellValue(outsourcing.getAdjustment().doubleValue());
                sum5 += outsourcing.getAdjustment().doubleValue();
            }
            if (outsourcing.getTotalDeductions() != null){
                row2.createCell(8).setCellValue(outsourcing.getTotalDeductions().doubleValue());
                sum6 += outsourcing.getTotalDeductions().doubleValue();
            }
            if (outsourcing.getNetAssetTax() != null){
                row2.createCell(9).setCellValue(outsourcing.getNetAssetTax().doubleValue());
                sum7 += outsourcing.getNetAssetTax().doubleValue();
            }
            if (outsourcing.getImss() != null){
                row2.createCell(10).setCellValue(outsourcing.getImss().doubleValue());
                sum8 += outsourcing.getImss().doubleValue();
            }
            if (outsourcing.getRcv() != null){
                row2.createCell(11).setCellValue(outsourcing.getRcv().doubleValue());
                sum9 += outsourcing.getRcv().doubleValue();
            }
            if (outsourcing.getEnterpriseInfonavit() != null){
                row2.createCell(12).setCellValue(outsourcing.getEnterpriseInfonavit().doubleValue());
                sum10 += outsourcing.getEnterpriseInfonavit().doubleValue();
            }
            if (outsourcing.getPayrollTax() != null){
                row2.createCell(13).setCellValue(outsourcing.getPayrollTax().doubleValue());
                sum11 += outsourcing.getPayrollTax().doubleValue();
            }
            if (outsourcing.getTotalSocialSecurity() != null){
                row2.createCell(14).setCellValue(outsourcing.getTotalSocialSecurity().doubleValue());
                sum12 += outsourcing.getTotalSocialSecurity().doubleValue();
            }
            if (outsourcing.getCommission() != null){
                row2.createCell(15).setCellValue(outsourcing.getCommission().doubleValue());
                sum13 += outsourcing.getCommission().doubleValue();
            }
            if (outsourcing.getSubtotal() != null){
                row2.createCell(16).setCellValue(outsourcing.getSubtotal().doubleValue());
                sum14 += outsourcing.getSubtotal().doubleValue();
            }
            if (outsourcing.getIva() != null){
                row2.createCell(17).setCellValue(outsourcing.getIva().doubleValue());
                sum15 += outsourcing.getIva().doubleValue();
            }
            if (outsourcing.getTotal() != null){
                row2.createCell(18).setCellValue(outsourcing.getTotal().doubleValue());
                sum16 += outsourcing.getTotal().doubleValue();
            }

            aux2++;
        }

        row2 = hoja2.createRow(aux2+2);

        row2.createCell(2).setCellValue("TOTALES");
        row2.createCell(3).setCellValue(sum1);
        row2.createCell(4).setCellValue(sum2);
        row2.createCell(5).setCellValue(sum3);
        row2.createCell(6).setCellValue(sum4);
        row2.createCell(7).setCellValue(sum5);
        row2.createCell(8).setCellValue(sum6);
        row2.createCell(9).setCellValue(sum7);
        row2.createCell(10).setCellValue(sum8);
        row2.createCell(11).setCellValue(sum9);
        row2.createCell(12).setCellValue(sum10);
        row2.createCell(13).setCellValue(sum11);
        row2.createCell(14).setCellValue(sum12);
        row2.createCell(15).setCellValue(sum13);
        row2.createCell(16).setCellValue(sum14);
        row2.createCell(17).setCellValue(sum15);
        row2.createCell(18).setCellValue(sum16);

        Sheet hoja3 = wb.createSheet("RHMAS AMERMEDIA");

        //Se crea la fila que contiene la cabecera
        Row row3 = hoja3.createRow(0);

        row3.createCell(0).setCellValue("DEPARTAMENTO");
        row3.createCell(1).setCellValue("CODIGO");
        row3.createCell(2).setCellValue("NOMBRE");
        row3.createCell(3).setCellValue("SUELDO");
        row3.createCell(4).setCellValue("SUBSIDIO");
        row3.createCell(5).setCellValue("IMSS EMPLEADO");
        row3.createCell(6).setCellValue("ISR");
        row3.createCell(7).setCellValue("AJUSTE AL NETO");
        row3.createCell(8).setCellValue("TOTAL DEDUCCIONES");
        row3.createCell(9).setCellValue("NETO SUELDO FISCAL");
        row3.createCell(10).setCellValue("IMSS");
        row3.createCell(11).setCellValue("RCV");
        row3.createCell(12).setCellValue("INFONAVIT EMPRESA");
        row3.createCell(13).setCellValue("IMPUESTO SOBRE NÓMINA");
        row3.createCell(14).setCellValue("TOTAL PREVISION SOCIAL");
        row3.createCell(15).setCellValue("COMISIÓN");
        row3.createCell(16).setCellValue("SUBTOTAL");
        row3.createCell(17).setCellValue("IVA");
        row3.createCell(18).setCellValue("TOTAL");

        //Implementacion del estilo
        for (Cell celda : row3) {
            celda.setCellStyle(style);
        }

        List<DwEnterprises> dwEnterList = dwEnterprisesDao.findByDistributor(3);
        List<Outsourcing> outsourcings = outsourcingDao.findByDwEnterprise(dwEnterList,applicatioDateStart,applicationDateEnd);

        int aux3 = 1;

        Double sum17 = 0.0;
        Double sum18 = 0.0;
        Double sum19 = 0.0;
        Double sum20 = 0.0;
        Double sum21 = 0.0;
        Double sum22 = 0.0;
        Double sum23 = 0.0;
        Double sum24 = 0.0;
        Double sum25 = 0.0;
        Double sum26 = 0.0;
        Double sum27 = 0.0;
        Double sum28 = 0.0;
        Double sum29 = 0.0;
        Double sum30 = 0.0;
        Double sum31 = 0.0;
        Double sum32 = 0.0;

        for (Outsourcing outsourcing : outsourcings){
            row3 = hoja3.createRow(aux3);

            if (outsourcing.getDwEnterprises() != null){
                if (outsourcing.getDwEnterprises().getArea() != null){
                    row3.createCell(0).setCellValue(outsourcing.getDwEnterprises().getArea().getAreaName());
                }
            }
            if(outsourcing.getEmployee() != null){
                row3.createCell(1).setCellValue(outsourcing.getEmployee().getIdEmployee());
            }
            if(outsourcing.getEmployee() != null){
                row3.createCell(2).setCellValue(outsourcing.getEmployee().getFullName());
            }
            if(outsourcing.getSalary() != null){
                row3.createCell(3).setCellValue(outsourcing.getSalary().doubleValue());
                sum17 += outsourcing.getTotal().doubleValue();
            }
            if(outsourcing.getSubsidy() != null){
                row3.createCell(4).setCellValue(outsourcing.getSubsidy().doubleValue());
                sum18 += outsourcing.getTotal().doubleValue();
            }
            if (outsourcing.getImssEmployee() != null){
                row3.createCell(5).setCellValue(outsourcing.getImssEmployee().doubleValue());
                sum19 += outsourcing.getTotal().doubleValue();
            }
            if (outsourcing.getIsr() != null){
                row3.createCell(6).setCellValue(outsourcing.getIsr().doubleValue());
                sum20 += outsourcing.getTotal().doubleValue();
            }
            if (outsourcing.getAdjustment() != null){
                row3.createCell(7).setCellValue(outsourcing.getAdjustment().doubleValue());
                sum21 += outsourcing.getTotal().doubleValue();
            }
            if (outsourcing.getTotalDeductions() != null){
                row3.createCell(8).setCellValue(outsourcing.getTotalDeductions().doubleValue());
                sum22 += outsourcing.getTotal().doubleValue();
            }
            if (outsourcing.getNetAssetTax() != null){
                row3.createCell(9).setCellValue(outsourcing.getNetAssetTax().doubleValue());
                sum23 += outsourcing.getTotal().doubleValue();
            }
            if (outsourcing.getImss() != null){
                row3.createCell(10).setCellValue(outsourcing.getImss().doubleValue());
                sum24 += outsourcing.getTotal().doubleValue();
            }
            if (outsourcing.getRcv() != null){
                row3.createCell(11).setCellValue(outsourcing.getRcv().doubleValue());
                sum25 += outsourcing.getTotal().doubleValue();
            }
            if (outsourcing.getEnterpriseInfonavit() != null){
                row3.createCell(12).setCellValue(outsourcing.getEnterpriseInfonavit().doubleValue());
                sum26 += outsourcing.getTotal().doubleValue();
            }
            if (outsourcing.getPayrollTax() != null){
                row3.createCell(13).setCellValue(outsourcing.getPayrollTax().doubleValue());
                sum27 += outsourcing.getTotal().doubleValue();
            }
            if (outsourcing.getTotalSocialSecurity() != null){
                row3.createCell(14).setCellValue(outsourcing.getTotalSocialSecurity().doubleValue());
                sum28 += outsourcing.getTotal().doubleValue();
            }
            if (outsourcing.getCommission() != null){
                row3.createCell(15).setCellValue(outsourcing.getCommission().doubleValue());
                sum29 += outsourcing.getTotal().doubleValue();
            }
            if (outsourcing.getSubtotal() != null){
                row3.createCell(16).setCellValue(outsourcing.getSubtotal().doubleValue());
                sum30 += outsourcing.getTotal().doubleValue();
            }
            if (outsourcing.getIva() != null){
                row3.createCell(17).setCellValue(outsourcing.getIva().doubleValue());
                sum31 += outsourcing.getTotal().doubleValue();
            }
            if (outsourcing.getTotal() != null){
                row3.createCell(18).setCellValue(outsourcing.getTotal().doubleValue());
                sum32 += outsourcing.getTotal().doubleValue();
            }

            aux3++;
        }

        row3 = hoja3.createRow(aux3+2);

        row3.createCell(2).setCellValue("TOTALES");
        row3.createCell(3).setCellValue(sum17);
        row3.createCell(4).setCellValue(sum18);
        row3.createCell(5).setCellValue(sum19);
        row3.createCell(6).setCellValue(sum20);
        row3.createCell(7).setCellValue(sum21);
        row3.createCell(8).setCellValue(sum22);
        row3.createCell(9).setCellValue(sum23);
        row3.createCell(10).setCellValue(sum24);
        row3.createCell(11).setCellValue(sum25);
        row3.createCell(12).setCellValue(sum26);
        row3.createCell(13).setCellValue(sum27);
        row3.createCell(14).setCellValue(sum28);
        row3.createCell(15).setCellValue(sum29);
        row3.createCell(16).setCellValue(sum30);
        row3.createCell(17).setCellValue(sum31);
        row3.createCell(18).setCellValue(sum32);

        Sheet hoja4 = wb.createSheet("AMER NEC");

        //Se crea la fila que contiene la cabecera
        Row row4 = hoja4.createRow(0);

        row4.createCell(0).setCellValue("N");
        row4.createCell(1).setCellValue("NOMBRE DEL EMPLEADO");
        row4.createCell(2).setCellValue("BANCO");
        row4.createCell(3).setCellValue("N. DE CUENTA");
        row4.createCell(4).setCellValue("CLABE");
        row4.createCell(5).setCellValue("SUCURSAL");
        row4.createCell(6).setCellValue("AREA");
        row4.createCell(7).setCellValue("RFC");
        row4.createCell(8).setCellValue("CURP");
        row4.createCell(9).setCellValue("MONTO A PAGAR");
        row4.createCell(10).setCellValue("11.50%");
        row4.createCell(11).setCellValue("TOTAL A FACTURAR");

        //Implementacion del estilo
        for (Cell celda : row4) {
            celda.setCellStyle(style);
        }

        int aux4 = 1;

        List <Payroll> amerNec = payrollDao.findByDistributor(2);

        Double sum33 = 0.0;
        Double sum34 = 0.0;
        Double sum35 = 0.0;

        for(Payroll payroll : amerNec){
            row4 = hoja4.createRow(aux4);

            if (payroll.getNumeroDeEmpleado() != null){
                row4.createCell(0).setCellValue(payroll.getNumeroDeEmpleado());
            }
            if (payroll.getNombre() != null){
                row4.createCell(1).setCellValue(payroll.getNombre());
            }
            if (payroll.getBanco() != null){
                row4.createCell(2).setCellValue(payroll.getBanco());
            }
            if (payroll.getNumeroDeCuenta() != null){
                row4.createCell(3).setCellValue(payroll.getNumeroDeCuenta());
            }
            if (payroll.getCuentaClabe() != null){
                row4.createCell(4).setCellValue(payroll.getCuentaClabe());
            }
            if (payroll.getSucursal() != null){
                row4.createCell(5).setCellValue(payroll.getSucursal());
            }
            if (payroll.getArea() != null){
                row4.createCell(6).setCellValue(payroll.getArea());
            }
            if (payroll.getRfc() != null){
                row4.createCell(7).setCellValue(payroll.getRfc());
            }
            if (payroll.getCurp() != null){
                row4.createCell(8).setCellValue(payroll.getCurp());
            }
            if (payroll.getPago() != null){
                row4.createCell(9).setCellValue(payroll.getPago().doubleValue());
                sum33 += payroll.getPago().doubleValue();
            }
            if (payroll.getComisionNec() != null){
                row4.createCell(10).setCellValue(payroll.getComisionNec().doubleValue());
                sum34 += payroll.getComisionNec().doubleValue();
            }
            if (payroll.getTotalFacturar() != null){
                row4.createCell(11).setCellValue(payroll.getTotalFacturar().doubleValue());
                sum35 += payroll.getTotalFacturar().doubleValue();
            }

            aux4 ++;

        }

        row4 = hoja4.createRow(aux4+2);

        row4.createCell(8).setCellValue("TOTALES");
        row4.createCell(9).setCellValue(sum33);
        row4.createCell(10).setCellValue(sum34);
        row4.createCell(11).setCellValue(sum35);

        Sheet hoja5 = wb.createSheet("AMERMEDIA NEC");

        //Se crea la fila que contiene la cabecera
        Row row5 = hoja5.createRow(0);

        row5.createCell(0).setCellValue("N");
        row5.createCell(1).setCellValue("NOMBRE DEL EMPLEADO");
        row5.createCell(2).setCellValue("BANCO");
        row5.createCell(3).setCellValue("N. DE CUENTA");
        row5.createCell(4).setCellValue("CLABE");
        row5.createCell(5).setCellValue("SUCURSAL");
        row5.createCell(6).setCellValue("AREA");
        row5.createCell(7).setCellValue("RFC");
        row5.createCell(8).setCellValue("CURP");
        row5.createCell(9).setCellValue("MONTO A PAGAR");
        row5.createCell(10).setCellValue("11.50%");
        row5.createCell(11).setCellValue("TOTAL A FACTURAR");

        //Implementacion del estilo
        for (Cell celda : row5) {
            celda.setCellStyle(style);
        }

        int aux5 = 1;

        List <Payroll> amermediaNec = payrollDao.findByDistributor(3);

        Double sum36 = 0.0;
        Double sum37 = 0.0;
        Double sum38 = 0.0;

        for(Payroll payroll : amermediaNec){
            row5 = hoja5.createRow(aux5);

            if (payroll.getNumeroDeEmpleado() != null){
                row5.createCell(0).setCellValue(payroll.getNumeroDeEmpleado());
            }
            if (payroll.getNombre() != null){
                row5.createCell(1).setCellValue(payroll.getNombre());
            }
            if (payroll.getBanco() != null){
                row5.createCell(2).setCellValue(payroll.getBanco());
            }
            if (payroll.getNumeroDeCuenta() != null){
                row5.createCell(3).setCellValue(payroll.getNumeroDeCuenta());
            }
            if (payroll.getCuentaClabe() != null){
                row5.createCell(4).setCellValue(payroll.getCuentaClabe());
            }
            if (payroll.getSucursal() != null){
                row5.createCell(5).setCellValue(payroll.getSucursal());
            }
            if (payroll.getArea() != null){
                row5.createCell(6).setCellValue(payroll.getArea());
            }
            if (payroll.getRfc() != null){
                row5.createCell(7).setCellValue(payroll.getRfc());
            }
            if (payroll.getCurp() != null){
                row5.createCell(8).setCellValue(payroll.getCurp());
            }
            if (payroll.getPago() != null){
                row5.createCell(9).setCellValue(payroll.getPago().doubleValue());
                sum36 += payroll.getPago().doubleValue();
            }
            if (payroll.getComisionNec() != null){
                row5.createCell(10).setCellValue(payroll.getComisionNec().doubleValue());
                sum37 += payroll.getComisionNec().doubleValue();
            }
            if (payroll.getTotalFacturar() != null){
                row5.createCell(11).setCellValue(payroll.getTotalFacturar().doubleValue());
                sum38 += payroll.getTotalFacturar().doubleValue();
            }

            aux5 ++;

        }

        row5 = hoja5.createRow(aux5+2);

        row5.createCell(8).setCellValue("TOTALES");
        row5.createCell(9).setCellValue(sum36);
        row5.createCell(10).setCellValue(sum37);
        row5.createCell(11).setCellValue(sum38);

        Sheet hoja6 = wb.createSheet("TOTALES");

        //Se crea la fila que contiene la cabecera
        Row row6 = hoja6.createRow(0);


        //Implementacion del estilo
        for (Cell celda : row6) {
            celda.setCellStyle(style);
        }

        BigDecimal amerNecSum = (BigDecimal) payrollDao.sumDistributorNec(2);
        BigDecimal amermediaNecSum = (BigDecimal) payrollDao.sumDistributorNec(3);
        BigDecimal rhmasAmer  = (BigDecimal) outsourcingDao.findSumRhmasByDwEnterprise(dwEnterprisesList,applicatioDateStart,applicationDateEnd);
        BigDecimal rhmasAmermedia  = (BigDecimal) outsourcingDao.findSumRhmasByDwEnterprise(dwEnterList,applicatioDateStart,applicationDateEnd);

        Double totalDeTotales = 0.00;

        if(amerNecSum != null){
            totalDeTotales+=amerNecSum.doubleValue();
            Row rowAmerNec = hoja6.createRow(3);

            rowAmerNec.createCell(1).setCellValue("AMER NEC");
            rowAmerNec.createCell(2).setCellValue(amerNecSum.doubleValue());
        }
        if(amermediaNecSum != null){
            totalDeTotales+=amermediaNecSum.doubleValue();
            Row rowAmermediaNec = hoja6.createRow(4);

            rowAmermediaNec.createCell(1).setCellValue("AMERMEDIA NEC");
            rowAmermediaNec.createCell(2).setCellValue(amermediaNecSum.doubleValue());
        }
        if (rhmasAmer != null){
            totalDeTotales+=rhmasAmer.doubleValue();
            Row rowRhmasAmer = hoja6.createRow(5);

            rowRhmasAmer.createCell(1).setCellValue("RHMAS AMER");
            rowRhmasAmer.createCell(2).setCellValue(rhmasAmer.doubleValue());
        }
        if (rhmasAmermedia != null){
            totalDeTotales+=rhmasAmermedia.doubleValue();
            Row rowRhmasAmermedia = hoja6.createRow(6);

            rowRhmasAmermedia.createCell(1).setCellValue("RHMAS AMERMEDIA");
            rowRhmasAmermedia.createCell(2).setCellValue(rhmasAmermedia.doubleValue());
        }


        Row rowTotal = hoja6.createRow(8);

        rowTotal.createCell(1).setCellValue("TOTAL");
        rowTotal.createCell(2).setCellValue(totalDeTotales);

        hoja1.autoSizeColumn(0);
        hoja2.autoSizeColumn(0);
        hoja3.autoSizeColumn(0);
        hoja4.autoSizeColumn(0);
        hoja5.autoSizeColumn(0);
        hoja6.autoSizeColumn(0);

        wb.write(fileOutputStream);
        wb.write(outputStream);
    }

    @Override
    public void reportWeeklyPay(OutputStream outputStream, LocalDateTime applicatioDateStart, LocalDateTime applicationDateEnd, FileOutputStream fileOutputStream, List queryResult) throws IOException {
        Workbook wb = new XSSFWorkbook();
        //Definicion del estilo de la cabecera
        Font font = wb.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 10);
        font.setFontName("Arial");
        font.setColor(IndexedColors.WHITE.getIndex());
        CellStyle style = wb.createCellStyle();
        style.setFont(font);
        style.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        style.setAlignment(CellStyle.ALIGN_CENTER);

        CellStyle cellDateStyle = wb.createCellStyle();
        CreationHelper createHelper = wb.getCreationHelper();
        cellDateStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/MM/yyyy"));

        Sheet hoja1 = wb.createSheet("MATRIZ");

        Row row1 = hoja1.createRow(0);

        row1.createCell(0).setCellValue("NÚMERO DE EMPLEADO");
        row1.createCell(1).setCellValue("EMPRESA");
        row1.createCell(2).setCellValue("REGION");
        row1.createCell(3).setCellValue("ZONA");
        row1.createCell(4).setCellValue("NOMBRE");
        row1.createCell(5).setCellValue("CLAVE SAP");
        row1.createCell(6).setCellValue("PUESTO");
        row1.createCell(7).setCellValue("BANCO");
        row1.createCell(8).setCellValue("CUENTA");
        row1.createCell(9).setCellValue("CLABE");
        row1.createCell(10).setCellValue("SUCURSAL");
        row1.createCell(11).setCellValue("RFC");
        row1.createCell(12).setCellValue("CURP");
        row1.createCell(13).setCellValue("FECHA DE INGRESO");
        row1.createCell(14).setCellValue("APOYO 375");
        row1.createCell(15).setCellValue("MONTO_PROMOTOR");
        row1.createCell(16).setCellValue("COMISIÓN");
        row1.createCell(17).setCellValue("BONO CUMPLIMIENTO");
        row1.createCell(18).setCellValue("AJUSTES");
        row1.createCell(19).setCellValue("APOYO PASAJES");
        row1.createCell(20).setCellValue("DESCUENTOS");
        row1.createCell(21).setCellValue("IMMS RHMAS PAGO");
        row1.createCell(22).setCellValue("RH MAS TOTAL A FACTURAR");
        row1.createCell(23).setCellValue("PERCEPCIONES");
        row1.createCell(24).setCellValue("DEDUCCIONES");
        row1.createCell(25).setCellValue("DEPOSITO ASIMILABLES");

        //Implementacion del estilo
        for (Cell celda : row1) {
            celda.setCellStyle(style);
        }

        int aux1 = 1;

        List<Payroll> payrollList = payrollDao.findAll();

        for (Payroll payroll : payrollList){
            row1 = hoja1.createRow(aux1);

            if (payroll.getNumeroDeEmpleado() != null){
                row1.createCell(0).setCellValue(payroll.getNumeroDeEmpleado());
            }
            if (payroll.getDistributorName() != null){
                row1.createCell(1).setCellValue(payroll.getDistributorName());
            }
            if (payroll.getRegionName() != null){
                row1.createCell(2).setCellValue(payroll.getRegionName());
            }
            if (payroll.getZonaName() != null){
                row1.createCell(3).setCellValue(payroll.getZonaName());
            }
            if (payroll.getNombre() != null){
                row1.createCell(4).setCellValue(payroll.getNombre());
            }
            if (payroll.getClaveSap() != null){
                row1.createCell(5).setCellValue(payroll.getClaveSap());
            }
            if (payroll.getPuesto() != null){
                row1.createCell(6).setCellValue(payroll.getPuesto());
            }
            if (payroll.getBanco() != null){
                row1.createCell(7).setCellValue(payroll.getBanco());
            }
            if (payroll.getNumeroDeCuenta() != null){
                row1.createCell(8).setCellValue(payroll.getNumeroDeCuenta());
            }
            if (payroll.getCuentaClabe() != null){
                row1.createCell(9).setCellValue(payroll.getCuentaClabe());
            }
            if (payroll.getSucursal() != null){
                row1.createCell(10).setCellValue(payroll.getSucursal());
            }
            if (payroll.getRfc() != null){
                row1.createCell(11).setCellValue(payroll.getRfc());
            }
            if (payroll.getCurp() != null){
                row1.createCell(12).setCellValue(payroll.getCurp());
            }
            if(payroll.getFechaIngreso() != null){
                Date joinDate = Date.from(payroll.getFechaIngreso().atStartOfDay(ZoneId.systemDefault()).toInstant());
                row1.createCell(13);
                row1.getCell(13).setCellValue(joinDate);
                row1.getCell(13).setCellStyle(cellDateStyle);
            }
            if (payroll.getComissionPromotor() != null){
                row1.createCell(14).setCellValue(payroll.getApoyo375().doubleValue());
            }
            if (payroll.getMontoPromotor() != null){
                row1.createCell(15).setCellValue(payroll.getMontoPromotor().doubleValue());
            }
            if (payroll.getComissionPromotor() != null){
                row1.createCell(16).setCellValue(payroll.getComissionPromotor().doubleValue());
            }
            if (payroll.getBono() != null){
                row1.createCell(17).setCellValue(payroll.getBono().doubleValue());
            }
            if (payroll.getAjuste() != null){
                row1.createCell(18).setCellValue(payroll.getAjuste().doubleValue());
            }
            if (payroll.getApoyoPasajes() != null){
                row1.createCell(19).setCellValue(payroll.getApoyoPasajes().doubleValue());
            }
            if (payroll.getDescuento() != null){
                row1.createCell(20).setCellValue(payroll.getDescuento().doubleValue());
            }
            if (payroll.getRhmasPago() != null){
                row1.createCell(21).setCellValue(payroll.getRhmasPago().doubleValue());
            }
            if (payroll.getRhmasTotalFacturar() != null){
                row1.createCell(22).setCellValue(payroll.getRhmasTotalFacturar().doubleValue());
            }
            if (payroll.getPercepcion() != null){
                row1.createCell(23).setCellValue(payroll.getPercepcion().doubleValue());
            }
            if (payroll.getDeduccion() != null){
                row1.createCell(24).setCellValue(payroll.getDeduccion().doubleValue());
            }
            if (payroll.getPago() != null){
                row1.createCell(25).setCellValue(payroll.getPago().doubleValue());
            }

            aux1++;
        }

        Sheet hoja2 = wb.createSheet("RHMAS AMER");

        //Se crea la fila que contiene la cabecera
        Row row2 = hoja2.createRow(0);

        row2.createCell(0).setCellValue("DEPARTAMENTO");
        row2.createCell(1).setCellValue("CODIGO");
        row2.createCell(2).setCellValue("NOMBRE");
        row2.createCell(3).setCellValue("SUELDO");
        row2.createCell(4).setCellValue("SUBSIDIO");
        row2.createCell(5).setCellValue("IMSS EMPLEADO");
        row2.createCell(6).setCellValue("ISR");
        row2.createCell(7).setCellValue("AJUSTE AL NETO");
        row2.createCell(8).setCellValue("TOTAL DEDUCCIONES");
        row2.createCell(9).setCellValue("NETO SUELDO FISCAL");
        row2.createCell(10).setCellValue("IMSS");
        row2.createCell(11).setCellValue("RCV");
        row2.createCell(12).setCellValue("INFONAVIT EMPRESA");
        row2.createCell(13).setCellValue("IMPUESTO SOBRE NÓMINA");
        row2.createCell(14).setCellValue("TOTAL PREVISION SOCIAL");
        row2.createCell(15).setCellValue("COMISIÓN");
        row2.createCell(16).setCellValue("SUBTOTAL");
        row2.createCell(17).setCellValue("IVA");
        row2.createCell(18).setCellValue("TOTAL");

        //Implementacion del estilo
        for (Cell celda : row2) {
            celda.setCellStyle(style);
        }

        List<DwEnterprises> dwEnterprisesList = dwEnterprisesDao.findByDistributor(2);
        List<Outsourcing> outsourcingList = outsourcingDao.findByDwEnterprise(dwEnterprisesList,applicatioDateStart,applicationDateEnd);

        int aux2 = 1;

        Double sum1 = 0.0;
        Double sum2 = 0.0;
        Double sum3 = 0.0;
        Double sum4 = 0.0;
        Double sum5 = 0.0;
        Double sum6 = 0.0;
        Double sum7 = 0.0;
        Double sum8 = 0.0;
        Double sum9 = 0.0;
        Double sum10 = 0.0;
        Double sum11 = 0.0;
        Double sum12 = 0.0;
        Double sum13 = 0.0;
        Double sum14 = 0.0;
        Double sum15 = 0.0;
        Double sum16 = 0.0;

        for (Outsourcing outsourcing : outsourcingList){
            row2 = hoja2.createRow(aux2);

            if (outsourcing.getDwEnterprises() != null){
                if (outsourcing.getDwEnterprises().getArea() != null){
                    row2.createCell(0).setCellValue(outsourcing.getDwEnterprises().getArea().getAreaName());
                }
            }
            if(outsourcing.getEmployee() != null){
                row2.createCell(1).setCellValue(outsourcing.getEmployee().getIdEmployee());
            }
            if(outsourcing.getEmployee() != null){
                row2.createCell(2).setCellValue(outsourcing.getEmployee().getFullName());
            }
            if(outsourcing.getSalary() != null){
                row2.createCell(3).setCellValue(outsourcing.getSalary().doubleValue());
                sum1 += outsourcing.getSalary().doubleValue();
            }
            if(outsourcing.getSubsidy() != null){
                row2.createCell(4).setCellValue(outsourcing.getSubsidy().doubleValue());
                sum2 += outsourcing.getSubsidy().doubleValue();
            }
            if (outsourcing.getImssEmployee() != null){
                row2.createCell(5).setCellValue(outsourcing.getImssEmployee().doubleValue());
                sum3 += outsourcing.getImssEmployee().doubleValue();
            }
            if (outsourcing.getIsr() != null){
                row2.createCell(6).setCellValue(outsourcing.getIsr().doubleValue());
                sum4 += outsourcing.getIsr().doubleValue();
            }
            if (outsourcing.getAdjustment() != null){
                row2.createCell(7).setCellValue(outsourcing.getAdjustment().doubleValue());
                sum5 += outsourcing.getAdjustment().doubleValue();
            }
            if (outsourcing.getTotalDeductions() != null){
                row2.createCell(8).setCellValue(outsourcing.getTotalDeductions().doubleValue());
                sum6 += outsourcing.getTotalDeductions().doubleValue();
            }
            if (outsourcing.getNetAssetTax() != null){
                row2.createCell(9).setCellValue(outsourcing.getNetAssetTax().doubleValue());
                sum7 += outsourcing.getNetAssetTax().doubleValue();
            }
            if (outsourcing.getImss() != null){
                row2.createCell(10).setCellValue(outsourcing.getImss().doubleValue());
                sum8 += outsourcing.getImss().doubleValue();
            }
            if (outsourcing.getRcv() != null){
                row2.createCell(11).setCellValue(outsourcing.getRcv().doubleValue());
                sum9 += outsourcing.getRcv().doubleValue();
            }
            if (outsourcing.getEnterpriseInfonavit() != null){
                row2.createCell(12).setCellValue(outsourcing.getEnterpriseInfonavit().doubleValue());
                sum10 += outsourcing.getEnterpriseInfonavit().doubleValue();
            }
            if (outsourcing.getPayrollTax() != null){
                row2.createCell(13).setCellValue(outsourcing.getPayrollTax().doubleValue());
                sum11 += outsourcing.getPayrollTax().doubleValue();
            }
            if (outsourcing.getTotalSocialSecurity() != null){
                row2.createCell(14).setCellValue(outsourcing.getTotalSocialSecurity().doubleValue());
                sum12 += outsourcing.getTotalSocialSecurity().doubleValue();
            }
            if (outsourcing.getCommission() != null){
                row2.createCell(15).setCellValue(outsourcing.getCommission().doubleValue());
                sum13 += outsourcing.getCommission().doubleValue();
            }
            if (outsourcing.getSubtotal() != null){
                row2.createCell(16).setCellValue(outsourcing.getSubtotal().doubleValue());
                sum14 += outsourcing.getSubtotal().doubleValue();
            }
            if (outsourcing.getIva() != null){
                row2.createCell(17).setCellValue(outsourcing.getIva().doubleValue());
                sum15 += outsourcing.getIva().doubleValue();
            }
            if (outsourcing.getTotal() != null){
                row2.createCell(18).setCellValue(outsourcing.getTotal().doubleValue());
                sum16 += outsourcing.getTotal().doubleValue();
            }

            aux2++;
        }

        row2 = hoja2.createRow(aux2+2);

        row2.createCell(2).setCellValue("TOTALES");
        row2.createCell(3).setCellValue(sum1);
        row2.createCell(4).setCellValue(sum2);
        row2.createCell(5).setCellValue(sum3);
        row2.createCell(6).setCellValue(sum4);
        row2.createCell(7).setCellValue(sum5);
        row2.createCell(8).setCellValue(sum6);
        row2.createCell(9).setCellValue(sum7);
        row2.createCell(10).setCellValue(sum8);
        row2.createCell(11).setCellValue(sum9);
        row2.createCell(12).setCellValue(sum10);
        row2.createCell(13).setCellValue(sum11);
        row2.createCell(14).setCellValue(sum12);
        row2.createCell(15).setCellValue(sum13);
        row2.createCell(16).setCellValue(sum14);
        row2.createCell(17).setCellValue(sum15);
        row2.createCell(18).setCellValue(sum16);

        Sheet hoja3 = wb.createSheet("RHMAS AMERMEDIA");

        //Se crea la fila que contiene la cabecera
        Row row3 = hoja3.createRow(0);

        row3.createCell(0).setCellValue("DEPARTAMENTO");
        row3.createCell(1).setCellValue("CODIGO");
        row3.createCell(2).setCellValue("NOMBRE");
        row3.createCell(3).setCellValue("SUELDO");
        row3.createCell(4).setCellValue("SUBSIDIO");
        row3.createCell(5).setCellValue("IMSS EMPLEADO");
        row3.createCell(6).setCellValue("ISR");
        row3.createCell(7).setCellValue("AJUSTE AL NETO");
        row3.createCell(8).setCellValue("TOTAL DEDUCCIONES");
        row3.createCell(9).setCellValue("NETO SUELDO FISCAL");
        row3.createCell(10).setCellValue("IMSS");
        row3.createCell(11).setCellValue("RCV");
        row3.createCell(12).setCellValue("INFONAVIT EMPRESA");
        row3.createCell(13).setCellValue("IMPUESTO SOBRE NÓMINA");
        row3.createCell(14).setCellValue("TOTAL PREVISION SOCIAL");
        row3.createCell(15).setCellValue("COMISIÓN");
        row3.createCell(16).setCellValue("SUBTOTAL");
        row3.createCell(17).setCellValue("IVA");
        row3.createCell(18).setCellValue("TOTAL");

        //Implementacion del estilo
        for (Cell celda : row3) {
            celda.setCellStyle(style);
        }

        List<DwEnterprises> dwEnterList = dwEnterprisesDao.findByDistributor(3);
        List<Outsourcing> outsourcings = outsourcingDao.findByDwEnterprise(dwEnterList,applicatioDateStart,applicationDateEnd);

        int aux3 = 1;

        Double sum17 = 0.0;
        Double sum18 = 0.0;
        Double sum19 = 0.0;
        Double sum20 = 0.0;
        Double sum21 = 0.0;
        Double sum22 = 0.0;
        Double sum23 = 0.0;
        Double sum24 = 0.0;
        Double sum25 = 0.0;
        Double sum26 = 0.0;
        Double sum27 = 0.0;
        Double sum28 = 0.0;
        Double sum29 = 0.0;
        Double sum30 = 0.0;
        Double sum31 = 0.0;
        Double sum32 = 0.0;

        for (Outsourcing outsourcing : outsourcings){
            row3 = hoja3.createRow(aux3);

            if (outsourcing.getDwEnterprises() != null){
                if (outsourcing.getDwEnterprises().getArea() != null){
                    row3.createCell(0).setCellValue(outsourcing.getDwEnterprises().getArea().getAreaName());
                }
            }
            if(outsourcing.getEmployee() != null){
                row3.createCell(1).setCellValue(outsourcing.getEmployee().getIdEmployee());
            }
            if(outsourcing.getEmployee() != null){
                row3.createCell(2).setCellValue(outsourcing.getEmployee().getFullName());
            }
            if(outsourcing.getSalary() != null){
                row3.createCell(3).setCellValue(outsourcing.getSalary().doubleValue());
                sum17 += outsourcing.getTotal().doubleValue();
            }
            if(outsourcing.getSubsidy() != null){
                row3.createCell(4).setCellValue(outsourcing.getSubsidy().doubleValue());
                sum18 += outsourcing.getTotal().doubleValue();
            }
            if (outsourcing.getImssEmployee() != null){
                row3.createCell(5).setCellValue(outsourcing.getImssEmployee().doubleValue());
                sum19 += outsourcing.getTotal().doubleValue();
            }
            if (outsourcing.getIsr() != null){
                row3.createCell(6).setCellValue(outsourcing.getIsr().doubleValue());
                sum20 += outsourcing.getTotal().doubleValue();
            }
            if (outsourcing.getAdjustment() != null){
                row3.createCell(7).setCellValue(outsourcing.getAdjustment().doubleValue());
                sum21 += outsourcing.getTotal().doubleValue();
            }
            if (outsourcing.getTotalDeductions() != null){
                row3.createCell(8).setCellValue(outsourcing.getTotalDeductions().doubleValue());
                sum22 += outsourcing.getTotal().doubleValue();
            }
            if (outsourcing.getNetAssetTax() != null){
                row3.createCell(9).setCellValue(outsourcing.getNetAssetTax().doubleValue());
                sum23 += outsourcing.getTotal().doubleValue();
            }
            if (outsourcing.getImss() != null){
                row3.createCell(10).setCellValue(outsourcing.getImss().doubleValue());
                sum24 += outsourcing.getTotal().doubleValue();
            }
            if (outsourcing.getRcv() != null){
                row3.createCell(11).setCellValue(outsourcing.getRcv().doubleValue());
                sum25 += outsourcing.getTotal().doubleValue();
            }
            if (outsourcing.getEnterpriseInfonavit() != null){
                row3.createCell(12).setCellValue(outsourcing.getEnterpriseInfonavit().doubleValue());
                sum26 += outsourcing.getTotal().doubleValue();
            }
            if (outsourcing.getPayrollTax() != null){
                row3.createCell(13).setCellValue(outsourcing.getPayrollTax().doubleValue());
                sum27 += outsourcing.getTotal().doubleValue();
            }
            if (outsourcing.getTotalSocialSecurity() != null){
                row3.createCell(14).setCellValue(outsourcing.getTotalSocialSecurity().doubleValue());
                sum28 += outsourcing.getTotal().doubleValue();
            }
            if (outsourcing.getCommission() != null){
                row3.createCell(15).setCellValue(outsourcing.getCommission().doubleValue());
                sum29 += outsourcing.getTotal().doubleValue();
            }
            if (outsourcing.getSubtotal() != null){
                row3.createCell(16).setCellValue(outsourcing.getSubtotal().doubleValue());
                sum30 += outsourcing.getTotal().doubleValue();
            }
            if (outsourcing.getIva() != null){
                row3.createCell(17).setCellValue(outsourcing.getIva().doubleValue());
                sum31 += outsourcing.getTotal().doubleValue();
            }
            if (outsourcing.getTotal() != null){
                row3.createCell(18).setCellValue(outsourcing.getTotal().doubleValue());
                sum32 += outsourcing.getTotal().doubleValue();
            }

            aux3++;
        }

        row3 = hoja3.createRow(aux3+2);

        row3.createCell(2).setCellValue("TOTALES");
        row3.createCell(3).setCellValue(sum17);
        row3.createCell(4).setCellValue(sum18);
        row3.createCell(5).setCellValue(sum19);
        row3.createCell(6).setCellValue(sum20);
        row3.createCell(7).setCellValue(sum21);
        row3.createCell(8).setCellValue(sum22);
        row3.createCell(9).setCellValue(sum23);
        row3.createCell(10).setCellValue(sum24);
        row3.createCell(11).setCellValue(sum25);
        row3.createCell(12).setCellValue(sum26);
        row3.createCell(13).setCellValue(sum27);
        row3.createCell(14).setCellValue(sum28);
        row3.createCell(15).setCellValue(sum29);
        row3.createCell(16).setCellValue(sum30);
        row3.createCell(17).setCellValue(sum31);
        row3.createCell(18).setCellValue(sum32);

        Sheet hoja4 = wb.createSheet("AMER NEC");

        //Se crea la fila que contiene la cabecera
        Row row4 = hoja4.createRow(0);

        row4.createCell(0).setCellValue("N");
        row4.createCell(1).setCellValue("NOMBRE DEL EMPLEADO");
        row4.createCell(2).setCellValue("BANCO");
        row4.createCell(3).setCellValue("N. DE CUENTA");
        row4.createCell(4).setCellValue("CLABE");
        row4.createCell(5).setCellValue("SUCURSAL");
        row4.createCell(6).setCellValue("AREA");
        row4.createCell(7).setCellValue("RFC");
        row4.createCell(8).setCellValue("CURP");
        row4.createCell(9).setCellValue("MONTO A PAGAR");
        row4.createCell(10).setCellValue("11.50%");
        row4.createCell(11).setCellValue("TOTAL A FACTURAR");

        //Implementacion del estilo
        for (Cell celda : row4) {
            celda.setCellStyle(style);
        }

        int aux4 = 1;

        List <Payroll> amerNec = payrollDao.findByDistributor(2);

        Double sum33 = 0.0;
        Double sum34 = 0.0;
        Double sum35 = 0.0;

        for(Payroll payroll : amerNec){
            row4 = hoja4.createRow(aux4);

            if (payroll.getNumeroDeEmpleado() != null){
                row4.createCell(0).setCellValue(payroll.getNumeroDeEmpleado());
            }
            if (payroll.getNombre() != null){
                row4.createCell(1).setCellValue(payroll.getNombre());
            }
            if (payroll.getBanco() != null){
                row4.createCell(2).setCellValue(payroll.getBanco());
            }
            if (payroll.getNumeroDeCuenta() != null){
                row4.createCell(3).setCellValue(payroll.getNumeroDeCuenta());
            }
            if (payroll.getCuentaClabe() != null){
                row4.createCell(4).setCellValue(payroll.getCuentaClabe());
            }
            if (payroll.getSucursal() != null){
                row4.createCell(5).setCellValue(payroll.getSucursal());
            }
            if (payroll.getArea() != null){
                row4.createCell(6).setCellValue(payroll.getArea());
            }
            if (payroll.getRfc() != null){
                row4.createCell(7).setCellValue(payroll.getRfc());
            }
            if (payroll.getCurp() != null){
                row4.createCell(8).setCellValue(payroll.getCurp());
            }
            if (payroll.getPago() != null){
                row4.createCell(9).setCellValue(payroll.getPago().doubleValue());
                sum33 += payroll.getPago().doubleValue();
            }
            if (payroll.getComisionNec() != null){
                row4.createCell(10).setCellValue(payroll.getComisionNec().doubleValue());
                sum34 += payroll.getComisionNec().doubleValue();
            }
            if (payroll.getTotalFacturar() != null){
                row4.createCell(11).setCellValue(payroll.getTotalFacturar().doubleValue());
                sum35 += payroll.getTotalFacturar().doubleValue();
            }

            aux4 ++;

        }

        row4 = hoja4.createRow(aux4+2);

        row4.createCell(8).setCellValue("TOTALES");
        row4.createCell(9).setCellValue(sum33);
        row4.createCell(10).setCellValue(sum34);
        row4.createCell(11).setCellValue(sum35);

        Sheet hoja5 = wb.createSheet("AMERMEDIA NEC");

        //Se crea la fila que contiene la cabecera
        Row row5 = hoja5.createRow(0);

        row5.createCell(0).setCellValue("N");
        row5.createCell(1).setCellValue("NOMBRE DEL EMPLEADO");
        row5.createCell(2).setCellValue("BANCO");
        row5.createCell(3).setCellValue("N. DE CUENTA");
        row5.createCell(4).setCellValue("CLABE");
        row5.createCell(5).setCellValue("SUCURSAL");
        row5.createCell(6).setCellValue("AREA");
        row5.createCell(7).setCellValue("RFC");
        row5.createCell(8).setCellValue("CURP");
        row5.createCell(9).setCellValue("MONTO A PAGAR");
        row5.createCell(10).setCellValue("11.50%");
        row5.createCell(11).setCellValue("TOTAL A FACTURAR");

        //Implementacion del estilo
        for (Cell celda : row5) {
            celda.setCellStyle(style);
        }

        int aux5 = 1;

        List <Payroll> amermediaNec = payrollDao.findByDistributor(3);

        Double sum36 = 0.0;
        Double sum37 = 0.0;
        Double sum38 = 0.0;

        for(Payroll payroll : amermediaNec){
            row5 = hoja5.createRow(aux5);

            if (payroll.getNumeroDeEmpleado() != null){
                row5.createCell(0).setCellValue(payroll.getNumeroDeEmpleado());
            }
            if (payroll.getNombre() != null){
                row5.createCell(1).setCellValue(payroll.getNombre());
            }
            if (payroll.getBanco() != null){
                row5.createCell(2).setCellValue(payroll.getBanco());
            }
            if (payroll.getNumeroDeCuenta() != null){
                row5.createCell(3).setCellValue(payroll.getNumeroDeCuenta());
            }
            if (payroll.getCuentaClabe() != null){
                row5.createCell(4).setCellValue(payroll.getCuentaClabe());
            }
            if (payroll.getSucursal() != null){
                row5.createCell(5).setCellValue(payroll.getSucursal());
            }
            if (payroll.getArea() != null){
                row5.createCell(6).setCellValue(payroll.getArea());
            }
            if (payroll.getRfc() != null){
                row5.createCell(7).setCellValue(payroll.getRfc());
            }
            if (payroll.getCurp() != null){
                row5.createCell(8).setCellValue(payroll.getCurp());
            }
            if (payroll.getPago() != null){
                row5.createCell(9).setCellValue(payroll.getPago().doubleValue());
                sum36 += payroll.getPago().doubleValue();
            }
            if (payroll.getComisionNec() != null){
                row5.createCell(10).setCellValue(payroll.getComisionNec().doubleValue());
                sum37 += payroll.getComisionNec().doubleValue();
            }
            if (payroll.getTotalFacturar() != null){
                row5.createCell(11).setCellValue(payroll.getTotalFacturar().doubleValue());
                sum38 += payroll.getTotalFacturar().doubleValue();
            }

            aux5 ++;

        }

        row5 = hoja5.createRow(aux5+2);

        row5.createCell(8).setCellValue("TOTALES");
        row5.createCell(9).setCellValue(sum36);
        row5.createCell(10).setCellValue(sum37);
        row5.createCell(11).setCellValue(sum38);

        Sheet hoja6 = wb.createSheet("TOTALES");

        //Se crea la fila que contiene la cabecera
        Row row6 = hoja6.createRow(0);


        //Implementacion del estilo
        for (Cell celda : row6) {
            celda.setCellStyle(style);
        }

        BigDecimal amerNecSum = (BigDecimal) payrollDao.sumDistributorNec(2);
        BigDecimal amermediaNecSum = (BigDecimal) payrollDao.sumDistributorNec(3);
        BigDecimal rhmasAmer  = (BigDecimal) outsourcingDao.findSumRhmasByDwEnterprise(dwEnterprisesList,applicatioDateStart,applicationDateEnd);
        BigDecimal rhmasAmermedia  = (BigDecimal) outsourcingDao.findSumRhmasByDwEnterprise(dwEnterList,applicatioDateStart,applicationDateEnd);

        Double totalDeTotales = 0.00;

        if(amerNecSum != null){
            totalDeTotales+=amerNecSum.doubleValue();
            Row rowAmerNec = hoja6.createRow(3);

            rowAmerNec.createCell(1).setCellValue("AMER NEC");
            rowAmerNec.createCell(2).setCellValue(amerNecSum.doubleValue());
        }
        if(amermediaNecSum != null){
            totalDeTotales+=amermediaNecSum.doubleValue();
            Row rowAmermediaNec = hoja6.createRow(4);

            rowAmermediaNec.createCell(1).setCellValue("AMERMEDIA NEC");
            rowAmermediaNec.createCell(2).setCellValue(amermediaNecSum.doubleValue());
        }
        if (rhmasAmer != null){
            totalDeTotales+=rhmasAmer.doubleValue();
            Row rowRhmasAmer = hoja6.createRow(5);

            rowRhmasAmer.createCell(1).setCellValue("RHMAS AMER");
            rowRhmasAmer.createCell(2).setCellValue(rhmasAmer.doubleValue());
        }
        if (rhmasAmermedia != null){
            totalDeTotales+=rhmasAmermedia.doubleValue();
            Row rowRhmasAmermedia = hoja6.createRow(6);

            rowRhmasAmermedia.createCell(1).setCellValue("RHMAS AMERMEDIA");
            rowRhmasAmermedia.createCell(2).setCellValue(rhmasAmermedia.doubleValue());
        }


        Row rowTotal = hoja6.createRow(8);

        rowTotal.createCell(1).setCellValue("TOTAL");
        rowTotal.createCell(2).setCellValue(totalDeTotales);


        Sheet hoja7 = wb.createSheet("CUADRO ADMON");

        //Se crea la fila que contiene la cabecera
        Row row7 = hoja7.createRow(0);


        row7.createCell(0).setCellValue("CONCEPTO");
        row7.createCell(1).setCellValue("VENTAS");
        row7.createCell(2).setCellValue("% CONTRA VENTA");

        //Implementacion del estilo
        for (Cell celda : row7) {
            celda.setCellStyle(style);
        }

        int aux7 = 1;

        List<Object[]> results = queryResult;

        Double sum100 = 0.00;
        Double sum200 = 0.00;

        for(Object[] admonReport : results){
            row7 = hoja7.createRow(aux7);

            if (admonReport[0] != null){
                row7.createCell(0).setCellValue(String.valueOf(admonReport[0]));
            }
            if (admonReport[1] != null){
                row7.createCell(1).setCellValue(Double.parseDouble(String.valueOf(admonReport[1])));
                sum100 += Double.parseDouble(String.valueOf(admonReport[1]));
            }
            if (admonReport[2] != null){
                String cadena = String.valueOf(admonReport[2]);
                if (!cadena.isEmpty()){
                    row7.createCell(2).setCellValue(Double.parseDouble(cadena));
                    sum200 += Double.parseDouble(cadena);
                }
            }


            aux7 ++;

        }

        BigDecimal totalAmountSap = (BigDecimal) sapSaleDao.sumTotalAmuntBeteween(applicatioDateStart,applicationDateEnd);

        row7 = hoja7.createRow(aux7+1);
        row7.createCell(0).setCellValue("Total promotor");
        row7.createCell(1).setCellValue(sum100-totalAmountSap.doubleValue());
        row7.createCell(2).setCellValue(sum200);

        hoja1.autoSizeColumn(0);
        hoja2.autoSizeColumn(0);
        hoja3.autoSizeColumn(0);
        hoja4.autoSizeColumn(0);
        hoja5.autoSizeColumn(0);
        hoja6.autoSizeColumn(0);
        hoja7.autoSizeColumn(0);

        wb.write(fileOutputStream);
        wb.write(outputStream);
    }




    @Override
    public void monthlyPayrollReport(OutputStream outputStream, LocalDateTime applicatioDateStart, LocalDateTime applicationDateEnd, FileOutputStream fileOutputStream, List queryResult) throws IOException {
        Workbook wb = new XSSFWorkbook();
        //Definicion del estilo de la cabecera
        Font font = wb.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 10);
        font.setFontName("Arial");
        font.setColor(IndexedColors.WHITE.getIndex());
        CellStyle style = wb.createCellStyle();
        style.setFont(font);
        style.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        style.setAlignment(CellStyle.ALIGN_CENTER);

        CellStyle cellDateStyle = wb.createCellStyle();
        CreationHelper createHelper = wb.getCreationHelper();
        cellDateStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/MM/yyyy"));

        Sheet hoja1 = wb.createSheet("MATRIZ");

        Row row1 = hoja1.createRow(0);

        row1.createCell(0).setCellValue("NÚMERO DE EMPLEADO");
        row1.createCell(1).setCellValue("EMPRESA");
        row1.createCell(2).setCellValue("REGION");
        row1.createCell(3).setCellValue("ZONA");
        row1.createCell(4).setCellValue("NOMBRE");
        row1.createCell(5).setCellValue("CLAVE SAP");
        row1.createCell(6).setCellValue("PUESTO");
        row1.createCell(7).setCellValue("BANCO");
        row1.createCell(8).setCellValue("CUENTA");
        row1.createCell(9).setCellValue("CLABE");
        row1.createCell(10).setCellValue("SUCURSAL");
        row1.createCell(11).setCellValue("RFC");
        row1.createCell(12).setCellValue("CURP");
        row1.createCell(13).setCellValue("FECHA DE INGRESO");
        row1.createCell(14).setCellValue("APOYO 375");
        row1.createCell(15).setCellValue("MONTO_PROMOTOR");
        row1.createCell(16).setCellValue("COMISIÓN SEMANAL PROMOTOR");
        row1.createCell(17).setCellValue("BONO CUMPLIMIENTO");
        row1.createCell(18).setCellValue("AJUSTES");
        row1.createCell(19).setCellValue("APOYO PASAJES");
        row1.createCell(20).setCellValue("MONTO MENSUAL");
        row1.createCell(21).setCellValue("COMISIÓN MENSUAL");
        row1.createCell(22).setCellValue("DESCUENTOS");
        row1.createCell(23).setCellValue("IMMS RHMAS PAGO");
        row1.createCell(24).setCellValue("RH MAS TOTAL A FACTURAR");
        row1.createCell(25).setCellValue("PERCEPCIONES");
        row1.createCell(26).setCellValue("DEDUCCIONES");
        row1.createCell(27).setCellValue("DEPOSITO ASIMILABLES");

        //Implementacion del estilo
        for (Cell celda : row1) {
            celda.setCellStyle(style);
        }

        int aux1 = 1;

        List<Payroll> payrollList = payrollDao.findAll();

        for (Payroll payroll : payrollList){
            row1 = hoja1.createRow(aux1);

            if (payroll.getNumeroDeEmpleado() != null){
                row1.createCell(0).setCellValue(payroll.getNumeroDeEmpleado());
            }
            if (payroll.getDistributorName() != null){
                row1.createCell(1).setCellValue(payroll.getDistributorName());
            }
            if (payroll.getRegionName() != null){
                row1.createCell(2).setCellValue(payroll.getRegionName());
            }
            if (payroll.getZonaName() != null){
                row1.createCell(3).setCellValue(payroll.getZonaName());
            }
            if (payroll.getNombre() != null){
                row1.createCell(4).setCellValue(payroll.getNombre());
            }
            if (payroll.getClaveSap() != null){
                row1.createCell(5).setCellValue(payroll.getClaveSap());
            }
            if (payroll.getPuesto() != null){
                row1.createCell(6).setCellValue(payroll.getPuesto());
            }
            if (payroll.getBanco() != null){
                row1.createCell(7).setCellValue(payroll.getBanco());
            }
            if (payroll.getNumeroDeCuenta() != null){
                row1.createCell(8).setCellValue(payroll.getNumeroDeCuenta());
            }
            if (payroll.getCuentaClabe() != null){
                row1.createCell(9).setCellValue(payroll.getCuentaClabe());
            }
            if (payroll.getSucursal() != null){
                row1.createCell(10).setCellValue(payroll.getSucursal());
            }
            if (payroll.getRfc() != null){
                row1.createCell(11).setCellValue(payroll.getRfc());
            }
            if (payroll.getCurp() != null){
                row1.createCell(12).setCellValue(payroll.getCurp());
            }
            if(payroll.getFechaIngreso() != null){
                Date joinDate = Date.from(payroll.getFechaIngreso().atStartOfDay(ZoneId.systemDefault()).toInstant());
                row1.createCell(13);
                row1.getCell(13).setCellValue(joinDate);
                row1.getCell(13).setCellStyle(cellDateStyle);
            }
            if (payroll.getComissionPromotor() != null){
                row1.createCell(14).setCellValue(payroll.getApoyo375().doubleValue());
            }
            if (payroll.getMontoPromotor() != null){
                row1.createCell(15).setCellValue(payroll.getMontoPromotor().doubleValue());
            }
            if (payroll.getComissionPromotor() != null){
                row1.createCell(16).setCellValue(payroll.getComissionPromotor().doubleValue());
            }
            if (payroll.getBono() != null){
                row1.createCell(17).setCellValue(payroll.getBono().doubleValue());
            }
            if (payroll.getAjuste() != null){
                row1.createCell(18).setCellValue(payroll.getAjuste().doubleValue());
            }
            if (payroll.getApoyoPasajes() != null){
                row1.createCell(19).setCellValue(payroll.getApoyoPasajes().doubleValue());
            }
            if (payroll.getMontoMensual() != null){
                row1.createCell(20).setCellValue(payroll.getMontoMensual().doubleValue());
            }
            if (payroll.getComisionMensual() != null){
                row1.createCell(21).setCellValue(payroll.getComisionMensual().doubleValue());
            }
            if (payroll.getDescuento() != null){
                row1.createCell(22).setCellValue(payroll.getDescuento().doubleValue());
            }
            if (payroll.getRhmasPago() != null){
                row1.createCell(23).setCellValue(payroll.getRhmasPago().doubleValue());
            }
            if (payroll.getRhmasTotalFacturar() != null){
                row1.createCell(24).setCellValue(payroll.getRhmasTotalFacturar().doubleValue());
            }
            if (payroll.getPercepcion() != null){
                row1.createCell(25).setCellValue(payroll.getPercepcion().doubleValue());
            }
            if (payroll.getDeduccion() != null){
                row1.createCell(26).setCellValue(payroll.getDeduccion().doubleValue());
            }
            if (payroll.getPago() != null){
                row1.createCell(27).setCellValue(payroll.getPago().doubleValue());
            }

            aux1++;
        }

        Sheet hoja2 = wb.createSheet("RHMAS AMER");

        //Se crea la fila que contiene la cabecera
        Row row2 = hoja2.createRow(0);

        row2.createCell(0).setCellValue("DEPARTAMENTO");
        row2.createCell(1).setCellValue("CODIGO");
        row2.createCell(2).setCellValue("NOMBRE");
        row2.createCell(3).setCellValue("SUELDO");
        row2.createCell(4).setCellValue("SUBSIDIO");
        row2.createCell(5).setCellValue("IMSS EMPLEADO");
        row2.createCell(6).setCellValue("ISR");
        row2.createCell(7).setCellValue("AJUSTE AL NETO");
        row2.createCell(8).setCellValue("TOTAL DEDUCCIONES");
        row2.createCell(9).setCellValue("NETO SUELDO FISCAL");
        row2.createCell(10).setCellValue("IMSS");
        row2.createCell(11).setCellValue("RCV");
        row2.createCell(12).setCellValue("INFONAVIT EMPRESA");
        row2.createCell(13).setCellValue("IMPUESTO SOBRE NÓMINA");
        row2.createCell(14).setCellValue("TOTAL PREVISION SOCIAL");
        row2.createCell(15).setCellValue("COMISIÓN");
        row2.createCell(16).setCellValue("SUBTOTAL");
        row2.createCell(17).setCellValue("IVA");
        row2.createCell(18).setCellValue("TOTAL");

        //Implementacion del estilo
        for (Cell celda : row2) {
            celda.setCellStyle(style);
        }

        List<DwEnterprises> dwEnterprisesList = dwEnterprisesDao.findByDistributor(2);
        List<Outsourcing> outsourcingList = outsourcingDao.findByDwEnterprise(dwEnterprisesList,applicatioDateStart,applicationDateEnd);

        int aux2 = 1;

        Double sum1 = 0.0;
        Double sum2 = 0.0;
        Double sum3 = 0.0;
        Double sum4 = 0.0;
        Double sum5 = 0.0;
        Double sum6 = 0.0;
        Double sum7 = 0.0;
        Double sum8 = 0.0;
        Double sum9 = 0.0;
        Double sum10 = 0.0;
        Double sum11 = 0.0;
        Double sum12 = 0.0;
        Double sum13 = 0.0;
        Double sum14 = 0.0;
        Double sum15 = 0.0;
        Double sum16 = 0.0;

        for (Outsourcing outsourcing : outsourcingList){
            row2 = hoja2.createRow(aux2);

            if (outsourcing.getDwEnterprises() != null){
                if (outsourcing.getDwEnterprises().getArea() != null){
                    row2.createCell(0).setCellValue(outsourcing.getDwEnterprises().getArea().getAreaName());
                }
            }
            if(outsourcing.getEmployee() != null){
                row2.createCell(1).setCellValue(outsourcing.getEmployee().getIdEmployee());
            }
            if(outsourcing.getEmployee() != null){
                row2.createCell(2).setCellValue(outsourcing.getEmployee().getFullName());
            }
            if(outsourcing.getSalary() != null){
                row2.createCell(3).setCellValue(outsourcing.getSalary().doubleValue());
                sum1 += outsourcing.getSalary().doubleValue();
            }
            if(outsourcing.getSubsidy() != null){
                row2.createCell(4).setCellValue(outsourcing.getSubsidy().doubleValue());
                sum2 += outsourcing.getSubsidy().doubleValue();
            }
            if (outsourcing.getImssEmployee() != null){
                row2.createCell(5).setCellValue(outsourcing.getImssEmployee().doubleValue());
                sum3 += outsourcing.getImssEmployee().doubleValue();
            }
            if (outsourcing.getIsr() != null){
                row2.createCell(6).setCellValue(outsourcing.getIsr().doubleValue());
                sum4 += outsourcing.getIsr().doubleValue();
            }
            if (outsourcing.getAdjustment() != null){
                row2.createCell(7).setCellValue(outsourcing.getAdjustment().doubleValue());
                sum5 += outsourcing.getAdjustment().doubleValue();
            }
            if (outsourcing.getTotalDeductions() != null){
                row2.createCell(8).setCellValue(outsourcing.getTotalDeductions().doubleValue());
                sum6 += outsourcing.getTotalDeductions().doubleValue();
            }
            if (outsourcing.getNetAssetTax() != null){
                row2.createCell(9).setCellValue(outsourcing.getNetAssetTax().doubleValue());
                sum7 += outsourcing.getNetAssetTax().doubleValue();
            }
            if (outsourcing.getImss() != null){
                row2.createCell(10).setCellValue(outsourcing.getImss().doubleValue());
                sum8 += outsourcing.getImss().doubleValue();
            }
            if (outsourcing.getRcv() != null){
                row2.createCell(11).setCellValue(outsourcing.getRcv().doubleValue());
                sum9 += outsourcing.getRcv().doubleValue();
            }
            if (outsourcing.getEnterpriseInfonavit() != null){
                row2.createCell(12).setCellValue(outsourcing.getEnterpriseInfonavit().doubleValue());
                sum10 += outsourcing.getEnterpriseInfonavit().doubleValue();
            }
            if (outsourcing.getPayrollTax() != null){
                row2.createCell(13).setCellValue(outsourcing.getPayrollTax().doubleValue());
                sum11 += outsourcing.getPayrollTax().doubleValue();
            }
            if (outsourcing.getTotalSocialSecurity() != null){
                row2.createCell(14).setCellValue(outsourcing.getTotalSocialSecurity().doubleValue());
                sum12 += outsourcing.getTotalSocialSecurity().doubleValue();
            }
            if (outsourcing.getCommission() != null){
                row2.createCell(15).setCellValue(outsourcing.getCommission().doubleValue());
                sum13 += outsourcing.getCommission().doubleValue();
            }
            if (outsourcing.getSubtotal() != null){
                row2.createCell(16).setCellValue(outsourcing.getSubtotal().doubleValue());
                sum14 += outsourcing.getSubtotal().doubleValue();
            }
            if (outsourcing.getIva() != null){
                row2.createCell(17).setCellValue(outsourcing.getIva().doubleValue());
                sum15 += outsourcing.getIva().doubleValue();
            }
            if (outsourcing.getTotal() != null){
                row2.createCell(18).setCellValue(outsourcing.getTotal().doubleValue());
                sum16 += outsourcing.getTotal().doubleValue();
            }

            aux2++;
        }

        row2 = hoja2.createRow(aux2+2);

        row2.createCell(2).setCellValue("TOTALES");
        row2.createCell(3).setCellValue(sum1);
        row2.createCell(4).setCellValue(sum2);
        row2.createCell(5).setCellValue(sum3);
        row2.createCell(6).setCellValue(sum4);
        row2.createCell(7).setCellValue(sum5);
        row2.createCell(8).setCellValue(sum6);
        row2.createCell(9).setCellValue(sum7);
        row2.createCell(10).setCellValue(sum8);
        row2.createCell(11).setCellValue(sum9);
        row2.createCell(12).setCellValue(sum10);
        row2.createCell(13).setCellValue(sum11);
        row2.createCell(14).setCellValue(sum12);
        row2.createCell(15).setCellValue(sum13);
        row2.createCell(16).setCellValue(sum14);
        row2.createCell(17).setCellValue(sum15);
        row2.createCell(18).setCellValue(sum16);

        Sheet hoja3 = wb.createSheet("RHMAS AMERMEDIA");

        //Se crea la fila que contiene la cabecera
        Row row3 = hoja3.createRow(0);

        row3.createCell(0).setCellValue("DEPARTAMENTO");
        row3.createCell(1).setCellValue("CODIGO");
        row3.createCell(2).setCellValue("NOMBRE");
        row3.createCell(3).setCellValue("SUELDO");
        row3.createCell(4).setCellValue("SUBSIDIO");
        row3.createCell(5).setCellValue("IMSS EMPLEADO");
        row3.createCell(6).setCellValue("ISR");
        row3.createCell(7).setCellValue("AJUSTE AL NETO");
        row3.createCell(8).setCellValue("TOTAL DEDUCCIONES");
        row3.createCell(9).setCellValue("NETO SUELDO FISCAL");
        row3.createCell(10).setCellValue("IMSS");
        row3.createCell(11).setCellValue("RCV");
        row3.createCell(12).setCellValue("INFONAVIT EMPRESA");
        row3.createCell(13).setCellValue("IMPUESTO SOBRE NÓMINA");
        row3.createCell(14).setCellValue("TOTAL PREVISION SOCIAL");
        row3.createCell(15).setCellValue("COMISIÓN");
        row3.createCell(16).setCellValue("SUBTOTAL");
        row3.createCell(17).setCellValue("IVA");
        row3.createCell(18).setCellValue("TOTAL");

        //Implementacion del estilo
        for (Cell celda : row3) {
            celda.setCellStyle(style);
        }

        List<DwEnterprises> dwEnterList = dwEnterprisesDao.findByDistributor(3);
        List<Outsourcing> outsourcings = outsourcingDao.findByDwEnterprise(dwEnterList,applicatioDateStart,applicationDateEnd);

        int aux3 = 1;

        Double sum17 = 0.0;
        Double sum18 = 0.0;
        Double sum19 = 0.0;
        Double sum20 = 0.0;
        Double sum21 = 0.0;
        Double sum22 = 0.0;
        Double sum23 = 0.0;
        Double sum24 = 0.0;
        Double sum25 = 0.0;
        Double sum26 = 0.0;
        Double sum27 = 0.0;
        Double sum28 = 0.0;
        Double sum29 = 0.0;
        Double sum30 = 0.0;
        Double sum31 = 0.0;
        Double sum32 = 0.0;

        for (Outsourcing outsourcing : outsourcings){
            row3 = hoja3.createRow(aux3);

            if (outsourcing.getDwEnterprises() != null){
                if (outsourcing.getDwEnterprises().getArea() != null){
                    row3.createCell(0).setCellValue(outsourcing.getDwEnterprises().getArea().getAreaName());
                }
            }
            if(outsourcing.getEmployee() != null){
                row3.createCell(1).setCellValue(outsourcing.getEmployee().getIdEmployee());
            }
            if(outsourcing.getEmployee() != null){
                row3.createCell(2).setCellValue(outsourcing.getEmployee().getFullName());
            }
            if(outsourcing.getSalary() != null){
                row3.createCell(3).setCellValue(outsourcing.getSalary().doubleValue());
                sum17 += outsourcing.getTotal().doubleValue();
            }
            if(outsourcing.getSubsidy() != null){
                row3.createCell(4).setCellValue(outsourcing.getSubsidy().doubleValue());
                sum18 += outsourcing.getTotal().doubleValue();
            }
            if (outsourcing.getImssEmployee() != null){
                row3.createCell(5).setCellValue(outsourcing.getImssEmployee().doubleValue());
                sum19 += outsourcing.getTotal().doubleValue();
            }
            if (outsourcing.getIsr() != null){
                row3.createCell(6).setCellValue(outsourcing.getIsr().doubleValue());
                sum20 += outsourcing.getTotal().doubleValue();
            }
            if (outsourcing.getAdjustment() != null){
                row3.createCell(7).setCellValue(outsourcing.getAdjustment().doubleValue());
                sum21 += outsourcing.getTotal().doubleValue();
            }
            if (outsourcing.getTotalDeductions() != null){
                row3.createCell(8).setCellValue(outsourcing.getTotalDeductions().doubleValue());
                sum22 += outsourcing.getTotal().doubleValue();
            }
            if (outsourcing.getNetAssetTax() != null){
                row3.createCell(9).setCellValue(outsourcing.getNetAssetTax().doubleValue());
                sum23 += outsourcing.getTotal().doubleValue();
            }
            if (outsourcing.getImss() != null){
                row3.createCell(10).setCellValue(outsourcing.getImss().doubleValue());
                sum24 += outsourcing.getTotal().doubleValue();
            }
            if (outsourcing.getRcv() != null){
                row3.createCell(11).setCellValue(outsourcing.getRcv().doubleValue());
                sum25 += outsourcing.getTotal().doubleValue();
            }
            if (outsourcing.getEnterpriseInfonavit() != null){
                row3.createCell(12).setCellValue(outsourcing.getEnterpriseInfonavit().doubleValue());
                sum26 += outsourcing.getTotal().doubleValue();
            }
            if (outsourcing.getPayrollTax() != null){
                row3.createCell(13).setCellValue(outsourcing.getPayrollTax().doubleValue());
                sum27 += outsourcing.getTotal().doubleValue();
            }
            if (outsourcing.getTotalSocialSecurity() != null){
                row3.createCell(14).setCellValue(outsourcing.getTotalSocialSecurity().doubleValue());
                sum28 += outsourcing.getTotal().doubleValue();
            }
            if (outsourcing.getCommission() != null){
                row3.createCell(15).setCellValue(outsourcing.getCommission().doubleValue());
                sum29 += outsourcing.getTotal().doubleValue();
            }
            if (outsourcing.getSubtotal() != null){
                row3.createCell(16).setCellValue(outsourcing.getSubtotal().doubleValue());
                sum30 += outsourcing.getTotal().doubleValue();
            }
            if (outsourcing.getIva() != null){
                row3.createCell(17).setCellValue(outsourcing.getIva().doubleValue());
                sum31 += outsourcing.getTotal().doubleValue();
            }
            if (outsourcing.getTotal() != null){
                row3.createCell(18).setCellValue(outsourcing.getTotal().doubleValue());
                sum32 += outsourcing.getTotal().doubleValue();
            }

            aux3++;
        }

        row3 = hoja3.createRow(aux3+2);

        row3.createCell(2).setCellValue("TOTALES");
        row3.createCell(3).setCellValue(sum17);
        row3.createCell(4).setCellValue(sum18);
        row3.createCell(5).setCellValue(sum19);
        row3.createCell(6).setCellValue(sum20);
        row3.createCell(7).setCellValue(sum21);
        row3.createCell(8).setCellValue(sum22);
        row3.createCell(9).setCellValue(sum23);
        row3.createCell(10).setCellValue(sum24);
        row3.createCell(11).setCellValue(sum25);
        row3.createCell(12).setCellValue(sum26);
        row3.createCell(13).setCellValue(sum27);
        row3.createCell(14).setCellValue(sum28);
        row3.createCell(15).setCellValue(sum29);
        row3.createCell(16).setCellValue(sum30);
        row3.createCell(17).setCellValue(sum31);
        row3.createCell(18).setCellValue(sum32);

        Sheet hoja4 = wb.createSheet("AMER NEC");

        //Se crea la fila que contiene la cabecera
        Row row4 = hoja4.createRow(0);

        row4.createCell(0).setCellValue("N");
        row4.createCell(1).setCellValue("NOMBRE DEL EMPLEADO");
        row4.createCell(2).setCellValue("BANCO");
        row4.createCell(3).setCellValue("N. DE CUENTA");
        row4.createCell(4).setCellValue("CLABE");
        row4.createCell(5).setCellValue("SUCURSAL");
        row4.createCell(6).setCellValue("AREA");
        row4.createCell(7).setCellValue("RFC");
        row4.createCell(8).setCellValue("CURP");
        row4.createCell(9).setCellValue("MONTO A PAGAR");
        row4.createCell(10).setCellValue("11.50%");
        row4.createCell(11).setCellValue("TOTAL A FACTURAR");

        //Implementacion del estilo
        for (Cell celda : row4) {
            celda.setCellStyle(style);
        }

        int aux4 = 1;

        List <Payroll> amerNec = payrollDao.findByDistributor(2);

        Double sum33 = 0.0;
        Double sum34 = 0.0;
        Double sum35 = 0.0;

        for(Payroll payroll : amerNec){
            row4 = hoja4.createRow(aux4);

            if (payroll.getNumeroDeEmpleado() != null){
                row4.createCell(0).setCellValue(payroll.getNumeroDeEmpleado());
            }
            if (payroll.getNombre() != null){
                row4.createCell(1).setCellValue(payroll.getNombre());
            }
            if (payroll.getBanco() != null){
                row4.createCell(2).setCellValue(payroll.getBanco());
            }
            if (payroll.getNumeroDeCuenta() != null){
                row4.createCell(3).setCellValue(payroll.getNumeroDeCuenta());
            }
            if (payroll.getCuentaClabe() != null){
                row4.createCell(4).setCellValue(payroll.getCuentaClabe());
            }
            if (payroll.getSucursal() != null){
                row4.createCell(5).setCellValue(payroll.getSucursal());
            }
            if (payroll.getArea() != null){
                row4.createCell(6).setCellValue(payroll.getArea());
            }
            if (payroll.getRfc() != null){
                row4.createCell(7).setCellValue(payroll.getRfc());
            }
            if (payroll.getCurp() != null){
                row4.createCell(8).setCellValue(payroll.getCurp());
            }
            if (payroll.getPago() != null){
                row4.createCell(9).setCellValue(payroll.getPago().doubleValue());
                sum33 += payroll.getPago().doubleValue();
            }
            if (payroll.getComisionNec() != null){
                row4.createCell(10).setCellValue(payroll.getComisionNec().doubleValue());
                sum34 += payroll.getComisionNec().doubleValue();
            }
            if (payroll.getTotalFacturar() != null){
                row4.createCell(11).setCellValue(payroll.getTotalFacturar().doubleValue());
                sum35 += payroll.getTotalFacturar().doubleValue();
            }

            aux4 ++;

        }

        row4 = hoja4.createRow(aux4+2);

        row4.createCell(8).setCellValue("TOTALES");
        row4.createCell(9).setCellValue(sum33);
        row4.createCell(10).setCellValue(sum34);
        row4.createCell(11).setCellValue(sum35);

        Sheet hoja5 = wb.createSheet("AMERMEDIA NEC");

        //Se crea la fila que contiene la cabecera
        Row row5 = hoja5.createRow(0);

        row5.createCell(0).setCellValue("N");
        row5.createCell(1).setCellValue("NOMBRE DEL EMPLEADO");
        row5.createCell(2).setCellValue("BANCO");
        row5.createCell(3).setCellValue("N. DE CUENTA");
        row5.createCell(4).setCellValue("CLABE");
        row5.createCell(5).setCellValue("SUCURSAL");
        row5.createCell(6).setCellValue("AREA");
        row5.createCell(7).setCellValue("RFC");
        row5.createCell(8).setCellValue("CURP");
        row5.createCell(9).setCellValue("MONTO A PAGAR");
        row5.createCell(10).setCellValue("11.50%");
        row5.createCell(11).setCellValue("TOTAL A FACTURAR");

        //Implementacion del estilo
        for (Cell celda : row5) {
            celda.setCellStyle(style);
        }

        int aux5 = 1;

        List <Payroll> amermediaNec = payrollDao.findByDistributor(3);

        Double sum36 = 0.0;
        Double sum37 = 0.0;
        Double sum38 = 0.0;

        for(Payroll payroll : amermediaNec){
            row5 = hoja5.createRow(aux5);

            if (payroll.getNumeroDeEmpleado() != null){
                row5.createCell(0).setCellValue(payroll.getNumeroDeEmpleado());
            }
            if (payroll.getNombre() != null){
                row5.createCell(1).setCellValue(payroll.getNombre());
            }
            if (payroll.getBanco() != null){
                row5.createCell(2).setCellValue(payroll.getBanco());
            }
            if (payroll.getNumeroDeCuenta() != null){
                row5.createCell(3).setCellValue(payroll.getNumeroDeCuenta());
            }
            if (payroll.getCuentaClabe() != null){
                row5.createCell(4).setCellValue(payroll.getCuentaClabe());
            }
            if (payroll.getSucursal() != null){
                row5.createCell(5).setCellValue(payroll.getSucursal());
            }
            if (payroll.getArea() != null){
                row5.createCell(6).setCellValue(payroll.getArea());
            }
            if (payroll.getRfc() != null){
                row5.createCell(7).setCellValue(payroll.getRfc());
            }
            if (payroll.getCurp() != null){
                row5.createCell(8).setCellValue(payroll.getCurp());
            }
            if (payroll.getPago() != null){
                row5.createCell(9).setCellValue(payroll.getPago().doubleValue());
                sum36 += payroll.getPago().doubleValue();
            }
            if (payroll.getComisionNec() != null){
                row5.createCell(10).setCellValue(payroll.getComisionNec().doubleValue());
                sum37 += payroll.getComisionNec().doubleValue();
            }
            if (payroll.getTotalFacturar() != null){
                row5.createCell(11).setCellValue(payroll.getTotalFacturar().doubleValue());
                sum38 += payroll.getTotalFacturar().doubleValue();
            }

            aux5 ++;

        }

        row5 = hoja5.createRow(aux5+2);

        row5.createCell(8).setCellValue("TOTALES");
        row5.createCell(9).setCellValue(sum36);
        row5.createCell(10).setCellValue(sum37);
        row5.createCell(11).setCellValue(sum38);

        Sheet hoja6 = wb.createSheet("TOTALES");

        //Se crea la fila que contiene la cabecera
        Row row6 = hoja6.createRow(0);


        //Implementacion del estilo
        for (Cell celda : row6) {
            celda.setCellStyle(style);
        }

        BigDecimal amerNecSum = (BigDecimal) payrollDao.sumDistributorNec(2);
        BigDecimal amermediaNecSum = (BigDecimal) payrollDao.sumDistributorNec(3);
        BigDecimal rhmasAmer  = (BigDecimal) outsourcingDao.findSumRhmasByDwEnterprise(dwEnterprisesList,applicatioDateStart,applicationDateEnd);
        BigDecimal rhmasAmermedia  = (BigDecimal) outsourcingDao.findSumRhmasByDwEnterprise(dwEnterList,applicatioDateStart,applicationDateEnd);

        Double totalDeTotales = 0.00;

        if(amerNecSum != null){
            totalDeTotales+=amerNecSum.doubleValue();
            Row rowAmerNec = hoja6.createRow(3);

            rowAmerNec.createCell(1).setCellValue("AMER NEC");
            rowAmerNec.createCell(2).setCellValue(amerNecSum.doubleValue());
        }
        if(amermediaNecSum != null){
            totalDeTotales+=amermediaNecSum.doubleValue();
            Row rowAmermediaNec = hoja6.createRow(4);

            rowAmermediaNec.createCell(1).setCellValue("AMERMEDIA NEC");
            rowAmermediaNec.createCell(2).setCellValue(amermediaNecSum.doubleValue());
        }
        if (rhmasAmer != null){
            totalDeTotales+=rhmasAmer.doubleValue();
            Row rowRhmasAmer = hoja6.createRow(5);

            rowRhmasAmer.createCell(1).setCellValue("RHMAS AMER");
            rowRhmasAmer.createCell(2).setCellValue(rhmasAmer.doubleValue());
        }
        if (rhmasAmermedia != null){
            totalDeTotales+=rhmasAmermedia.doubleValue();
            Row rowRhmasAmermedia = hoja6.createRow(6);

            rowRhmasAmermedia.createCell(1).setCellValue("RHMAS AMERMEDIA");
            rowRhmasAmermedia.createCell(2).setCellValue(rhmasAmermedia.doubleValue());
        }


        Row rowTotal = hoja6.createRow(8);

        rowTotal.createCell(1).setCellValue("TOTAL");
        rowTotal.createCell(2).setCellValue(totalDeTotales);


        Sheet hoja7 = wb.createSheet("CUADRO ADMON");

        //Se crea la fila que contiene la cabecera
        Row row7 = hoja7.createRow(0);


        row7.createCell(0).setCellValue("CONCEPTO");
        row7.createCell(1).setCellValue("VENTAS");
        row7.createCell(2).setCellValue("% CONTRA VENTA");

        //Implementacion del estilo
        for (Cell celda : row7) {
            celda.setCellStyle(style);
        }

        int aux7 = 1;

        List<Object[]> results = queryResult;

        Double sum100 = 0.00;
        Double sum200 = 0.00;

        for(Object[] admonReport : results){
            row7 = hoja7.createRow(aux7);

            if (admonReport[0] != null){
                row7.createCell(0).setCellValue(String.valueOf(admonReport[0]));
            }
            if (admonReport[1] != null){
                row7.createCell(1).setCellValue(Double.parseDouble(String.valueOf(admonReport[1])));
                sum100 += Double.parseDouble(String.valueOf(admonReport[1]));
            }
            if (admonReport[2] != null){
                String cadena = String.valueOf(admonReport[2]);
                if (!cadena.isEmpty()){
                    row7.createCell(2).setCellValue(Double.parseDouble(cadena));
                    sum200 += Double.parseDouble(cadena);
                }
            }


            aux7 ++;

        }

        BigDecimal totalAmountSap = (BigDecimal) sapSaleDao.sumTotalAmuntBeteween(applicatioDateStart,applicationDateEnd);

        row7 = hoja7.createRow(aux7+2);
        row7.createCell(0).setCellValue("Subtotal");
        row7.createCell(1).setCellValue(sum100-totalAmountSap.doubleValue());
        row7.createCell(2).setCellValue(sum200);

        hoja1.autoSizeColumn(0);
        hoja2.autoSizeColumn(0);
        hoja3.autoSizeColumn(0);
        hoja4.autoSizeColumn(0);
        hoja5.autoSizeColumn(0);
        hoja6.autoSizeColumn(0);
        hoja7.autoSizeColumn(0);

        wb.write(fileOutputStream);
        wb.write(outputStream);
    }


    @Override
    public void reportCost(OutputStream outputStream, List queryResult) throws IOException{

    Workbook wb = new XSSFWorkbook();
    //Definicion del estilo de la cabecera
        Font font = wb.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 10);
        font.setFontName("Arial");
        font.setColor(IndexedColors.WHITE.getIndex());
        CellStyle style = wb.createCellStyle();
        style.setFont(font);
        style.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        style.setAlignment(CellStyle.ALIGN_CENTER);

        CellStyle cellDateStyle = wb.createCellStyle();
        CreationHelper createHelper = wb.getCreationHelper();
        cellDateStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/MM/yyyy"));

        Sheet hoja1 = wb.createSheet("NEC");

        //Se crea la fila que contiene la cabecera
        Row row1 = hoja1.createRow(0);


        row1.createCell(0).setCellValue("No");
        row1.createCell(1).setCellValue("NOMBRE DEL EMPLEADO");
        row1.createCell(2).setCellValue("BANCO");
        row1.createCell(3).setCellValue("N. DE CUENTA");
        row1.createCell(4).setCellValue("CLABE");
        row1.createCell(5).setCellValue("SUCURSAL");
        row1.createCell(6).setCellValue("AREA");
        row1.createCell(7).setCellValue("RFC");
        row1.createCell(8).setCellValue("CURP");
        row1.createCell(9).setCellValue("MONTO A PAGAR");
        row1.createCell(10).setCellValue("COSTO");
        row1.createCell(11).setCellValue("TOTAL A FACTURAR");
        row1.createCell(12).setCellValue("PAGADOR");

        //Implementacion del estilo
        for (Cell celda : row1) {
            celda.setCellStyle(style);
        }

        int aux1 = 1;

        List<Object[]> results = queryResult;

        for(Object[] costReportPojo : results){
            row1 = hoja1.createRow(aux1);

            if (costReportPojo[0] != null){
                row1.createCell(0).setCellValue(Integer.parseInt(String.valueOf(costReportPojo[0])));
            }
            if (costReportPojo[1] != null){
                row1.createCell(1).setCellValue(String.valueOf(costReportPojo[1]));
            }
            if (costReportPojo[2] != null){
                row1.createCell(2).setCellValue(String.valueOf(costReportPojo[2]));
            }
            if (costReportPojo[3] != null){
                row1.createCell(3).setCellValue(String.valueOf(costReportPojo[3]));
            }
            if (costReportPojo[4] != null){
                row1.createCell(4).setCellValue(String.valueOf(costReportPojo[4]));
            }
            if (costReportPojo[6] != null){
                row1.createCell(5).setCellValue(String.valueOf(costReportPojo[6]));
            }
            if (costReportPojo[8] != null){
                row1.createCell(6).setCellValue(String.valueOf(costReportPojo[8]));
            }
            if (costReportPojo[9] != null){
                row1.createCell(7).setCellValue(String.valueOf(costReportPojo[9]));
            }
            if (costReportPojo[10] != null){
                row1.createCell(8).setCellValue(String.valueOf(costReportPojo[10]));
            }
            if (costReportPojo[11] != null){
                row1.createCell(9).setCellValue(Double.parseDouble(String.valueOf(costReportPojo[11])));
            }
            if (costReportPojo[12] != null){
                row1.createCell(10).setCellValue(Double.parseDouble(String.valueOf(costReportPojo[12])));
            }
            if (costReportPojo[13] != null){
                row1.createCell(11).setCellValue(Double.parseDouble(String.valueOf(costReportPojo[13])));
            }

            if(costReportPojo[14] != null){
                row1.createCell(12).setCellValue(String.valueOf(costReportPojo[14]));
            }

            aux1 ++;

        }

        hoja1.autoSizeColumn(0);

        wb.write(outputStream);



    }


    @Override
    public void reportCorporateNec(FileOutputStream fileOutputStream) throws IOException {

        Workbook wb = new XSSFWorkbook();
        //Definicion del estilo de la cabecera
        Font font = wb.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 10);
        font.setFontName("Arial");
        font.setColor(IndexedColors.WHITE.getIndex());
        CellStyle style = wb.createCellStyle();
        style.setFont(font);
        style.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        style.setAlignment(CellStyle.ALIGN_CENTER);

        CellStyle cellDateStyle = wb.createCellStyle();
        CreationHelper createHelper = wb.getCreationHelper();
        cellDateStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/MM/yyyy"));

        Sheet hoja1 = wb.createSheet("GMT ENZO");

        //Se crea la fila que contiene la cabecera
        Row row1 = hoja1.createRow(0);

        row1.createCell(0).setCellValue("N");
        row1.createCell(1).setCellValue("NOMBRE DEL EMPLEADO");
        row1.createCell(2).setCellValue("BANCO");
        row1.createCell(3).setCellValue("N. DE CUENTA");
        row1.createCell(4).setCellValue("CLABE");
        row1.createCell(5).setCellValue("SUCURSAL");
        row1.createCell(6).setCellValue("AREA");
        row1.createCell(7).setCellValue("RFC");
        row1.createCell(8).setCellValue("CURP");
        row1.createCell(9).setCellValue("MONTO A PAGAR");
        row1.createCell(10).setCellValue("11.50%");
        row1.createCell(11).setCellValue("TOTAL A FACTURAR");

        //Implementacion del estilo
        for (Cell celda : row1) {
            celda.setCellStyle(style);
        }

        List<Payroll> payrollList = payrollDao.findAllByAmountPositives();

        int aux1 = 1;

        Double sum1 = 0.0;
        Double sum2 = 0.0;
        Double sum3 = 0.0;

        for(Payroll payroll : payrollList){
            row1 = hoja1.createRow(aux1);

            if (payroll.getNumeroDeEmpleado() != null){
                row1.createCell(0).setCellValue(payroll.getNumeroDeEmpleado());
            }
            if (payroll.getNombre() != null){
                row1.createCell(1).setCellValue(payroll.getNombre());
            }
            if (payroll.getBanco() != null){
                row1.createCell(2).setCellValue(payroll.getBanco());
            }
            if (payroll.getNumeroDeCuenta() != null){
                row1.createCell(3).setCellValue(payroll.getNumeroDeCuenta());
            }
            if (payroll.getCuentaClabe() != null){
                row1.createCell(4).setCellValue(payroll.getCuentaClabe());
            }
            if (payroll.getSucursal() != null){
                row1.createCell(5).setCellValue(payroll.getSucursal());
            }
            if (payroll.getArea() != null){
                row1.createCell(6).setCellValue(payroll.getArea());
            }
            if (payroll.getRfc() != null){
                row1.createCell(7).setCellValue(payroll.getRfc());
            }
            if (payroll.getCurp() != null){
                row1.createCell(8).setCellValue(payroll.getCurp());
            }
            if (payroll.getPago() != null){
                row1.createCell(9).setCellValue(payroll.getPago().doubleValue());
                sum1 += payroll.getPago().doubleValue();
            }
            if (payroll.getComisionNec() != null){
                row1.createCell(10).setCellValue(payroll.getComisionNec().doubleValue());
                sum2 += payroll.getComisionNec().doubleValue();
            }
            if (payroll.getTotalFacturar() != null){
                row1.createCell(11).setCellValue(payroll.getTotalFacturar().doubleValue());
                sum3 += payroll.getTotalFacturar().doubleValue();
            }

            aux1 ++;

        }

        row1 = hoja1.createRow(aux1+2);
        row1.createCell(8).setCellValue("TOTALES");
        row1.createCell(9).setCellValue(sum1);
        row1.createCell(10).setCellValue(sum2);
        row1.createCell(11).setCellValue(sum3);

        hoja1.autoSizeColumn(0);

        wb.write(fileOutputStream);
    }

    @Override
    public void reportDistributionNec(FileOutputStream fileOutputStream) throws IOException {

        Workbook wb = new XSSFWorkbook();
        //Definicion del estilo de la cabecera
        Font font = wb.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 10);
        font.setFontName("Arial");
        font.setColor(IndexedColors.WHITE.getIndex());
        CellStyle style = wb.createCellStyle();
        style.setFont(font);
        style.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        style.setAlignment(CellStyle.ALIGN_CENTER);

        CellStyle cellDateStyle = wb.createCellStyle();
        CreationHelper createHelper = wb.getCreationHelper();
        cellDateStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/MM/yyyy"));

        Sheet hoja1 = wb.createSheet("AMER NEC");

        //Se crea la fila que contiene la cabecera
        Row row1 = hoja1.createRow(0);

        row1.createCell(0).setCellValue("N");
        row1.createCell(1).setCellValue("NOMBRE DEL EMPLEADO");
        row1.createCell(2).setCellValue("BANCO");
        row1.createCell(3).setCellValue("N. DE CUENTA");
        row1.createCell(4).setCellValue("CLABE");
        row1.createCell(5).setCellValue("SUCURSAL");
        row1.createCell(6).setCellValue("AREA");
        row1.createCell(7).setCellValue("RFC");
        row1.createCell(8).setCellValue("CURP");
        row1.createCell(9).setCellValue("MONTO A PAGAR");
        row1.createCell(10).setCellValue("11.50%");
        row1.createCell(11).setCellValue("TOTAL A FACTURAR");

        //Implementacion del estilo
        for (Cell celda : row1) {
            celda.setCellStyle(style);
        }

        int aux1 = 1;

        List <Payroll> amerNec = payrollDao.findByDistributor(2);

        Double sum1 = 0.0;
        Double sum2 = 0.0;
        Double sum3 = 0.0;

        for(Payroll payroll : amerNec){
            row1 = hoja1.createRow(aux1);

            if (payroll.getNumeroDeEmpleado() != null){
                row1.createCell(0).setCellValue(payroll.getNumeroDeEmpleado());
            }
            if (payroll.getNombre() != null){
                row1.createCell(1).setCellValue(payroll.getNombre());
            }
            if (payroll.getBanco() != null){
                row1.createCell(2).setCellValue(payroll.getBanco());
            }
            if (payroll.getNumeroDeCuenta() != null){
                row1.createCell(3).setCellValue(payroll.getNumeroDeCuenta());
            }
            if (payroll.getCuentaClabe() != null){
                row1.createCell(4).setCellValue(payroll.getCuentaClabe());
            }
            if (payroll.getSucursal() != null){
                row1.createCell(5).setCellValue(payroll.getSucursal());
            }
            if (payroll.getArea() != null){
                row1.createCell(6).setCellValue(payroll.getArea());
            }
            if (payroll.getRfc() != null){
                row1.createCell(7).setCellValue(payroll.getRfc());
            }
            if (payroll.getCurp() != null){
                row1.createCell(8).setCellValue(payroll.getCurp());
            }
            if (payroll.getPago() != null){
                row1.createCell(9).setCellValue(payroll.getPago().doubleValue());
                sum1 += payroll.getPago().doubleValue();
            }
            if (payroll.getComisionNec() != null){
                row1.createCell(10).setCellValue(payroll.getComisionNec().doubleValue());
                sum2 += payroll.getComisionNec().doubleValue();
            }
            if (payroll.getTotalFacturar() != null){
                row1.createCell(11).setCellValue(payroll.getTotalFacturar().doubleValue());
                sum3 += payroll.getTotalFacturar().doubleValue();
            }

            aux1 ++;

        }

        row1 = hoja1.createRow(aux1+2);
        row1.createCell(8).setCellValue("TOTALES");
        row1.createCell(9).setCellValue(sum1);
        row1.createCell(10).setCellValue(sum2);
        row1.createCell(11).setCellValue(sum3);

        Sheet hoja2 = wb.createSheet("AMERMEDIA NEC");

        //Se crea la fila que contiene la cabecera
        Row row2 = hoja2.createRow(0);

        row2.createCell(0).setCellValue("N");
        row2.createCell(1).setCellValue("NOMBRE DEL EMPLEADO");
        row2.createCell(2).setCellValue("BANCO");
        row2.createCell(3).setCellValue("N. DE CUENTA");
        row2.createCell(4).setCellValue("CLABE");
        row2.createCell(5).setCellValue("SUCURSAL");
        row2.createCell(6).setCellValue("AREA");
        row2.createCell(7).setCellValue("RFC");
        row2.createCell(8).setCellValue("CURP");
        row2.createCell(9).setCellValue("MONTO A PAGAR");
        row2.createCell(10).setCellValue("11.50%");
        row2.createCell(11).setCellValue("TOTAL A FACTURAR");

        //Implementacion del estilo
        for (Cell celda : row2) {
            celda.setCellStyle(style);
        }

        int aux2 = 1;

        List <Payroll> amermediaNec = payrollDao.findByDistributor(3);

        Double sum4 = 0.0;
        Double sum5 = 0.0;
        Double sum6 = 0.0;

        for(Payroll payroll : amermediaNec){
            row2 = hoja2.createRow(aux2);

            if (payroll.getNumeroDeEmpleado() != null){
                row2.createCell(0).setCellValue(payroll.getNumeroDeEmpleado());
            }
            if (payroll.getNombre() != null){
                row2.createCell(1).setCellValue(payroll.getNombre());
            }
            if (payroll.getBanco() != null){
                row2.createCell(2).setCellValue(payroll.getBanco());
            }
            if (payroll.getNumeroDeCuenta() != null){
                row2.createCell(3).setCellValue(payroll.getNumeroDeCuenta());
            }
            if (payroll.getCuentaClabe() != null){
                row2.createCell(4).setCellValue(payroll.getCuentaClabe());
            }
            if (payroll.getSucursal() != null){
                row2.createCell(5).setCellValue(payroll.getSucursal());
            }
            if (payroll.getArea() != null){
                row2.createCell(6).setCellValue(payroll.getArea());
            }
            if (payroll.getRfc() != null){
                row2.createCell(7).setCellValue(payroll.getRfc());
            }
            if (payroll.getCurp() != null){
                row2.createCell(8).setCellValue(payroll.getCurp());
            }
            if (payroll.getPago() != null){
                row2.createCell(9).setCellValue(payroll.getPago().doubleValue());
                sum4 += payroll.getPago().doubleValue();
            }
            if (payroll.getComisionNec() != null){
                row2.createCell(10).setCellValue(payroll.getComisionNec().doubleValue());
                sum5 += payroll.getComisionNec().doubleValue();
            }
            if (payroll.getTotalFacturar() != null){
                row2.createCell(11).setCellValue(payroll.getTotalFacturar().doubleValue());
                sum6 += payroll.getTotalFacturar().doubleValue();
            }

            aux2 ++;

        }

        row2 = hoja2.createRow(aux2+2);
        row2.createCell(8).setCellValue("TOTALES");
        row2.createCell(9).setCellValue(sum4);
        row2.createCell(10).setCellValue(sum5);
        row2.createCell(11).setCellValue(sum6);

        hoja1.autoSizeColumn(0);
        hoja2.autoSizeColumn(0);

        wb.write(fileOutputStream);
    }
}
