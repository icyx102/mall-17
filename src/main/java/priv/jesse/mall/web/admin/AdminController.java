package priv.jesse.mall.web.admin;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import priv.jesse.mall.entity.AdminUser;
import priv.jesse.mall.service.AdminUserService;
import priv.jesse.mall.web.BaseController;

import java.io.IOException;

@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController {

    @Resource
    private AdminUserService adminUserService;

    /**
     * Index
     */
    @RequestMapping("/toIndex.html")
    public String toIndex() {
        return "admin/index";
    }

    /**
     * Login page
     */
    @RequestMapping("/toLogin.html")
    public String toLogin() {
        return "admin/login";
    }

    /**
     * Login request
     */
    //@ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/login.do")
    public String login(String username, String password) throws IOException {
        AdminUser adminUser = adminUserService.checkLogin(username, password);
        storeSessionUser(adminUser);
        return "admin/index";
    }

    /**
     * logout
     */
    @RequestMapping("/logout.do")
    public String logout() throws IOException {
        removeSessionUser();
        return "redirect:toLogin.html";
    }
}