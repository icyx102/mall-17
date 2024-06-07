package priv.jesse.mall;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import priv.jesse.mall.dao.AdminUserDao;
import priv.jesse.mall.entity.AdminUser;

@SpringBootTest
public class MallApplicationTests {
	@Autowired
	private AdminUserDao adminUserDao;

	@Test
	public void contextLoads() {
		AdminUser adminUser = adminUserDao.findByUsernameAndPassword("admin", "admin");
		System.out.println("-----------");
		System.out.println("adminUser:" + adminUser);
		System.out.println("-----------");
	}
}
