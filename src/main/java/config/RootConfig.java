package config;

import org.apache.commons.dbcp2.BasicDataSourceFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
// 定义 Spring 扫描的包
@ComponentScan(value = "club.*", includeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION,value = Service.class)},excludeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION,value = Controller.class)})
// 使用事务驱动管理器
@EnableTransactionManagement
public class RootConfig implements TransactionManagementConfigurer {    // 实现接口配置注解驱动事务

    private DataSource dataSource = null;

    /**
     * 配置数据库
     * @return 数据连接池
     */
    @Bean(name = "dataSource")
    public DataSource initDataSource(){
        if(dataSource != null)
            return dataSource;
        Properties properties = new Properties();
        properties.setProperty("driverClassName","com.mysql.jdbc.Driver");
        properties.setProperty("url","jdbc:mysql://localhost:3306/red_packet");
        properties.setProperty("username","root");
        properties.setProperty("password","GSSG255211");
        properties.setProperty("maxAlive","200");
        properties.setProperty("MaxIdle","20");
        properties.setProperty("MaxWait","30000");
        try{
            dataSource = BasicDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataSource;
    }

    /**
     * 配置 SqlSessionFactoryBean
     * @return SqlSessionFactoryBean
     */
    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactoryBean initSqlSessionFactory(){
        SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(initDataSource());
        // 配置 MyBatis 配置文件
        Resource resource = new ClassPathResource("spring/spring-mybatis.xml");
        sqlSessionFactory.setConfigLocation(resource);
        return sqlSessionFactory;
    }

    /**
     * 通过自动扫描，发现 MyBatis Mapper 接口
     * @return Mapper 扫描器
     */
    @Bean
    public MapperScannerConfigurer initMapperScannerConfigurer(){
        MapperScannerConfigurer msc = new MapperScannerConfigurer();
        msc.setBasePackage("club.*");
        msc.setSqlSessionFactoryBeanName("sqlSessionFactory");
        msc.setAnnotationClass(Repository.class);
        return msc;
    }

    /**
     * 注册注解事务
     */
    @Override
    @Bean(name = "annotationDrivenTransactionManager")
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
        transactionManager.setDataSource(initDataSource());
        return transactionManager;
    }
}
