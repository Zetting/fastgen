package com.fastgen;

import com.fastgen.core.base.dds.DynamicDataSourceRegister;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Import(DynamicDataSourceRegister.class)
@SpringBootApplication
public class GenApplication {

    public static void main(String[] args) {
        SpringApplication.run(GenApplication.class, args);
    }

}
