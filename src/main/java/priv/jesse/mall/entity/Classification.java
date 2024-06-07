package priv.jesse.mall.entity;

import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

/**
 * 分类
 */
@Entity
@Data
@NoArgsConstructor
@SuperBuilder
@Table(name = "CLASSIFICATION")
public class Classification extends AbstractEntity {

    /**
     * 上级分类Id
     */
    @Column(name = "parent_id")
    private Integer parentId;

    /**
     * 分类名称
     */
    @Column(name = "cname",nullable = false,length = 32)
    private String cname;

    /**
     * 类型 1一级分类 2二级分类
     */
    @Column(name = "type")
    private Integer type;
}
