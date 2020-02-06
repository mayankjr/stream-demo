package com.example.streamdemo;

import com.example.streamdemo.model.SimplePojo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bson.types.ObjectId;
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
	public Supplier<SimplePojo> generate1() {
		return () -> new SimplePojo(new ObjectId("5d57ea04a925495093b62549"));
	}

	@Bean
	public Function<Flux<String>, Flux<String>> process() {
		return flux -> flux.map(String::toUpperCase);
	}

	@Bean
	public Consumer<String> consume() {
		return payload -> logger.info("Data received: " + payload);
	}

	@Bean
	public Consumer<SimplePojo> consume1() {
		return payload -> logger.info("Data received: " + payload.toString());
	}

}
