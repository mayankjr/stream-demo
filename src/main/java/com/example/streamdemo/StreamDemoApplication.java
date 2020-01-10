package com.example.streamdemo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import reactor.core.publisher.EmitterProcessor;
import reactor.core.publisher.Flux;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@SpringBootApplication
@Controller
public class StreamDemoApplication {
	private final Log logger = LogFactory.getLog(StreamDemoApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(StreamDemoApplication.class, args);
	}

//	@Bean
//	public Supplier<String> generate() {
//		return () -> "sample" + Math.random();
//	}
//
//	@Bean
//	public Function<String, String> process() {
//		return String::toUpperCase;
//	}

	EmitterProcessor<String> processor = EmitterProcessor.create();

	@RequestMapping
	@ResponseStatus(HttpStatus.ACCEPTED)
	public void delegateToSupplier(@RequestBody String body) {
		logger.info(body);
		processor.onNext(body);
	}

	@Bean
	public Supplier<Flux<String>> generate() {
		return () -> processor;
	}

	@Bean
	public Function<Flux<String>, Flux<String>> process() {
		return flux -> flux.map(val -> val.toUpperCase());
	}

	@Bean
	public Consumer<String> consume() {
		return payload -> logger.info("Data received: " + payload);
	}
}
