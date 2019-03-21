package com.macys.reactive;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;
import reactor.core.publisher.Flux;

import java.time.Duration;


/*  CREATE KEYSPACE IF NOT EXISTS pricing WITH REPLICATION = { 'class':'SimpleStrategy', 'replication_factor':1 }; */


@SpringBootApplication
@EnableBinding(Processor.class)
public class ReactiveProcessorApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReactiveProcessorApplication.class, args);
	}

	@StreamListener
	@Output(Processor.OUTPUT)
	public Flux<String> aggregate(@Input(Processor.INPUT) Flux<String> inbound) {
		return inbound.
				log()
				.window(Duration.ofSeconds(5), Duration.ofSeconds(5))
				.flatMap(w -> w.reduce("", (s1,s2)->s1+s2))
				.log();
	}

	public interface Sink {
		@Input("test-sink")
		SubscribableChannel sampleSink();
	}

	public interface Source {
		@Output("test-source")
		MessageChannel sampleSource();
	}
}
