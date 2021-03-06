package mx.bidg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import mx.bidg.config.JsonViews;
import mx.bidg.model.TravelExpenses;
import mx.bidg.model.Users;
import mx.bidg.service.RequestTravelExpenseEmailNotificationService;
import mx.bidg.service.TravelExpensesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by josueolvera on 14/07/16.
 */
@Controller
@RequestMapping("/travel-expenses")
public class TravelExpensesController {

    @Autowired
    private TravelExpensesService travelExpensesService;

    @Autowired
    private RequestTravelExpenseEmailNotificationService requestTravelExpenseEmailNotificationService;

    @Autowired
    private ObjectMapper mapper;

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> save(@RequestBody String data, HttpSession httpSession) throws Exception {

        Users user = (Users) httpSession.getAttribute("user");
        TravelExpenses travelExpense = travelExpensesService.save(data, user);
        requestTravelExpenseEmailNotificationService.sendEmailToUser(travelExpense);
        requestTravelExpenseEmailNotificationService.sendEmailToAdminAux(travelExpense);
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(travelExpense), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> getTravelExpenses(@RequestParam(name = "user", required = false) Integer idUser) throws Exception {
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(travelExpensesService.getTravelExpenses(idUser)), HttpStatus.OK);
    }

    @RequestMapping(value = "/{idTravelExpense}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> getTravelExpenseById(@PathVariable Integer idTravelExpense) throws Exception {
        TravelExpenses travelExpense = travelExpensesService.findById(idTravelExpense);
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(travelExpense), HttpStatus.OK);
    }

    @RequestMapping(value = "/change-status", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> changeStatus(
            @RequestParam(name = "travel_expense") Integer idTravelExpense,
            @RequestBody String data
    ) throws Exception {

        TravelExpenses travelExpense = travelExpensesService.changeRequestStatus(idTravelExpense, data);
        requestTravelExpenseEmailNotificationService.sendEmailToUserStatus(travelExpense);

        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(travelExpense), HttpStatus.OK);
    }
}
