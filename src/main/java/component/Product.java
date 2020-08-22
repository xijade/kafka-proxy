package component;
import java.util.Properties;
import java.util.Random;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
public class Product {
    public static String topic = "test";//定义主题

    public static void main(String[] args) throws InterruptedException {
        Properties p = new Properties();
        p.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "47.98.104.23:9092,47.98.104.23:9093");//kafka地址，多个地址用逗号分割
        p.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        p.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        p.put("acks", "all");
        p.put("retries", 0);
        p.put("batch.size", 16384);
        p.put("linger.ms", 1);
        p.put("buffer.memory", 33554432);
        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<String, String>(p);

        try {
            while (true) {
                String msg = "Hello," + new Random().nextInt(100);
                ProducerRecord<String, String> record = new ProducerRecord<String, String>(topic, msg);
                kafkaProducer.send(record);
                System.out.println("消息发送成功:" + msg);
                Thread.sleep(5000);
            }
        } finally {
            kafkaProducer.close();
        }

    }
}
