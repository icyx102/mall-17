package priv.jesse.mall.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import jakarta.persistence.*;  // 确保导入的是 jakarta.persistence 包
import java.util.Date;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@SuperBuilder
@Table(name = "PRODUCT")
public class Product extends AbstractEntity {
    /**
     * 商品标题
     */
    @Column(name = "title",length = 255)
    private String title;

    /**
     * 市场价
     */
    @Column(name = "market_price")
    private Double marketPrice;

    /**
     * 商城价
     */
    @Column(name = "shop_price")
    private Double shopPrice;

    /**
     * 主图
     */
    @Column(name = "image",length = 255)
    private String image;

    /**
     * 描述
     */
    @Column(name = "description", columnDefinition = "text",length = 512)
    private String desc;

    /**
     * 是否热门商品
     */
    @Column(name = "is_hot")
    private Integer isHot;

    /**
     * 二级分类Id
    @Column(name = "csid")
    @Transient
    private Integer csid;
     */

    /**
     * 商品创建日期
     */
    @Column(name = "pdate")
    private Date pdate;

    /**
     * 类型
     */
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "csid")
    private Classification categorySec;

    /**
     * 商品标签
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "PRODUCT_TAG",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags;


    public Integer getCsid(){
        return categorySec == null ? null : categorySec.getId();
    }

    public void setCsid(Integer csid){
        if(categorySec != null){
            categorySec.setId(csid);
            return;
        }
        categorySec = new Classification();
        categorySec.setId(csid);
    }
}