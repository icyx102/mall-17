package priv.jesse.mall.web.admin;

import io.micrometer.common.util.StringUtils;
import jakarta.annotation.Resource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import priv.jesse.mall.entity.Classification;
import priv.jesse.mall.entity.Product;
import priv.jesse.mall.entity.pojo.ResultBean;
import priv.jesse.mall.service.ClassificationService;
import priv.jesse.mall.service.ProductService;
import priv.jesse.mall.utils.FileUtil;
import priv.jesse.mall.web.BaseController;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Controller
@RequestMapping("/admin/product")
public class AdminProductController extends BaseController {
    @Resource
    private ProductService productService;
    @Resource
    private ClassificationService classificationService;

    @RequestMapping("/toList.html")
    public String toList() {
        return "admin/product/list";
    }

    @RequestMapping("/toAdd.html")
    public String toAdd() {
        return "admin/product/add";
    }

    @RequestMapping("/toEdit.html")
    public String toEdit(int id, Map<String, Object> map) {
        Product product = productService.findById(id);
        Classification classification = classificationService.findById(product.getCsid());
        product.setCategorySec(classification);
        map.put("product", product);
        return "admin/product/edit";
    }

    @ResponseBody
    @RequestMapping("/list.do")
    public ResultBean<List<Product>> listProduct(
            int pageindex,
            @RequestParam(value = "pageSize", defaultValue = "15") int pageSize
    ) {
        Pageable pageable = PageRequest.of(pageindex, pageSize);
        List<Product> list = productService.findAll(pageable).getContent();
        return new ResultBean<>(list);
    }

    @ResponseBody
    @RequestMapping("/getTotal.do")
    public ResultBean<Long> getTotal() {
        return new ResultBean<>(productService.count());
    }

    @RequestMapping("/del.do")
    @ResponseBody
    public ResultBean<Boolean> del(int id) {
        productService.delById(id);
        return new ResultBean<>(true);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/add.do")
    public String add(
            MultipartFile image,
            String title,
            Double marketPrice,
            Double shopPrice,
            int isHot,
            String desc,
            int csid
    ) throws Exception {
        Product product = new Product();
        product.setTitle(title);
        product.setMarketPrice(marketPrice);
        product.setShopPrice(shopPrice);
        product.setDesc(desc);
        product.setIsHot(isHot);
        product.setCsid(csid);
        product.setPdate(new Date());
        String imgUrl = FileUtil.saveFile(image);
        product.setImage(imgUrl);
        productService.create(product);
        if (Objects.isNull(product.getId()) || product.getId() <= 0) {
            request.setAttribute("message", "添加失败！");
            return "forward:toAdd.html";
        } else {
            request.setAttribute("id", product.getId());
            return "forward:toEdit.html";
        }
    }


    @RequestMapping(method = RequestMethod.POST, value = "/update.do")
    public String update(int id,
                         String title,
                         Double marketPrice,
                         Double shopPrice,
                         String desc,
                         int csid,
                         int isHot,
                         MultipartFile image
    ) throws Exception {
        Product product = productService.findById(id);
        product.setTitle(title);
        product.setMarketPrice(marketPrice);
        product.setShopPrice(shopPrice);
        product.setDesc(desc);
        product.setIsHot(isHot);
        product.setCsid(csid);
        product.setPdate(new Date());
        String imgUrl = FileUtil.saveFile(image);
        if (StringUtils.isNotBlank(imgUrl)) {
            product.setImage(imgUrl);
        }
        boolean flag;
        try {
            productService.update(product);
            flag = true;
        } catch (Exception e) {
            flag = false;
        }
        if (!flag) {
            request.setAttribute("message", "更新失败！");
        }
        return "redirect:toList.html";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/img/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Object> getImage(
            @PathVariable(name = "filename") String filename
    ) throws IOException {
        File file = new File("file/" + filename);
        if (file.exists()) {
            MediaType mediaType = MediaTypeFactory.getMediaType(file.getName()).orElse(MediaType.APPLICATION_OCTET_STREAM);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(mediaType);
            httpHeaders.setContentLength(file.length());
            httpHeaders.setContentDisposition(ContentDisposition.attachment().filename(URLEncoder.encode(filename, StandardCharsets.UTF_8)).build());
            return ResponseEntity.status(HttpStatus.CREATED).headers(httpHeaders).body(new FileSystemResource(file));
        }
        return ResponseEntity.notFound().build();
    }


}
