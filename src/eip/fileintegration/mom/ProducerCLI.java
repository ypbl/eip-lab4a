package eip.fileintegration.mom;


import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/*
 Based on the RabbitMQ tutorial examples:
 https://www.rabbitmq.com/tutorials/tutorial-one-java.html
 */

public class ProducerCLI {
    
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
        // Prepare the message
        String message = "Hello World!";
        // Send the message to the queue
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
        System.out.println(" [x] Sent '" + message + "'");
        // Close the channel and the connection and finish the execution
        channel.close();
        connection.close();
    }
}
