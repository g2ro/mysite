package mysite.config.app;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionManager;

@Configuration
public class DBConfig {
	
	@Bean
	public DataSource dataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName("org.mariadb.jdbc.Driver");
		dataSource.setUrl("jdbc:mariadb://192.168.64.3:3306/webdb");
		dataSource.setUsername("webdb");
		dataSource.setPassword("webdb");
		
		return dataSource;
	}
	
	@Bean
	public TransactionManager transactionManager(DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}
}
