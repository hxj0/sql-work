package com.hxj.book.config;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

/**
 * @author Evan
 * @date 2019/4
 */
@SpringBootConfiguration
public class MyWebConfigurer implements WebMvcConfigurer {


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//    System.out.println(new File("").getAbsolutePath().replaceAll("\\\\","/")+"/upload/");
        registry.addResourceHandler("/upload/**").addResourceLocations("file:"+new File("").getAbsolutePath()+"/upload/");
    }

}
