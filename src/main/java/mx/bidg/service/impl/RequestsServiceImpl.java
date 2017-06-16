package mx.bidg.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import mx.bidg.dao.*;
import mx.bidg.exceptions.ValidationException;
import mx.bidg.model.*;
import mx.bidg.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RequestsServiceImpl implements RequestsService {

    @Autowired
    RequestsDao requestsDao;

    @Autowired
    AccountingAccountsDao accountingAccountsDao;

    @Autowired
    RequestTypesProductDao requestTypesProductDao;

    @Autowired
    CRequestCategoriesDao cRequestCategoriesDao;

    @Autowired
    UsersDao usersDao;

    @Autowired
    BudgetsDao budgetsDao;

    @Autowired
    RealBudgetSpendingDao budgetYearConceptDao;

    @Autowired
    CMonthsDao cMonthsDao;

    @Autowired
    FoliosService foliosService;

    @Autowired
    RequestProductsDao requestProductsDao;

    @Autowired
    PriceEstimationsDao priceEstimationsDao;

    @Autowired
    PeriodicPaymentsDao periodicPaymentsDao;

    @Autowired
    AccountsPayableDao accountsPayableDao;

    @Autowired
    private EmailDeliveryService emailDeliveryService;

    @Autowired
    private EmailTemplatesService emailTemplatesService;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private DistributorCostCenterDao distributorCostCenterDao;

    @Autowired
    private RequestHistoryService requestHistoryService;

    @Autowired
    private RequestOrderDocumentsDao requestOrderDocumentsDao;

    @Autowired
    private RequestOrderDetailDao requestOrderDetailDao;

    @Override
    public HashMap<String, Object> getBudgetMonthProductType(String data) throws Exception {

        JsonNode jsonRequest = mapper.readTree(data);
        HashMap<String, Object> hashMap = new HashMap<>();

        CRequestsCategories cRequestsCategory = cRequestCategoriesDao
                .findById(jsonRequest.get("idRequestCategory").asInt());

        AccountingAccounts accountingAccounts = accountingAccountsDao.findById(jsonRequest.get("idProductType").asInt());

        Users userResponsable = usersDao.findByIdFetchDwEmployee(jsonRequest.get("idUserResponsable").asInt());
        LocalDateTime date = LocalDateTime.now();

        CMonths month = cMonthsDao.findById(date.getMonthValue());
        Integer year = date.getYear();

        if(month == null) {
            throw new ValidationException("No existe el mes", "Error al obtener el mes");
        }

        DwEnterprises dwEnterprise = userResponsable.getDwEmployee().getDwEnterprise();

        Budgets budget = budgetsDao.findByCombination(dwEnterprise.getDistributor(), dwEnterprise.getArea(),
                accountingAccounts);

        if(budget == null) {
            throw new ValidationException("No existe el Presupuesto", "No existe un presupuesto para esta solicitud");
        }

        RequestTypesProduct requestTypesProduct = requestTypesProductDao.findByCombination(cRequestsCategory,
                accountingAccounts);

        if(requestTypesProduct == null) {
            throw new ValidationException("No existe el RequestTypesProduct", "No existe un tipo de producto "
                    + "asociado a esta solicitud");
        }

        hashMap.put("requestTypesProduct", requestTypesProduct);
        RealBudgetSpending realBudgetSpending = budgetYearConceptDao.findByCombination(budget, month, dwEnterprise, year);

        if(realBudgetSpending == null) {
            throw new ValidationException("No existe Presupuesto para la fecha solicitada", "No existe Presupuesto para la fecha solicitada");
        }

        hashMap.put("realBudgetSpending", realBudgetSpending);

        return hashMap;
    }


    @Override
    public Requests saveData(String data, Users user) throws Exception {

        JsonNode jsonRequest = mapper.readTree(data);
        Requests request = new Requests();

        String justify = (jsonRequest.get("request").get("purpose").asText());
        int idCostCenter = jsonRequest.get("request").get("idCostCenter").asInt();
        int idAccountingAccounts = jsonRequest.get("request").get("idAccountingAccount").asInt();


        DistributorCostCenter distributorCostCenter = distributorCostCenterDao.findByIdCostCenterAndAA(idCostCenter, idAccountingAccounts);


        //51 es el id de Requests en CTables
        request.setFolio(foliosService.createNew(new CTables(51)));
        request.setRequestStatus(CRequestStatus.ENVIADA_PENDIENTE);
        request.setRequestCategory(CRequestsCategories.SOLICITUD);
        request.setRequestType(CRequestTypes.VIGENTES);
        request.setDistributorCostCenter(distributorCostCenter);
        request.setIdAccessLevel(1);
        request.setTotalExpended(new BigDecimal(0.00));
        request.setReason(justify);

        if (user != null){
            request.setUserName(user.getUsername());
            if (user.getDwEmployee() != null){
                if (user.getDwEmployee().getEmployee() != null){
                    request.setEmployees(user.getDwEmployee().getEmployee());
                }
            }
        }

        request.setCreationDate(LocalDateTime.now());
        request.setIdAccessLevel(1);
        List<RequestProducts> requestProducts = new ArrayList<>();

        for(JsonNode jsonProducts : jsonRequest.get("products")) {
            RoleProductRequest roleProductRequest = new RoleProductRequest(jsonProducts.get("productBuy").get("idRoleProductRequest").asInt());

            RequestProducts requestProduct = new RequestProducts();

            requestProduct.setRoleProductRequest(roleProductRequest);
            requestProduct.setRequest(request);
            requestProduct.setIdAccessLevel(1);
            requestProduct.setCreationDate(LocalDateTime.now());
            requestProduct.setUsername(user.getUsername());
            requestProduct.setQuantity(jsonProducts.get("quantity").asInt());
            requestProducts.add(requestProduct);
        }

        request.setRequestProductsList(requestProducts);
        request = requestsDao.save(request);

        requestHistoryService.saveRequest(request, user);

        return request;
    }

    @Override
    public Requests authorization(Integer idRequest) {

        Requests request = requestsDao.findByIdFetchCategory(idRequest);
        if(request == null) {
            throw new ValidationException("No existe el request", "No se encuentra la solicitud", HttpStatus.CONFLICT);
        }

//        if((request.getIdRequestStatus() != CRequestStatus.COTIZADA.getIdRequestStatus())
//                && request.getIdRequestStatus() != CRequestStatus.PENDIENTE.getIdRequestStatus()) {
//            throw new ValidationException("Estatus de la solicitud invalida", "Esta solicitud ya fue validada anteriormente", HttpStatus.CONFLICT);
//        }

        boolean estimationAccepted = false;
        List<PriceEstimations> estimations = priceEstimationsDao.findByIdRequest(idRequest);
        if(estimations.isEmpty()) {
            throw new ValidationException("Esta solicitud no tiene cotizaciones", "Necesita agregar cotizaciones antes de " +
                    "autorizar una solicitud", HttpStatus.CONFLICT);
        }

        for(PriceEstimations estimation : estimations) {
            if(estimation.getIdEstimationStatus() == CEstimationStatus.APROBADA.getIdEstimationStatus()) {
                estimationAccepted = true;
                break;
            }
        }

        if(! estimationAccepted) {
            throw new ValidationException("Esta solicitud no tiene cotizaciones aprobadas", "Necesita aprobar una cotizacion " +
                    "antes de autorizar una solicitud", HttpStatus.CONFLICT);
        }

        PeriodicsPayments periodicsPayment = periodicPaymentsDao.findByFolio(request.getFolio());
        List<AccountsPayable> accountslist = accountsPayableDao.findByFolio(request.getFolio());

//        if((request.getRequestTypeProduct().getIdRequestCategory() == CRequestsCategories.PERIODICA && periodicsPayment == null) ||
//                (request.getRequestTypeProduct().getIdRequestCategory() == CRequestsCategories.COTIZABLE && accountslist.isEmpty()) ||
//                (request.getRequestTypeProduct().getIdRequestCategory() == CRequestsCategories.DIRECTA && accountslist.isEmpty())) {
//            throw new ValidationException("No hay CXP referentes a esta solicitud", "Necesita agregar informacion de pago para esta " +
//                    "solicitud", HttpStatus.CONFLICT);
//        }

        if (accountslist.size() > 0) {
            for ( AccountsPayable accountPayable : accountslist) {
                accountPayable.setAccountPayableStatus(CAccountsPayableStatus.PENDIENTE);
            }
        }

        if (periodicsPayment != null) {
            periodicsPayment.setPeriodicPaymentStatus(CPeriodicPaymentsStatus.ACTIVO);
        }
//        request.setRequestStatus(CRequestStatus.APROBADA);
        return requestsDao.update(request);
    }

    @Override
    public Requests findById(Integer idRequest) {
        return requestsDao.findById(idRequest);
    }

    @Override
    public Requests findByFolio(String folio) {
        return requestsDao.findByFolio(folio);
    }

    @Override
    public EmailTemplates sendEmailForNewRequest(Requests request) {
        EmailTemplates emailTemplate = emailTemplatesService.findByName("new_request_notification");
//        String typeRequest = request.getRequestTypeProduct().getRequestCategory().getCategory();
//        String typeRequestMinus = typeRequest.toLowerCase();
        emailTemplate.addProperty("request", request);
//        emailTemplate.addProperty("typeRequest", typeRequestMinus);
//        emailTemplate.addRecipient(new EmailRecipients(request.getUserRequest().getMail(), request.getUserRequest().getUsername(), EmailRecipients.TO));
        emailDeliveryService.deliverEmail(emailTemplate);
        return emailTemplate;
    }

    @Override
    public EmailTemplates sendEmailForNewRequestAuthorization(Requests request, Users user) {
        EmailTemplates emailTemplate = emailTemplatesService.findByName("request_autorization_notification");
//        String typeRequest = request.getRequestTypeProduct().getRequestCategory().getCategory();
//        String typeRequestMinus = typeRequest.toLowerCase();
        emailTemplate.addProperty("request", request);
//        emailTemplate.addProperty("typeRequest", typeRequestMinus);
        emailTemplate.addRecipient(new EmailRecipients(user.getMail(), user.getUsername(), EmailRecipients.TO));
        emailDeliveryService.deliverEmail(emailTemplate);
        return emailTemplate;
    }

    @Override
    public Requests changeActiveStatus(Integer idRequest) {

        Requests request = requestsDao.findById(idRequest);

//        if (request != null) {
//            request.setActive(!request.getActive());
//
//            request = requestsDao.update(request);
//        }

        return request;
    }

    @Override
    public List<Requests> findByRequestCategory(Integer idRequestCategory) {
        return requestsDao.findByRequestCategory(idRequestCategory);
    }

    @Override
    public List<Requests> findByCategoryAndTypeByEmployee(Integer idRequestCategory, Integer idRequestType, Integer idEmployee) {
        return requestsDao.findByCategoryAndTypeByEmployee(idRequestCategory, idRequestType, idEmployee);
    }

    @Override
    public List<Requests> findByCategoryAndType(Integer idRequestCategory, Integer idRequestType) {
        return requestsDao.findByCategoryAndType(idRequestCategory, idRequestType);
    }

    @Override
    public boolean deleteRequest(Integer idRequest) {

        Requests request = requestsDao.findById(idRequest);

        boolean aux = false;
        if (request != null){
            List<PriceEstimations> priceEstimationsList = priceEstimationsDao.findByIdRequest(request.getIdRequest());

            if (!priceEstimationsList.isEmpty()){
                for (PriceEstimations priceEstimations : priceEstimationsList){
                    priceEstimationsDao.delete(priceEstimations);
                }
            }

            List<RequestProducts> requestProductsList = requestProductsDao.findByIdRequest(request.getIdRequest());

            if (!requestProductsList.isEmpty()){
                for (RequestProducts requestProducts : requestProductsList){
                    requestProductsDao.delete(requestProducts);
                }
            }

            RequestOrderDocuments requestOrderDocuments = requestOrderDocumentsDao.findByIdRequest(request.getIdRequest());

            if (requestOrderDocuments != null){


                List<RequestOrderDetail> requestOrderDetails = requestOrderDetailDao.findByidReqOrderDocument(requestOrderDocuments.getIdRequestOrderDocument());

                if (!requestOrderDetails.isEmpty()){
                    for (RequestOrderDetail requestOrderDetail : requestOrderDetails){
                        requestOrderDetailDao.delete(requestOrderDetail);
                    }
                }

                requestOrderDocumentsDao.delete(requestOrderDocuments);
            }

            aux = requestsDao.delete(request);
        }
        return aux;
    }

    @Override
    public List<Requests> findByCategoryAndTypeAndStatus(Integer idRequestCategory, Integer idRequestType) {
        return requestsDao.findByCategoryAndTypeAndStatus(idRequestCategory, idRequestType);
    }

    @Override
    public Requests rejectRequest(Integer idRequest, String rejectJustify, Users user) {
        Requests request = requestsDao.findById(idRequest);

        if (request != null){
            request.setRequestStatus(CRequestStatus.RECHAZADA);
            request.setRequestType(CRequestTypes.RECHAZADA);
            request.setReasonResponsible(rejectJustify);
            request = requestsDao.update(request);

            requestHistoryService.saveRequest(request, user);
        }
        return request;
    }

    @Override
    public Requests update(Requests request) {
        return requestsDao.update(request);
    }
}
