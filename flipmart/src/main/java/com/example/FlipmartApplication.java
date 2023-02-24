package com.example;

import javax.annotation.Resource;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.service.StorageService;

@SpringBootApplication
public class FlipmartApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(FlipmartApplication.class, args);
	}
	
	@Resource
	private StorageService storageService;

	@Override
	public void run(String... args) throws Exception {
		storageService.init();
	}

}
