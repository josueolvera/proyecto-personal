package mx.bidg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import mx.bidg.config.JsonViews;
import mx.bidg.model.RoleTravelType;
import mx.bidg.service.RoleTravelTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created by josueolvera on 14/07/16.
 */
@Controller
@RequestMapping("/role-travel-type")
public class RoleTravelTypeController {

    @Autowired
    private RoleTravelTypeService roleTravelTypeService;

    @Autowired
    private ObjectMapper mapper;

    @RequestMapping(value = "/role/{idRole}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findByIdRole(@PathVariable Integer idRole) throws Exception {
        List<RoleTravelType> RoleTravelTypes = roleTravelTypeService.findByIdRole(idRole);
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(RoleTravelTypes), HttpStatus.OK);
    }
}
