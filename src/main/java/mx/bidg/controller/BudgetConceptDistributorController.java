package mx.bidg.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import mx.bidg.config.JsonViews;
import mx.bidg.model.BudgetConceptDistributor;
import mx.bidg.service.BudgetConceptDistributorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Rafael Viveros
 * Created on 17/05/16.
 */
@Controller
@RequestMapping("/budget-concept-distributor")
public class BudgetConceptDistributorController {

    @Autowired
    private BudgetConceptDistributorService budgetConceptDistributorService;

    @Autowired
    private ObjectMapper mapper;

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> save(@RequestBody String data) throws IOException {
        JsonNode node = mapper.readTree(data);
        List<BudgetConceptDistributor> list = budgetConceptDistributorService.saveJsonNode(node);
        return new ResponseEntity<>(
                "Registro guardado con exito",
                HttpStatus.OK
        );
    }
}
