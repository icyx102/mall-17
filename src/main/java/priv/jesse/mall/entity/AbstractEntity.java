package priv.jesse.mall.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import jakarta.persistence.*;  // 确保导入的是 jakarta.persistence 包

/**
 * 一个抽象实体
 */
@Data
@NoArgsConstructor
@SuperBuilder
@MappedSuperclass
public abstract class AbstractEntity {

    /**
     * 主键ID
     */
    @Id
    @Column(name = "id", unique = true, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
}
