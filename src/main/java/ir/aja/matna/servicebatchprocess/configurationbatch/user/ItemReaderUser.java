package ir.aja.matna.servicebatchprocess.configurationbatch.user;

import ir.aja.matna.servicebatchprocess.model.Order;
import ir.aja.matna.servicebatchprocess.model.User;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

@Configuration
public class ItemReaderUser {

    public static String[] names = new String[]
            {"name", "family"};
    @Bean
    public ItemReader<User> itemReaderUserBean(){

        FlatFileItemReader<User> itemReader = new FlatFileItemReader<>();
        itemReader.setLinesToSkip(1);
        itemReader.setName("CSV-Reader");
        itemReader.setResource(new FileSystemResource("/data/New folder/user.csv"));
        itemReader.setLineMapper(lineMapper());
        itemReader.setSaveState(false);
        return itemReader;
    }

    public DefaultLineMapper<User> lineMapper(){
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setNames(names);

        DefaultLineMapper<User> lineMapper= new DefaultLineMapper<>();

        lineMapper.setLineTokenizer(tokenizer);
        lineMapper.setFieldSetMapper(fieldSet ->{
            User user = new User();
            user.setName(fieldSet.readString("name"));
            user.setFamily(fieldSet.readString("family"));
            return user;
        });
        return lineMapper;
    }

}
