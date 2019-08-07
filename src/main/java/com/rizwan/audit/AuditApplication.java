
package com.rizwan.audit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.rizwan.audit.security.WebSecurityConfig;
import com.rizwan.audit.service.AuditService;
import com.rizwan.audit.web.ProductsController;

@SpringBootApplication
@ComponentScan(basePackageClasses = { ProductsController.class, AuditService.class, WebSecurityConfig.class })
public class AuditApplication {

	public static void main(String[] args) {

		SpringApplication.run(AuditApplication.class, args);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {

		return new BCryptPasswordEncoder();
	}
}
