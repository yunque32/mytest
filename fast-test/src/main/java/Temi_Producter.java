import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class Temi_Producter {

    public static void main(String[] arg0) throws JMSException {

        String brokerUrl="tcp://192.168.12.131:61616";
        ConnectionFactory factory = new ActiveMQConnectionFactory(brokerUrl);
        Connection connection = factory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination queue = session.createQueue("queue-test");
        MessageProducer producer = session.createProducer(queue);
        TextMessage message = session.createTextMessage();
        message.setText("我的第一次jms");
        producer.send(message);
        producer.close();
        session.close();
        connection.close();


    };
}
