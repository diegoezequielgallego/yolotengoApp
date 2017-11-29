package com.yolotengo.advertisementApp.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cassandra.config.CassandraCqlClusterFactoryBean;
import org.springframework.cassandra.config.DataCenterReplication;
import org.springframework.cassandra.core.keyspace.CreateKeyspaceSpecification;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.config.java.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

import java.util.ArrayList;
import java.util.List;

@Configuration
@PropertySource(value = {"classpath:cassandra.properties"})
@EnableCassandraRepositories(basePackages = {"com.yolotengo.advertisementApp"})
public class CassandraConfig extends AbstractCassandraConfiguration{

    @Autowired
    private Environment environment;

    @Bean
    @Override
    public CassandraCqlClusterFactoryBean cluster() {
        CassandraCqlClusterFactoryBean bean = new CassandraCqlClusterFactoryBean();
        bean.setKeyspaceCreations(getKeyspaceCreations());
        bean.setContactPoints(environment.getProperty("cassandra.contactpoints"));
        bean.setPort(Integer.parseInt(environment.getProperty("cassandra.port")));
        //bean.setUsername(USERNAME);
        //bean.setPassword(PASSWORD);
        return bean;
    }

    @Override
    public SchemaAction getSchemaAction() {
        return SchemaAction.CREATE_IF_NOT_EXISTS;
    }

    @Override
    protected String getKeyspaceName() {
        return environment.getProperty("cassandra.keyspace");
    }

    @Override
    public String[] getEntityBasePackages() {
        return new String[]{"com.yolotengo.advertisementApp"};
    }


    protected List<CreateKeyspaceSpecification> getKeyspaceCreations() {
        List<CreateKeyspaceSpecification> createKeyspaceSpecifications = new ArrayList<>();
        createKeyspaceSpecifications.add(getKeySpaceSpecification());
        return createKeyspaceSpecifications;
    }

    // Below method creates "my_keyspace" if it doesnt exist.
    private CreateKeyspaceSpecification getKeySpaceSpecification() {
        CreateKeyspaceSpecification pandaCoopKeyspace = new CreateKeyspaceSpecification();
        DataCenterReplication dcr = new DataCenterReplication("dc1", 3L);
        pandaCoopKeyspace.name(environment.getProperty("cassandra.keyspace"));
        pandaCoopKeyspace.ifNotExists(true).createKeyspace().withNetworkReplication(dcr);
        return pandaCoopKeyspace;
    }




}
