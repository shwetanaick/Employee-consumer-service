package com.benz.file.service.decrypt.config;

import org.apache.kafka.streams.StreamsConfig;
import org.springframework.cloud.stream.binder.kafka.streams.properties.KafkaStreamsConsumerProperties;
import org.springframework.kafka.config.KafkaStreamsConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {

    @Value("${kafka.consumer.bootstrap-servers}")
    public String bootstrapServers = "";
    @Value("${kafka.consumer.keySerde}")
    public String keySerde = "";
    @Value("${kafka.consumer.valueSerde}")
    public String valueSerde = "";



    public KafkaStreamsConfiguration getKafkaStreamsConfiguration(){
        Map props = new HashMap<String,Object>();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG,"Application");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG,bootstrapServers);
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG,keySerde);
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG,valueSerde);
        return new KafkaStreamsConfiguration(props);
    }
    @Bean
    public StreamsBuilderFactoryBean getStreamsBuilderFactoryBean(){
        KafkaStreamsConsumerProperties kafkaStreamsConsumerProperties = new KafkaStreamsConsumerProperties();
        return new StreamsBuilderFactoryBean(getKafkaStreamsConfiguration());
    }


}
