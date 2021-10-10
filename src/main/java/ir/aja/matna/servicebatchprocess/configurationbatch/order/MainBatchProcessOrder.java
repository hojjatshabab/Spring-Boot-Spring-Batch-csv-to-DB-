package ir.aja.matna.servicebatchprocess.configurationbatch.order;

import ir.aja.matna.servicebatchprocess.configurationbatch.order.model.Order;
import lombok.Data;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

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
    public Step stepOrder() {
        return stepBuilderFactory.get("stepOrder")
                .<Order, Order>chunk(100000)
                .reader(itemReader)
                .writer(itemWriter)
                .taskExecutor(taskExecutor())
                .build();
    }

    @Bean
    public Job jobOrder() {
        return jobBuilderFactory.get("jobOrder")
                .start(stepOrder()).build();
    }

}
