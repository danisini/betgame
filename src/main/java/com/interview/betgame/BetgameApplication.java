package com.interview.betgame;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class BetgameApplication {

	public static void main(String[] args) throws LifecycleException {
		SpringApplication.run(BetgameApplication.class, args);

		Tomcat tomcat = new Tomcat();
		tomcat.setPort(8080);

		Context context = tomcat.addContext("", null);
		Tomcat.addServlet(context, "betGameServlet", new BetGameServlet());
		context.addServletMappingDecoded("/", "betGameServlet");
		tomcat.start();
		tomcat.getServer().await();
	}

	private static class BetGameServlet extends HttpServlet {
		@Override
		protected  void doGet(HttpServletRequest request, HttpServletResponse response) {

		}
	}

}
