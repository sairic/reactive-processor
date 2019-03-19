package com.macys.reactive.repository;

import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import com.macys.reactive.model.StreamEvent;
import org.springframework.stereotype.Component;

@Component
public interface StreamEventRepository extends ReactiveCassandraRepository<StreamEvent, String> {

}
