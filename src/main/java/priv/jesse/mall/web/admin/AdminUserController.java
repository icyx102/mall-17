package priv.jesse.mall.web.admin;

import jakarta.annotation.Resource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import priv.jesse.mall.entity.Users;
import priv.jesse.mall.entity.pojo.ResultBean;
import priv.jesse.mall.service.UserService;
import priv.jesse.mall.web.BaseController;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/user")
public class AdminUserController extends BaseController {

    @Resource
    private UserService userService;

    /**
     * User list page
     */
    @RequestMapping("/toList.html")
    public String toList() {
        return "admin/user/list";
    }

    /**
     * Edit page
     */
    @RequestMapping("/toEdit.html")
    public String toEdit(int id, Map<String, Object> map) {
        Users users = userService.findById(id);
        map.put("user", users);
        return "admin/user/edit";
    }

    /**
     * All user list
     */
    @ResponseBody
    @RequestMapping("/list.do")
    public ResultBean<List<Users>> findAllUser(
            int pageindex,
            @RequestParam(value = "pageSize", defaultValue = "15") int pageSize
    ) {
        Pageable pageable = PageRequest.of(pageindex, pageSize, null);
        List<Users> users = userService.findAll(pageable).getContent();
        return new ResultBean<>(users);
    }

    @ResponseBody
    @RequestMapping("/getTotal.do")
    public ResultBean<Integer> geTotal() {
        Pageable pageable = PageRequest.of(0, 15, null);
        int total = (int) userService.findAll(pageable).getTotalElements();
        return new ResultBean<>(total);
    }

    @ResponseBody
    @RequestMapping("/del.do")
    public ResultBean<Boolean> del(int id) {
        userService.delById(id);
        return new ResultBean<>(true);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/update.do")
    public ResultBean<Boolean> update(
            int id,
            String username,
            String password,
            String name,
            String phone,
            String email,
            String addr
    ) {
        // before update
        Users users = userService.findById(id);
        users.setId(id);
        users.setName(name);
        users.setUsername(username);
        users.setPassword(password);
        users.setAddr(addr);
        users.setEmail(email);
        users.setPhone(phone);
        userService.update(users);
        return new ResultBean<>(true);
    }
}