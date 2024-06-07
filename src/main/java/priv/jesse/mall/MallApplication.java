package priv.jesse.mall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Program startup entry
 *
 * @ServletComponentScan Set up Spring to scan our own servlets and filters at startup for Druid monitoring
 * @EnableTransactionManagement Enable transaction management
 */
@ServletComponentScan
@EnableConfigurationProperties
@EnableTransactionManagement
@SpringBootApplication
public class MallApplication {

	public static void main(String[] args) {
		SpringApplication.run(MallApplication.class, args);
	}
}
