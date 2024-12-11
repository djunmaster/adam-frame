package com.pilot.config;

import com.pilot.interceptor.SqlBeautyInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MybatisConfiguration {
    /**
     * 注入 Bean 容器
     *
     * @return 返回 SqlBeautyInterceptor 实例
     */
    @Bean
    @ConditionalOnProperty(name = "sql.beauty", havingValue = "true", matchIfMissing = true)
    public SqlBeautyInterceptor sqlBeautyInterceptor() {
        return new SqlBeautyInterceptor();
    }
}
