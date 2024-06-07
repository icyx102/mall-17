package priv.jesse.mall.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import jakarta.persistence.*;  // 确保导入的是 jakarta.persistence 包

@Entity
@Data
@NoArgsConstructor
@SuperBuilder
@Table(name = "USERS")
public class Users extends AbstractEntity {
    /**
     * 用户名
     */
    @Column(name = "username", unique = true, nullable = false,length = 16)
    private String username;

    /**
     * 密码
     */
    @Column(name = "password",nullable = false,length = 64)
    private String password;

    /**
     * 姓名
     */
    @Column(name = "name", nullable = false,length = 32)
    private String name;

    /**
     * 邮件
     */
    @Column(name = "email",length = 64)
    private String email;

    /**
     * 电话
     */
    @Column(name = "phone",length = 20)
    private String phone;

    /**
     * 地址
     */
    @Column(name = "addr",length = 128)
    private String addr;
}
