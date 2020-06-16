package cn.tedu.sp03;

import com.codingapi.txlcn.tc.config.EnableDistributedTransaction;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableDistributedTransaction
@EnableDiscoveryClient
@SpringBootApplication
@EnableJpaRepositories(basePackages = {"cn.tedu.sp03.user.dao"})
@EntityScan(basePackages = {"cn.tedu.sp03.user.entity"})
public class Sp03UserserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(Sp03UserserviceApplication.class, args);
    }

}
