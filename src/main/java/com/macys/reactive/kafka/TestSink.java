package com.macys.reactive.kafka;

import com.macys.reactive.ReactiveProcessorApplication;
import com.macys.reactive.model.StreamEvent;
import com.macys.reactive.repository.StreamEventRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import reactor.core.publisher.Flux;

import java.util.UUID;

@EnableBinding(ReactiveProcessorApplication.Sink.class)
public class TestSink {

    private final Log logger = LogFactory.getLog(getClass());

    private StreamEventRepository repository;

    public TestSink(StreamEventRepository repository) {
        this.repository = repository;
    }

    @StreamListener("test-sink")
    public void receive(Flux<String> payload) {
        payload
            .flatMap(x -> this.repository.save(new StreamEvent(UUID.randomUUID(), x)))
            .subscribe(x -> logger.info("Data received: " + x));
    }
}
