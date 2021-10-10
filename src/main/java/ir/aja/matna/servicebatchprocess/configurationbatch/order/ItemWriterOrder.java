package ir.aja.matna.servicebatchprocess.configurationbatch.order;

import ir.aja.matna.servicebatchprocess.configurationbatch.order.model.Order;
import lombok.Data;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@Data
public class ItemWriterOrder{

    private final DataSource dataSource;

    public static String INSERT_ORDER_SQL = "insert into "
            + "shipped_order"
            + "(order_id, first_name, last_name, email, item_id, item_name, cost, ship_date)"
            + " values(:orderId, :firstName, :lastName, :email, :itemId, :itemName, :cost, :shipDate)";

    @Bean
    public ItemWriter<Order> itemWriterOrderBean() {
        return new JdbcBatchItemWriterBuilder<Order>()
                .dataSource(dataSource)
                .sql(INSERT_ORDER_SQL)
                .beanMapped()
                .build();
    }

}
