package config;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableJpaRepositories("repository") // Updated repository package
@EnableTransactionManagement
public class DatabaseConfig {

}
