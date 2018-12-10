package io.online.salesorder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

@SpringBootApplication
@EnableHystrixDashboard
public class HystrixDashboard117694Application {

	public static void main(String[] args) {
		SpringApplication.run(HystrixDashboard117694Application.class, args);
	}
}
