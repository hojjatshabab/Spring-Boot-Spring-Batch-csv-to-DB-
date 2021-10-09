package ir.aja.matna.servicebatchprocess.configurationbatch.order;

import ir.aja.matna.servicebatchprocess.model.Order;
import lombok.Data;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

@EnableBatchProcessing
@Configuration
@Data
public class MainBatchProcessOrder {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final ItemWriter<Order> itemWriter;
    private final ItemReader<Order> itemReader;


    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(10);
        return executor;

    }

    @Bean
    public Step stepProcess() {
        return stepBuilderFactory.get("stepProcess")
                .<Order, Order>chunk(100000)
                .reader(itemReader)
                .writer(itemWriter)
                .taskExecutor(taskExecutor())
                .build();
    }

    @Bean("jobProcess")
    public Job jobProcess() {
        return jobBuilderFactory.get("jobProcess")
                .start(stepProcess()).build();
    }

}
