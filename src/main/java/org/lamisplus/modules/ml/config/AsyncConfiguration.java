package org.lamisplus.modules.ml.config;

import io.github.jhipster.async.ExceptionHandlingAsyncTaskExecutor;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.aop.interceptor.SimpleAsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Configuration
@EnableAsync
@EnableScheduling
@Slf4j
@AllArgsConstructor
public class AsyncConfiguration implements AsyncConfigurer, SchedulingConfigurer {

    @Override
    @Bean(name = "taskExecutor")
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor ();
        executor.setCorePoolSize (3);
        executor.setMaxPoolSize (3);
        executor.setQueueCapacity (3);
        return new ExceptionHandlingAsyncTaskExecutor (executor);
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new SimpleAsyncUncaughtExceptionHandler ();
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setScheduler (scheduledTaskExecutor ());
    }

    @Bean
    public Executor scheduledTaskExecutor() {
                                                                                                                                                    return Executors.newScheduledThreadPool (3);
    }
}
