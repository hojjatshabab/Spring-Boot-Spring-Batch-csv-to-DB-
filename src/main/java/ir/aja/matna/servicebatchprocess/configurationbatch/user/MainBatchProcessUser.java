package ir.aja.matna.servicebatchprocess.configurationbatch.user;

import ir.aja.matna.servicebatchprocess.configurationbatch.user.model.User;
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
public class MainBatchProcessUser {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final ItemReader<User> itemReader;
    private final ItemWriter<User> itemWriter;

    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(10);
        return executor;

    }

    @Bean
    public Step stepUser() {
        return stepBuilderFactory.get("stepUser")
                .<User, User>chunk(100000)
                .reader(itemReader)
                .writer(itemWriter)
                .taskExecutor(taskExecutor())
                .build();
    }

    @Bean("jobUser")
    public Job jobUser() {
        return jobBuilderFactory.get("jobUser")
                .start(stepUser()).build();
    }

}
