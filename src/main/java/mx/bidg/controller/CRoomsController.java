package mx.bidg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import java.io.IOException;
import java.util.List;
import mx.bidg.config.JsonViews;
import mx.bidg.model.CRooms;
import mx.bidg.model.Events;
import mx.bidg.service.CRoomsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author rubens
 */
@Controller
@RequestMapping("/rooms")
public class CRoomsController {
    
    @Autowired
    CRoomsService cRoomsService;

    @Autowired
    private ObjectMapper mapper;
    
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findAll(@RequestParam(name = "role", required = false) Integer idRole) throws IOException{
        List<CRooms> crooms = cRoomsService.getRooms(idRole);
        return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(crooms), HttpStatus.OK);
    }
    
    @RequestMapping(value = "/{idRoom}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> findById(@PathVariable Integer idRoom) throws IOException {
        CRooms crooms = cRoomsService.findById(idRoom);
          return new ResponseEntity<>(mapper.writerWithView(JsonViews.Embedded.class).writeValueAsString(crooms), HttpStatus.OK);
        
        
    
    
    
    
}
    
}
