package priv.jesse.mall.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import priv.jesse.mall.entity.pojo.ResultBean;

import java.io.Serializable;
import java.util.Set;

/**
 * 统一异常处理
 * 在Controller中抛出的异常，GlobalExceptionHandler中定义的处理方法可以起作用
 * 其他的业务层异常也可以单独处理
 */
@ControllerAdvice
public class GlobalExceptionHandler implements Serializable {

    @Resource
    private ObjectMapper objectMapper;

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 默认的异常处理
     *
     * @param req
     * @param e
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResultBean<String> defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        //记录日志
        LOGGER.error(e.getMessage(), e);
        e.printStackTrace();
        ResultBean<String> r = new ResultBean<>(e);
        r.setData(req.getRequestURI());
        return r;
    }

    @ExceptionHandler(value = RuntimeException.class)
    public Object runtimeExceptionHandler(HttpServletRequest req, HttpServletResponse res, Exception e) throws Exception {
        //记录日志
        LOGGER.error(e.getMessage(), e);
        String ajaxLabel = req.getHeader("X-Requested-With");
        System.err.println("ajaxLabel = " + ajaxLabel);
        if ("XMLHttpRequest".equalsIgnoreCase(ajaxLabel)) {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setView(new MappingJackson2JsonView(objectMapper));
            modelAndView.addAllObjects(new ResultBean<>(e).toMap());
            return modelAndView;
        }
        req.setAttribute("msg", e.getMessage());
        return "forward:/mall/user/error.html";

    }

    /**
     * 处理validation异常
     *
     * @param req
     * @param e
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = ConstraintViolationException.class)
    @ResponseBody
    public ResultBean<String> validationExceptionHandler(HttpServletRequest req, ConstraintViolationException e) throws Exception {
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        StringBuilder strBuilder = new StringBuilder();
        for (ConstraintViolation<?> violation : violations) {
            strBuilder.append(violation.getMessage() + ",");
        }
        LOGGER.error(strBuilder.toString(), e);
        ResultBean<String> r = new ResultBean(strBuilder.toString());
        r.setData(req.getRequestURI());
        return r;
    }


}
