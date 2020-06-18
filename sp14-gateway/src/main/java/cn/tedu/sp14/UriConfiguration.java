package cn.tedu.sp14;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;


@PropertySource(value = {"classpath:config.properties"})
@ConfigurationProperties(prefix = "config")
@Data
@Component
public class UriConfiguration {
    private String httpbin;
}