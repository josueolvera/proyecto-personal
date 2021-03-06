package mx.bidg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import mx.bidg.config.JsonViews;
import mx.bidg.model.CStockDocumentsTypes;
import mx.bidg.service.CStockDocumentsTypesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.List;

/**
 * @author Rafael Viveros
 * Created on 29/12/15.
 */
@Controller
@RequestMapping("/stock-document-types")
public class CStockDocumentsTypesController {

    @Autowired
    private CStockDocumentsTypesService stockDocumentsTypesService;

    @Autowired
    private ObjectMapper mapper;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> getAll() throws IOException {
        List<CStockDocumentsTypes> documentsTypes = stockDocumentsTypesService.findAll();
        return new ResponseEntity<>(
                mapper.writerWithView(JsonViews.Root.class).writeValueAsString(documentsTypes),
                HttpStatus.OK
        );
    }

    @RequestMapping(value = "/required",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> getAllRequired() throws IOException {
        List<CStockDocumentsTypes> documentsTypes = stockDocumentsTypesService.findAllRequired();
        return new ResponseEntity<>(
                mapper.writerWithView(JsonViews.Root.class).writeValueAsString(documentsTypes),
                HttpStatus.OK
        );
    }

    @RequestMapping(value = "/no-required",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> getAllNoRequired() throws IOException {
        List<CStockDocumentsTypes> documentsTypes = stockDocumentsTypesService.findAllNoRequired();
        return new ResponseEntity<>(
                mapper.writerWithView(JsonViews.Root.class).writeValueAsString(documentsTypes),
                HttpStatus.OK
        );
    }
}
