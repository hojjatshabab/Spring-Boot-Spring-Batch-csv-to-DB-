package ir.aja.matna.servicebatchprocess.configurationbatch.order;

import ir.aja.matna.servicebatchprocess.model.Order;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

@Configuration
public class ItemReaderOrder{

    public static String[] names = new String[]
            {"order_id", "first_name", "last_name", "email"
                    , "cost", "item_id", "item_name", "ship_date"};
    @Bean
    public ItemReader<Order> itemReaderOrderBean(){

        FlatFileItemReader<Order> itemReader = new FlatFileItemReader<>();
        itemReader.setLinesToSkip(1);
        itemReader.setName("CSV-Reader");
        itemReader.setResource(new FileSystemResource("/data/New folder/shipped_orders.csv"));
        itemReader.setLineMapper(lineMapper());
        itemReader.setSaveState(false);
        return itemReader;
    }

    public DefaultLineMapper<Order> lineMapper(){
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setNames(names);

        DefaultLineMapper<Order> lineMapper= new DefaultLineMapper<>();

        lineMapper.setLineTokenizer(tokenizer);
        lineMapper.setFieldSetMapper(fieldSet ->{
            Order order = new Order();
            order.setOrderId(fieldSet.readLong("order_id"));
            order.setCost(fieldSet.readBigDecimal("cost"));
            order.setEmail(fieldSet.readString("email"));
            order.setFirstName(fieldSet.readString("first_name"));
            order.setLastName(fieldSet.readString("last_name"));
            order.setItemId(fieldSet.readString("item_id"));
            order.setItemName(fieldSet.readString("item_name"));
            order.setShipDate(fieldSet.readDate("ship_date"));
            return order;
        });
        return lineMapper;
    }

}
