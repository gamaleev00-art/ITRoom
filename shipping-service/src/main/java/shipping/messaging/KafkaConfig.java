package shipping.messaging;

import dto_order.OrderDTO;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic consumerTopic(@Value("${app.kafka.consumer-topic}") String topicName,
                                  @Value("${app.kafka.consumer-partitions}") int partitions) {
        return TopicBuilder.name(topicName)
                .partitions(partitions)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic producerTopic(@Value("${app.kafka.producer-topic}") String topicName,
                                  @Value("${app.kafka.producer-partitions}") int partitions) {
        return TopicBuilder.name(topicName)
                .partitions(partitions)
                .replicas(1)
                .build();
    }

    @Bean
    public KafkaTemplate<String, OrderDTO> kafkaTemplate(ProducerFactory<String, OrderDTO> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }
}
