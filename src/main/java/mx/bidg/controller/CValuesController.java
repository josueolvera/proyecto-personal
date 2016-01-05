package mx.bidg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import mx.bidg.config.JsonViews;
import mx.bidg.model.CValues;
import mx.bidg.service.CValuesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.util.List;

/**
 * @author Rafael Viveros
 * Created on 30/12/15.
 */
@Controller
@RequestMapping("values")
public class CValuesController {

    @Autowired
    private CValuesService valuesService;

    private ObjectMapper mapper = new ObjectMapper();

    @RequestMapping(method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public ResponseEntity<String> getValues() throws IOException {
        List<CValues> values = valuesService.findAll();
        return new ResponseEntity<>(
                mapper.writerWithView(JsonViews.Root.class).writeValueAsString(values),
                HttpStatus.OK
        );
    }
}
