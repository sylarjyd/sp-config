package cn.tedu.sp02;

import com.codingapi.txlcn.tc.config.EnableDistributedTransaction;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableDistributedTransaction
@EnableDiscoveryClient
@SpringBootApplication
@EnableJpaRepositories(basePackages = {"cn.tedu.sp02.item.dao"})
@EntityScan(basePackages = {"cn.tedu.sp02.item.entity"})
public class Sp02ItemserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(Sp02ItemserviceApplication.class, args);
    }

}
