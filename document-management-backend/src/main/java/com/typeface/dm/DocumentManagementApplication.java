package com.typeface.dm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.File;
import java.nio.file.Paths;
import java.util.TimeZone;

import static com.typeface.dm.common.ApplicationConstants.FILE_STORE_DIRECTORY;

@SpringBootApplication
public class DocumentManagementApplication {

    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        SpringApplication.run(DocumentManagementApplication.class, args);
    }

//    @PostConstruct
//    @PreDestroy
//    public void clearDirectory() {
//        File dir = new File(Paths.get(FILE_STORE_DIRECTORY).toAbsolutePath().toUri());
//        if (dir.exists() && dir.isDirectory()) {
//            File[] files = dir.listFiles();
//            if (files != null) {
//                for (File file : files) {
//                    if (!file.isDirectory()) {
//                        file.delete();
//                    }
//                }
//            }
//        }
//    }

}