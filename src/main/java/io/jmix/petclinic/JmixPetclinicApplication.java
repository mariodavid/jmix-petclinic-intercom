package io.jmix.petclinic;

import com.google.common.base.Strings;
import io.jmix.petclinic.intercom.sync.IntercomSyncConfig;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

@SpringBootApplication
@ConfigurationPropertiesScan
public class JmixPetclinicApplication {

	@Autowired
	private Environment environment;
	@Autowired
	private IntercomSyncConfig intercomSyncConfig;

	public static void main(String[] args) {
		SpringApplication.run(JmixPetclinicApplication.class, args);
	}

	@Bean
	@Primary
	@ConfigurationProperties("main.datasource")
	DataSourceProperties dataSourceProperties() {
		return new DataSourceProperties();
	}

	@Bean
	@Primary
	@ConfigurationProperties("main.datasource.hikari")
	DataSource dataSource(DataSourceProperties dataSourceProperties) {
		return dataSourceProperties.initializeDataSourceBuilder().build();
	}

	@EventListener
	public void printApplicationUrl(ApplicationStartedEvent event) {
		String applicationStartedMessage = String.format(
				"Application started at http://localhost:%s%s",
				environment.getProperty("local.server.port"),
				Strings.nullToEmpty(environment.getProperty("server.servlet.context-path"))
		);

		LoggerFactory.getLogger(JmixPetclinicApplication.class)
				.info(applicationStartedMessage);
		LoggerFactory.getLogger(JmixPetclinicApplication.class)
				.info(String.format("Access token: %s", intercomSyncConfig.getAccessToken()));
	}
}
