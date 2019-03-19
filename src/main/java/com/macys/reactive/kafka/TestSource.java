package com.macys.reactive.kafka;

import com.macys.reactive.ReactiveProcessorApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.cloud.stream.reactive.StreamEmitter;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.Random;

@EnableBinding(ReactiveProcessorApplication.Source.class)
public class TestSource {

    private Random random = new Random();

    @StreamEmitter
    @Output("test-source")
    public Flux<String> emit() {
        return Flux.interval(Duration.ofMillis(1000))
                .map(aLong -> random.nextBoolean() ? "foo" : "bar");
    }

}
