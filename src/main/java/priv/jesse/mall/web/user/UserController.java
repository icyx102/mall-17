package priv.jesse.mall.web.user;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import priv.jesse.mall.entity.Users;
import priv.jesse.mall.entity.pojo.ResultBean;
import priv.jesse.mall.service.UserService;
import priv.jesse.mall.service.exception.LoginException;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * Open the registration page
     */
    @RequestMapping("/toRegister.html")
    public String toRegister() {
        return "mall/user/register";
    }

    /**
     * Open the login page
     */
    @RequestMapping("/toLogin.html")
    public String toLogin() {
        return "mall/user/login";
    }

    /**
     * Login
     */
    @RequestMapping("/login.do")
    public void login(String username,
                      String password,
                      HttpServletRequest request,
                      HttpServletResponse response) throws IOException {
        Users users = userService.checkLogin(username, password);
        if (users != null) {
            // Login successful, redirect to homepage
            request.getSession().setAttribute("user", users);
            response.sendRedirect("/mall/index.html");
        } else {
            throw new LoginException("Login failed! Username or password incorrect");
        }
    }

    /**
     * Register
     */
    @RequestMapping("/register.do")
    public void register(String username,
                         String password,
                         String name,
                         String phone,
                         String email,
                         String addr,
                         HttpServletResponse response) throws IOException {
        Users users = new Users();
        users.setUsername(username);
        users.setPhone(phone);
        users.setPassword(password);
        users.setName(name);
        users.setEmail(email);
        users.setAddr(addr);
        userService.create(users);
        // After registration, redirect to login page
        response.sendRedirect("/mall/user/toLogin.html");
    }

    /**
     * Logout
     */
    @RequestMapping("/logout.do")
    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.getSession().removeAttribute("user");
        response.sendRedirect("/mall/index.html");
    }

    /**
     * Check if username is unique
     */
    @ResponseBody
    @RequestMapping("/checkUsername.do")
    public ResultBean<Boolean> checkUsername(String username){
        List<Users> users = userService.findByUsername(username);
        if (users==null || users.isEmpty()){
            return new ResultBean<>(true);
        }
        return new ResultBean<>(false);
    }

    /**
     * If an error occurs, forward to this page
     */
    @RequestMapping("/error.html")
    public String error(HttpServletResponse response, HttpServletRequest request) {
        return "error";
    }
}
