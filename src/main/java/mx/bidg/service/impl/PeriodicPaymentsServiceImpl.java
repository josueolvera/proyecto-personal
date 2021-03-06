/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import mx.bidg.dao.PeriodicPaymentsDao;
import mx.bidg.dao.RequestsDao;
import mx.bidg.model.CCurrencies;
import mx.bidg.model.CPeriodicPaymentsStatus;
import mx.bidg.model.CPeriods;
import mx.bidg.model.PeriodicsPayments;
import mx.bidg.service.PeriodicPaymentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PeriodicPaymentsServiceImpl implements PeriodicPaymentsService {
    
    @Autowired
    PeriodicPaymentsDao periodicPaymentsDao;
    
    @Autowired
    RequestsDao requestsDao;

    @Autowired
    private ObjectMapper mapper;

    @Override
    public PeriodicsPayments saveData(String data) throws Exception {
        
        JsonNode json = mapper.readTree(data);
        String folio = json.get("folio").asText();
        BigDecimal rate = ((json.get("rate").decimalValue().compareTo(BigDecimal.ZERO)) == 1)? json.get("rate").decimalValue() : BigDecimal.ONE;
        CPeriods period = new CPeriods(json.get("idPeriod").asInt());
        CCurrencies currency = new CCurrencies(json.get("idCurrency").asInt());
        BigDecimal amount = json.get("amount").decimalValue();
        LocalDateTime initialDate = LocalDateTime.parse(json.get("initialDate").asText(), DateTimeFormatter.ISO_DATE_TIME);
        LocalDateTime dueDate = (json.get("dueDate") == null || json.findValue("dueDate").asText().equals("")) ? null :
                LocalDateTime.parse(json.get("dueDate").asText(), DateTimeFormatter.ISO_DATE_TIME);
        
        PeriodicsPayments periodicsPayment = new PeriodicsPayments();
        periodicsPayment.setPeriod(period);
        periodicsPayment.setFolio(folio);
        periodicsPayment.setAmount(amount);
        periodicsPayment.setRate(rate);
        periodicsPayment.setInitialDate(initialDate);
        periodicsPayment.setDueDate(dueDate);
        //Ningun pago realizado de este servicio
        periodicsPayment.setPaymentNum(0);
        //Se crea el PeriodicsPayment con Status 1 = Inactivo
        periodicsPayment.setPeriodicPaymentStatus(new CPeriodicPaymentsStatus(1));
        periodicsPayment.setIdAccessLevel(1);
        periodicsPayment.setCurrency(currency);
        periodicsPayment = periodicPaymentsDao.save(periodicsPayment);
        
        return periodicsPayment;
    }

    @Override
    public PeriodicsPayments findByFolio(String folio) {
        return periodicPaymentsDao.findByFolio(folio);
    }

    @Override
    public PeriodicsPayments update(int idPayment, String data) throws Exception {
        JsonNode json = mapper.readTree(data);
        PeriodicsPayments payment = periodicPaymentsDao.findById(idPayment);
        BigDecimal amount = json.get("amount").decimalValue();
        LocalDateTime initialDate = (json.get("initialDate") == null || json.findValue("initialDate").asText().equals("")) ? null :
                LocalDateTime.parse(json.get("initialDate").asText(), DateTimeFormatter.ISO_DATE_TIME);
        LocalDateTime dueDate = (json.get("dueDate") == null || json.findValue("dueDate").asText().equals("")) ? null :
                LocalDateTime.parse(json.get("dueDate").asText(), DateTimeFormatter.ISO_DATE_TIME);
        int idPeriod = json.get("idPeriod").asInt();
        int idCurrency = json.get("idCurrency").asInt();
        BigDecimal rate = json.get("rate").decimalValue();

        payment.setAmount(amount);
        payment.setInitialDate(initialDate);
        payment.setDueDate(dueDate);
        payment.setPeriod(new CPeriods(idPeriod));
        payment.setCurrency(new CCurrencies(idCurrency));
        payment.setRate(rate);

        return payment;
    }

}
