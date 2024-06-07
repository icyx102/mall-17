package priv.jesse.mall.web.rest;

import jakarta.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import priv.jesse.mall.entity.Product;
import priv.jesse.mall.entity.pojo.ResultBean;
import priv.jesse.mall.service.ProductService;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController("apiProductController")
@RequestMapping("/api/product")
public class ProductController {

    @Resource
    private ProductService productService;

    @GetMapping("/page")
    ResultBean<Object> page(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        Page<Product> page = productService.findAll(PageRequest.of(pageNum - 1, pageSize, Sort.by("id").descending()));
        Map<String,Object> dataMap = new LinkedHashMap<>(4);
        dataMap.put("total", page.getTotalElements());
        dataMap.put("list", page.getContent());
        return new ResultBean<>(dataMap);
    }


    @PostMapping
    ResultBean<Product> save(@RequestBody Product product) {
        Product entity = productService.create(product);
        return new ResultBean<>(entity);
    }

    @GetMapping("/detail/{id}")
    ResultBean<Product> detail(@PathVariable Integer id) {
        return new ResultBean<>(productService.findById(id));
    }

    @DeleteMapping("/{id}")
    ResultBean<Boolean> delete(@PathVariable Integer id) {
        return new ResultBean<>(productService.delById(id));
    }
}
