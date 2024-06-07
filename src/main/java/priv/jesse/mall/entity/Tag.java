package priv.jesse.mall.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "TAG")
public class Tag extends AbstractEntity{

    @Column(name = "title",nullable = false,length = 16)
    private String title;

    @Column(name = "create_time",nullable = false)
    private Date createTime;

}
