
package com.rizwan.audit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.rizwan.audit.service.AuditService;
import com.rizwan.audit.web.ProductsController;

@SpringBootApplication
@ComponentScan(basePackageClasses = { ProductsController.class, AuditService.class })
public class AuditApplication {

	public static void main(String[] args) {

		SpringApplication.run(AuditApplication.class, args);
	}
}
