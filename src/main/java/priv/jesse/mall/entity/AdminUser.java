package priv.jesse.mall.entity;

import lombok.Data;

import jakarta.persistence.*;  // 确保导入的是 jakarta.persistence 包

/**
 * 管理员用户
 */
@Entity
@Data
@Table(name = "ADMIN_USER")
public class AdminUser extends AbstractEntity {

    /**
     * 用户名
     */
    @Column(name = "username",nullable = false,length = 64,unique = true)
    private String username;

    /**
     * 密码
     */
    @Column(name = "password",nullable = false,length = 32)
    private String password;
}
