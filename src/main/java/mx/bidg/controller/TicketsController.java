package mx.bidg.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by gerardo8 on 19/05/16.
 */
@Controller
@RequestMapping("/sima")
public class TicketsController {

    @RequestMapping(value = "/tickets-management", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ModelAndView ticketsManagement() {
        ModelAndView model = new ModelAndView();
        model.setViewName("TicketsManagement");
        return model;
    }

    @RequestMapping(value = "/tickets",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ModelAndView tickets() {
        ModelAndView model = new ModelAndView();
        model.setViewName("Tickets");
        return model;
    }
}
