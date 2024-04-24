package id.ac.ui.cs.advprog.eshop.mcsimportreq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class McsImportReqApplication {

    public static void main(String[] args) {
        SpringApplication.run(McsImportReqApplication.class, args);
    }

}
