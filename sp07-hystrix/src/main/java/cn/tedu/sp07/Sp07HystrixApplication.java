package cn.tedu.sp07;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

//@EnableCircuitBreaker
//@EnableDiscoveryClient
//@SpringBootApplication

@SpringCloudApplication
public class Sp07HystrixApplication {


    @LoadBalanced
    @Bean
    public RestTemplate getRestTemplate() {
        SimpleClientHttpRequestFactory f = new SimpleClientHttpRequestFactory();
        f.setConnectTimeout(1000);
        f.setReadTimeout(1000);
        return new RestTemplate(f);

        //RestTemplate 中默认的 Factory 实例中，两个超时属性默认是 -1，
        //未启用超时，也不会触发重试
        //return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(Sp07HystrixApplication.class, args);
    }

}
