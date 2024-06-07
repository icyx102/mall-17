package priv.jesse.mall.service.impl;

import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import priv.jesse.mall.dao.ClassificationDao;
import priv.jesse.mall.dao.OrderItemDao;
import priv.jesse.mall.dao.ProductDao;
import priv.jesse.mall.entity.Classification;
import priv.jesse.mall.entity.Product;
import priv.jesse.mall.service.ProductService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductDao productDao;
    @Autowired
    private ClassificationDao classificationDao;
    @Resource
    private OrderItemDao orderItemDao;

    @Override
    public Product findById(int id) {
        return productDao.findById(id).orElse(null);
    }

    @Override
    public Page<Product> findAll(Pageable pageable) {
        return productDao.findAll(pageable);
    }

    /**
     * 查找热门商品
     *
     * @return
     */
    @Override
    public List<Product> findHotProduct() {
        return productDao.findByIsHot(1, null);
    }

    @Override
    public List<Product> findProductsByTitle(String title) {
        return productDao.findProductsByTitleLike(title);
    }

    /**
     * 查找最新商品
     *
     * @param pageable
     * @return
     */
    @Override
    public List<Product> findNewProduct(Pageable pageable) {
        return productDao.findNew(pageable);
    }

    /**
     * 根据一级分类查找商品
     *
     * @param cid
     * @param pageable
     * @return
     */
    @Override
    public List<Product> findByCid(int cid, Pageable pageable) {
        // 查找出所有二级分类
        List<Classification> sec = classificationDao.findByParentId(cid);
        List<Classification> secIds = new ArrayList<>();
        for (Classification classification : sec) {
            Classification obj = new Classification();
            obj.setId(classification.getId());
            secIds.add(obj);
        }
        if (secIds.isEmpty()) {
            return Collections.emptyList();
        }
        if (secIds.size() == 1) {
            return findByCsid(secIds.get(0).getId(), pageable);
        }
        Page<Product> page = productDao.findByCategorySecIn(secIds, pageable);
        return page.getContent();
    }

    /**
     * 根据二级分类查找商品
     *
     * @param csid
     * @param pageable
     * @return
     */
    @Override
    public List<Product> findByCsid(int csid, Pageable pageable) {
        Classification classification = new Classification();
        classification.setId(csid);
        Page<Product> page = productDao.findByCategorySec(classification, pageable);
        return page.getContent();
    }

    @Override
    public Product update(Product product) {
        productDao.save(product);
        return findById(product.getId());
    }

    @Override
    public Product create(Product product) {
        productDao.save(product);
        return findById(product.getId());
    }

    @Override
    public boolean delById(int id) {
        Product product = new Product();
        product.setId(id);
        if (orderItemDao.countByProduct(product) > 0) {
            throw new RuntimeException("该商品已被下单，暂时不支持删除");
        }
        Optional<Product> productOptional = productDao.findById(id);
        productOptional.ifPresent(productDao::delete);
        return productOptional.isPresent();
    }

    @Override
    public long count() {
        return productDao.count();
    }
}
