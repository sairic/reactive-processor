package com.macys.reactive.model;

import com.datastax.driver.core.DataType;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.UUID;

@Table
public class StreamEvent {

    @PrimaryKeyColumn(name = "message_id", type = PrimaryKeyType.PARTITIONED)
    @CassandraType(type = DataType.Name.UUID)
    private UUID id;

    private String message;

    public StreamEvent() {
    }

    public StreamEvent(UUID id, String message) {
        this.id = id;
        this.message = message;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}