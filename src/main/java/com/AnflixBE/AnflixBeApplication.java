package com.AnflixBE;

import java.util.Arrays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableCaching
public class AnflixBeApplication {

	public static void main(String[] args) {
		SpringApplication.run(AnflixBeApplication.class, args);
	}

	@Bean
	public CacheManager cacheManager() {
		SimpleCacheManager simpleCacheManager = new SimpleCacheManager();
		simpleCacheManager
				.setCaches(Arrays.asList(new ConcurrentMapCache("search"), new ConcurrentMapCache("searchTitle")));
		return simpleCacheManager;
	}

}
