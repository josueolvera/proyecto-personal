/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import mx.bidg.config.ActiveSessionsList;
import mx.bidg.exceptions.ValidationException;
import mx.bidg.model.ActiveSession;
import mx.bidg.model.Users;
import mx.bidg.service.ActiveSessionService;
import mx.bidg.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author sistemask
 */
@Controller
@RequestMapping("/")
public class SessionController {
    
    @Autowired
    UsersService usersService;
    @Autowired
    ActiveSessionsList activeSessionsList;
    @Autowired
    ActiveSessionService activeSessionService;
    
    @RequestMapping(produces = {"text/html;charset=UTF-8"})
    public String home(Model model) {
        return "index";
    }
    
    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    public ResponseEntity<String> login(@RequestBody Users user, HttpServletRequest request) {
        
        Users userSession = usersService.verifyUserLogin(user);
        
        if(userSession == null) {
            throw new ValidationException("Usuario o contraseña invalidos");
        }
        
        HttpSession session = request.getSession();
        session.setAttribute("user", userSession);
        
        activeSessionService.save(new ActiveSession(userSession.getIdUser(), session.getId()));
        activeSessionsList.addSession(session);
        
        return new ResponseEntity<>(HttpStatus.OK );
    }
    
    @RequestMapping(value = "/logout", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    public ResponseEntity<String> logout(HttpServletRequest request) throws Exception {
        
        HttpSession session = request.getSession(false);
        
        if(session == null) {
            throw new ValidationException("Sesion invalida");
        }
        
        Users user = (Users) session.getAttribute("user");
        
        if(user == null) {
            throw new ValidationException("Usuario inválido");
        }
        
        activeSessionsList.removeSession(session.getId());
        activeSessionService.delete(new ActiveSession(user.getIdUser(), session.getId()));
        session.invalidate();
        
        return new ResponseEntity<>("La sesión se ha cerrado con éxito", HttpStatus.OK);
        
    }
    
}
