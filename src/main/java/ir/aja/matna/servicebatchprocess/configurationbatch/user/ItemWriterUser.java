package ir.aja.matna.servicebatchprocess.configurationbatch.user;

import ir.aja.matna.servicebatchprocess.model.Order;
import ir.aja.matna.servicebatchprocess.model.User;
import lombok.Data;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@Data
public class ItemWriterUser {

    public DataSource dataSource(){
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("org.postgresql.Driver");
        dataSourceBuilder.url("jdbc:postgresql://localhost:5432/test");
        dataSourceBuilder.username("postgres");
        dataSourceBuilder.password("postgres");
        return dataSourceBuilder.build();
    }

    public static String INSERT_ORDER_SQL = "insert into "
            + "user_output"
            + "(name, family)"
            + " values(:name, :family)";

    @Bean
    public ItemWriter<User> itemWriterUserBean() {
        return new JdbcBatchItemWriterBuilder<User>()
                .dataSource(dataSource())
                .sql(INSERT_ORDER_SQL)
                .beanMapped()
                .build();
    }

}
