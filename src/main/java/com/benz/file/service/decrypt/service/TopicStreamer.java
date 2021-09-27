package com.benz.file.service.decrypt.service;

import com.benz.file.service.decrypt.config.KafkaConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TopicStreamer {
    @Autowired
    KafkaConsumerConfig consumerConfig;
    @Autowired
    ProcessorService processorService;

    @Value("${kafka.consumer.topic}")
    public String topic = "";

    @Bean
    public String invokeKStream(StreamsBuilder kStreamBuilder){
        KStream<String,String> stream = kStreamBuilder.stream(topic, Consumed.with(Serdes.String(),Serdes.String()));
        stream.mapValues(v->processorService.processFile(v));

        return null;
    }



}
