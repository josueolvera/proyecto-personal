package mx.bidg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import mx.bidg.config.JsonViews;
import mx.bidg.service.CRequestCategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Rafael Viveros
 * Created on 20/11/15.
 */
@Controller
@RequestMapping("/request-categories")
public class RequestCategoriesController {

    @Autowired
    CRequestCategoriesService categoriesService;

    @Autowired
    private ObjectMapper mapper;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody
    String findAllRequestCategories() throws Exception {
        return mapper.writerWithView(JsonViews.EmbeddedRequestCategory.class).writeValueAsString(categoriesService.findAll());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody String findRequestCategoryById(@PathVariable int id) throws Exception {
        return mapper.writerWithView(JsonViews.Root.class).writeValueAsString(categoriesService.findById(id));
    }
}
