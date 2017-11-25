package com.conf;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
public class DruidConfiguration {
	private static final Logger LOGGER = LoggerFactory.getLogger(DruidConfiguration.class);

	@Value("${spring.datasource.driverClassName}")
	private String driverClassName;
	
	@Value("${spring.datasource.url}")
	private String url;
	
	@Value("${spring.datasource.username}")
	private String username;
	
	@Value("${spring.datasource.password}")
	private String password;
	
	@Value("${spring.datasource.initialSize}")
	private int initialSize;
	
	@Value("${spring.datasource.minIdle}")
	private int minIdle;
	
	@Value("${spring.datasource.maxActive}")
	private int maxActive;
	
	@Value("${spring.datasource.maxWait}")
	private int maxWait;
	
	@Value("${spring.datasource.validationQuery}")
	private String validationQuery;
	
	@Value("${spring.datasource.testOnBorrow}")
	private boolean testOnBorrow;
	
	@Value("${spring.datasource.testOnReturn}")
	private boolean testOnReturn;
	
	@Value("${spring.datasource.testWhileIdle}")
	private boolean testWhileIdle;
	
	@Value("${spring.datasource.timeBetweenEvictionRunsMillis}")
	private int timeBetweenEvictionRunsMillis;
	
	@Value("${spring.datasource.minEvictableIdleTimeMillis}")
	private int minEvictableIdleTimeMillis;
	
	@Value("${spring.datasource.removeAbandoned}")
	private boolean removeAbandoned;
	
	@Value("${spring.datasource.removeAbandonedTimeout}")
	private int removeAbandonedTimeout;
	
	@Value("${spring.datasource.logAbandoned}")
	private boolean logAbandoned;
	
	@Value("${spring.datasource.filters}")
	private String filters;
	
	@Value("${spring.datasource.poolPreparedStatements}")
	private boolean poolPreparedStatements;
	
	@Value("${spring.datasource.maxPoolPreparedStatementPerConnectionSize}")
	private int maxPoolPreparedStatementPerConnectionSize;

	@Bean
	@Primary
	public DataSource dataSource() {
		DruidDataSource datasource = new DruidDataSource();

		datasource.setDriverClassName(driverClassName);
		datasource.setUrl(url);
		datasource.setUsername(username);
		datasource.setPassword(password);
		// 其它配置
		datasource.setInitialSize(initialSize);
		datasource.setMinIdle(minIdle);
		datasource.setMaxActive(maxActive);
		datasource.setMaxWait(maxWait);
		datasource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
		datasource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
		datasource.setValidationQuery(validationQuery);
		datasource.setTestWhileIdle(testWhileIdle);
		datasource.setTestOnBorrow(testOnBorrow);
		datasource.setTestOnReturn(testOnReturn);
		datasource.setPoolPreparedStatements(poolPreparedStatements);
		datasource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
		try {
			datasource.setFilters(filters);
		} catch (SQLException e) {
			LOGGER.error("druid configuration initialization filter", e);
		}
		return datasource;
	}

	@Bean
	public ServletRegistrationBean DruidStatViewServle2() {
		ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(),
				"/druid/*");

		// 添加初始化参数：initParams
		/** 用户名 */
		servletRegistrationBean.addInitParameter("loginUsername", "admin");
		/** 密码 */
		servletRegistrationBean.addInitParameter("loginPassword", "admin");
		/** 禁用页面上的重置功能 */
		servletRegistrationBean.addInitParameter("resetEnable", "false");
		return servletRegistrationBean;
	}

	@Bean
	public FilterRegistrationBean druidStatFilter2() {
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());

		/** 过滤规则 */
		filterRegistrationBean.addUrlPatterns("/*");
		/** 忽略资源 */
		filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid2/*");
		return filterRegistrationBean;
	}

}
