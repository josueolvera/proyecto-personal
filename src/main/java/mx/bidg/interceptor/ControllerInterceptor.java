/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.interceptor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import mx.bidg.config.Permissions;
import mx.bidg.model.Users;
import mx.bidg.model.UsersRole;
import mx.bidg.service.UsersRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 *
 * @author sistemask
 */
@Component
public class ControllerInterceptor extends HandlerInterceptorAdapter {
    
    @Autowired
    Permissions permissions;
    @Autowired
    UsersRoleService usersRoleService;
    static final Logger logger = Logger.getLogger(ControllerInterceptor.class.getName());
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
        
        HttpSession session = request.getSession(false);

        if(session == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Es necesario inciar sesion");
            logger.log(Level.WARNING, "Sesion nula");
            return false;
        }
        
        Users user = (Users) session.getAttribute("user");
        
        if(user == null || user.getIdUser() == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Es necesario inciar sesion");
            logger.log(Level.WARNING, "Usuario nulo");
            return false;
        }
        
        String uri = request.getRequestURI();
        String method = request.getMethod().toLowerCase();
        
        String[] cadenas = uri.split("/");
        String key;
        
        if(cadenas.length > 3) {
            key = method + ":" + cadenas[2] + "/" + cadenas[3];
        } else {
            key = method + ":" + cadenas[2];
        }
        
        HashMap<String, ArrayList<Integer>> mapPermissions = permissions.getMap();
        ArrayList<Integer> idRoles = mapPermissions.get(key);
        
        if(idRoles == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Acceso denegado");
            logger.log(Level.WARNING, "No hay roles para este request. KEY: " + key);
            return false;
        }
        
        List<UsersRole> userRoles = usersRoleService.findAllByUserId(user);
        
        for(UsersRole userRol : userRoles) {
            Integer id = userRol.getIdSystemRole().getIdSystemRole();
            if(idRoles.contains(id)) {
                return true;
            }
            
        }
        
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Acceso denegado");
        return false;
        
    }
    
}