package priv.jesse.mall.web;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import priv.jesse.mall.entity.AdminUser;
import priv.jesse.mall.entity.Users;

public class BaseController {

    @Autowired
    protected HttpServletRequest request;

    @Autowired
    protected HttpServletResponse response;


    protected Users getCurrentUser() {
        return (Users) request.getSession().getAttribute("user");
    }

    protected AdminUser getCurrentAdmin() {
        return (AdminUser) request.getSession().getAttribute("login_user");
    }

    protected boolean storeSessionUser(Object user) {
        if (user instanceof AdminUser) {
            request.getSession().setAttribute("login_user", user);
        } else if (user instanceof Users) {
            request.getSession().setAttribute("user", user);
        } else {
            return false;
        }
        return true;
    }

    protected void removeSessionUser() {
        request.getSession().removeAttribute("login_user");
        request.getSession().removeAttribute("user");
    }

}
