package priv.jesse.mall.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import priv.jesse.mall.entity.Classification;
import priv.jesse.mall.entity.Product;
import priv.jesse.mall.entity.Tag;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

/**
 * 单元测试
 * <pre>
 *     测试JPA 增删改查
 * </pre>
 */
@SpringBootTest
public class ProductServiceTest {


    @Resource
    private ProductService productService;
    @Resource
    private ClassificationService classificationService;
    @Resource
    private TagService tagService;
    @Resource
    private ObjectMapper objectMapper;

    private Product product = new Product();


    @Test
    public void startup() throws Exception {
        create();
        findById();
        findAll();
        findHotProduct();
        findProductsByTitle();
        findByCid();
        findByCsid();
        update();
        findAll();
        delById();
        findAll();
    }

    @Test
    public void findById() {
        Product thisProduct = productService.findById(product.getId());
        System.err.println(thisProduct);
        System.err.println(thisProduct.getTags().size());
    }

    @Test
    public void findAll() {
        Page<Product> page = productService.findAll(PageRequest.of(0, 20));
        System.err.println(page.getTotalElements());
    }

    @Test
    public void findHotProduct() {
        List<Product> list = productService.findHotProduct();
        list.forEach(System.err::println);
    }

    @Test
    public void findProductsByTitle() {
        List<Product> list = productService.findProductsByTitle("商品标题");
        list.forEach(System.err::println);
    }

    @Test
    public void findByCid() {
        List<Product> list = productService.findByCid(2, PageRequest.of(0, 20));
        list.forEach(System.err::println);
    }

    @Test
    public void findByCsid() {
        List<Product> list = productService.findByCsid(product.getCsid(), PageRequest.of(0, 20));
        list.forEach(System.err::println);
    }

    @Test
    public void update() {
        product.setTitle(product.getTitle() + "2");
        productService.update(product);
        System.err.println(product.getTitle());
    }

    @Test
    public void create() throws Exception{
        List<Classification> list = classificationService.findAll(2);
        List<Tag> tags = tagService.findAll(Example.of(new Tag()));
        if (!list.isEmpty()) {
            int size = list.size();
            product.setCategorySec(list.get(new Random().nextInt(size)));
            product.setDesc("测试商品");
            product.setImage("商品图片");
            product.setTitle("商品标题");
            product.setIsHot(1);
            product.setMarketPrice(300d);
            product.setShopPrice(320d);
            product.setPdate(new Date());
            product.setTags(new HashSet<>(tags));
            System.out.println(objectMapper.writeValueAsString(product));;
            productService.create(product);
            System.err.println(product.getId());
        }
    }

    @Test
    public void delById() {
        productService.delById(product.getId());
    }
}