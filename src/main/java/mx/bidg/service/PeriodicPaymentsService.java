/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.service;

import mx.bidg.model.PeriodicsPayments;

/**
 *
 * @author sistemask
 */
public interface PeriodicPaymentsService {
    
    public PeriodicsPayments saveData(String data, int idRequest) throws Exception;
    
}