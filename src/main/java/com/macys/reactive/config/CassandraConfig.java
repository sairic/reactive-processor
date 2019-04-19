package com.macys.reactive.config;

import com.datastax.driver.core.PlainTextAuthProvider;
import com.datastax.driver.core.policies.ConstantReconnectionPolicy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.*;
import org.springframework.data.cassandra.core.cql.keyspace.CreateKeyspaceSpecification;
import org.springframework.data.cassandra.core.cql.keyspace.DropKeyspaceSpecification;
import org.springframework.data.cassandra.core.cql.keyspace.KeyspaceOption;
import org.springframework.data.cassandra.repository.config.EnableReactiveCassandraRepositories;

import java.util.Arrays;
import java.util.List;



@Configuration
@EnableReactiveCassandraRepositories(basePackages = "com.macys.reactive")
public class CassandraConfig extends AbstractReactiveCassandraConfiguration{
    @Value("${spring.data.cassandra.contact-points}") private String contactPoints;
    @Value("${spring.data.cassandra.port}") private int port;
    @Value("${spring.data.cassandra.keyspace-name}") private String keyspace;

    @Value("${spring.data.cassandra.username}") private String userName;
    @Value("${spring.data.cassandra.password}") private String password;


    @Override protected String getKeyspaceName() {
        return keyspace;
    }
    @Override protected String getContactPoints() {
        return contactPoints;
    }
    @Override protected int getPort() {
        return port;
    }
    @Override public SchemaAction getSchemaAction() {
        return SchemaAction.CREATE_IF_NOT_EXISTS;
    }
    @Override
    public String[] getEntityBasePackages() {
        return new String[]{"com.macys.reactive.model"};
    }

    @Override
    public CassandraClusterFactoryBean cluster() {
        PlainTextAuthProvider authProvider = new PlainTextAuthProvider(userName, password);

        CassandraClusterFactoryBean cluster=new CassandraClusterFactoryBean();

        cluster.setJmxReportingEnabled(false);
        cluster.setContactPoints(contactPoints);
        System.out.println("RICARDO CONTACT POINTS IS " + contactPoints);
        cluster.setPort(port);
        cluster.setAuthProvider(authProvider);
        cluster.setKeyspaceCreations(getKeyspaceCreations());
        cluster.setReconnectionPolicy(new ConstantReconnectionPolicy(1000));

        return cluster;
    }


    @Override
    protected List<CreateKeyspaceSpecification> getKeyspaceCreations() {

        CreateKeyspaceSpecification specification = CreateKeyspaceSpecification.createKeyspace(keyspace)
            .ifNotExists()
            .with(KeyspaceOption.DURABLE_WRITES, true);

        return Arrays.asList(specification);
    }



    @Override
    protected List<DropKeyspaceSpecification> getKeyspaceDrops() {
        return Arrays.asList(DropKeyspaceSpecification.dropKeyspace(keyspace));
    }



}
