package eip.fileintegration.mom;


import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

/*
 Based on the RabbitMQ tutorial examples:
 https://www.rabbitmq.com/tutorials/tutorial-one-java.html
 */

public class ConsumerCLI {
    // the name of the queue to be used to communicate
    private final static String QUEUE_NAME = "queue1";

    public static void main(String[] argv) throws Exception {
        // Create a factory of connections to the MOM
        ConnectionFactory factory = new ConnectionFactory();
        // Specify the address of the MOM server
        factory.setHost("localhost");
        // Create a new connection to the MOM
        Connection connection = factory.newConnection();
        // Create a new channel within this connection
        // to consume and to produce messages
        Channel channel = connection.createChannel();
        // Declare the queue to be used with the parameters:
        // durable:false, exclusive: false, autodelete:false,
        // additional-arguments:null
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        // now we will be ready to receive messages
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
        // a queue consumer is created on the chanel
        QueueingConsumer consumer = new QueueingConsumer(channel);
        // the consumer is associated is associate with the queue
        channel.basicConsume(QUEUE_NAME, true, consumer);
        // receive and print the delivered messages
            while (true) {
                QueueingConsumer.Delivery delivery = consumer.nextDelivery();
                String message = new String(delivery.getBody(), "UTF-8");
                System.out.println(" [x] Received '" + message + "'");
            }
        }
}
