package priv.jesse.mall.web.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
    /**
     * Homepage
     * @return
     */
    @RequestMapping({"/index.html","/"})
    public String toIndex() {
        return "mall/index";
    }

}
