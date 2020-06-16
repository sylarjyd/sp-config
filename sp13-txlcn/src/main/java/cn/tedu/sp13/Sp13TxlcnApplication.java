package cn.tedu.sp13;

import com.codingapi.txlcn.tm.config.EnableTransactionManagerServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableTransactionManagerServer
@EnableDiscoveryClient
@SpringBootApplication
public class Sp13TxlcnApplication {

    public static void main(String[] args) {
        SpringApplication.run(Sp13TxlcnApplication.class, args);
    }

}
